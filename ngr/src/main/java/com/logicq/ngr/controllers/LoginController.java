package com.logicq.ngr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.MessageHelper;
import com.logicq.ngr.constant.TokenAuthenticationConstant;
import com.logicq.ngr.helper.DynsHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.ReportService;
import com.logicq.ngr.service.security.TokenAuthenticationService;
import com.logicq.ngr.vo.MessageVO;
import com.logicq.ngr.vo.UserTemplateVO;
import com.logicq.ngr.vo.UserVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	ReportService reportService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	MessageHelper messageHelper;

	@Autowired
	DynsHelper dynsHelper;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> login() throws Exception {
		UserReport userreport = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserVO uservo = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserVO) {
				uservo = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			} else {
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
					String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					uservo = LoadApplicationData.userMap.get(username);
				}
			}
			if (null != uservo) {

				String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
				if (!tokenAuthenticationService.getTokenHandler().isTokenExpired(token)) {
					responseHeaders.set(TokenAuthenticationConstant.AUTH_HEADER_NAME, token);
					UserTemplateVO userTemplateVO = new UserTemplateVO();
					userTemplateVO.setUserName(uservo.getUsername());
					if (uservo.getEnabled()) {
						userreport = reportService.getUserReport(userTemplateVO);
					}

					UserDetails userDetails = userAdminService.getUser(uservo.getUsername());
					userDetails.setIsActive(Boolean.TRUE);
					userAdminService.updateUserDetails(userDetails);

				}

			}
		} else {
			return new ResponseEntity<UserReport>(userreport, responseHeaders, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<UserReport>(userreport, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> logout(@RequestHeader HttpHeaders headers) throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			UserVO uservo = null;
			if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof UserVO) {
					uservo = (UserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
					UserDetails userDetails = userAdminService.getUser(uservo.getUsername());
					userDetails.setIsActive(Boolean.FALSE);
					userAdminService.updateUserDetails(userDetails);
					messageVO = messageHelper.handleBusinessMessage(1008);
				}
			}
		} catch (Exception ex) {
			throw new BusinessException(1007);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}
}
