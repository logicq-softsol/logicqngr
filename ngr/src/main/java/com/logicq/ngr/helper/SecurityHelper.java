package com.logicq.ngr.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.service.security.TokenAuthenticationService;
import com.logicq.ngr.vo.DashBoardVO;
import com.logicq.ngr.vo.ReportPackDetailsVO;
import com.logicq.ngr.vo.ReportpackTemplateVO;
import com.logicq.ngr.vo.UserTemplateVO;

@Component
public class SecurityHelper {
	

	@Autowired
	TokenAuthenticationService tokenAuthenticationService;

	public  String getUserNameFromSecurityContext() throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
		return  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
	}
	
	
	public String setUserNameFromSecurityContext(UserTemplateVO usertemplatevo) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					usertemplatevo.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}
	
	

	public String setUserNameFromSecurityContext(DashBoardVO dashboardVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					dashboardVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}
	
	public String setUserNameFromSecurityContext(ReportPackDetailsVO reportPackDetailsVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					reportPackDetailsVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}
	
	public String setUserNameFromSecurityContext(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					reportpackTemplateVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}
}