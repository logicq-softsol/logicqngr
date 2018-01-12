package com.logicq.ngr.common.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.constant.TokenAuthenticationConstant;
import com.logicq.ngr.service.security.TokenAuthenticationService;
import com.logicq.ngr.service.security.UserService;
import com.logicq.ngr.validator.Preconditions;
import com.logicq.ngr.vo.UserVO;

public class RestAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;
	private UserService userService;
	public Map<String,String> loggedInUsers=new HashMap<String,String>(); // Key is UserName, Value is token
	

	public RestAuthenticationFilter(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = Preconditions.checkNotNull(tokenAuthenticationService);
		this.userService = userService;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			if ("OPTIONS".equals(httpRequest.getMethod())) {
				filterChain.doFilter(request, response);
			} else {
				final String token = httpRequest.getHeader(TokenAuthenticationConstant.AUTH_HEADER_NAME);

				if (!StringUtils.isEmpty(token)) {
						UserVO userVo = tokenAuthenticationService.getTokenHandler().parseUserFromToken(token);
				        //String exstingtoken=LoadApplicationData.userTokenMap.get(userVo.getUsername());
						//if(token.equals(exstingtoken)){
				           if (tokenAuthenticationService.getTokenHandler().validateToken(token, userVo)) {
							Authentication authentication = tokenAuthenticationService
									.getAuthentication((HttpServletRequest) request);
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
//				         else{
//							LoadApplicationData.userTokenMap.remove(userVo.getUsername());
//						}
					//}
				} else if (httpRequest.getRequestURI().endsWith("login")) {
					String username = (String) httpRequest.getHeader("username");
					String password = (String) httpRequest.getHeader("password");
					UserVO userDetails = userService.loadUserByUsername(username);
					//LDAPAuthenticationProvider ldapAuthenticationProvider = new LDAPAuthenticationProvider();
					//Currently skip the ldap authetication
					//if (ldapAuthenticationProvider.authenticateUser(userDetails.getEin(), password)) {
						 final	String	authtoken = tokenAuthenticationService.getTokenHandler()
									.createTokenForUser(userDetails);
						if (!StringUtils.isEmpty(authtoken)) {
							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
									userDetails, password, userDetails.getAuthorities());
							authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
							authentication.setDetails(authtoken);
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
					//}
				}
				if (SecurityContextHolder.getContext().getAuthentication() != null
						&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
					filterChain.doFilter(request, response);
				}
			}

		} catch (Exception ex) {
			SetResponse(httpResponse, HttpStatus.UNAUTHORIZED, 403, ex);
		} finally {
			if (null != SecurityContextHolder.getContext()) {
				SecurityContextHolder.getContext().setAuthentication(null);
//				SetResponse(httpResponse, HttpStatus.UNAUTHORIZED, 403, null);
			}
		}

	}

	public void SetResponse(HttpServletResponse httpResponse, HttpStatus message, Integer responsecode, Exception ex)
			throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		httpResponse.setContentType("application/json;charset=UTF-8");
		((HttpServletResponse) httpResponse).setStatus(responsecode, message.toString());
		PrintWriter out = httpResponse.getWriter();
		if(null!=ex){
		out.print(jsonMapper.writeValueAsString(ex.getMessage()));
		}
	}

}
