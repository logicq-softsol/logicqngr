package com.logicq.ngr.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.BaseReport;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.RoleVO;
import com.logicq.ngr.vo.UserDetailVO;
import com.logicq.ngr.vo.UserTemplateVO;

// Need to change as its not going to use as helper . It service need to change
@Component
public class DynsHelper {

	@Autowired
	UserAdminService userAdminService;

	public void fetchUserFromUserName(UserTemplateVO userTemplateVO) {
		if (!StringUtils.isEmpty(userTemplateVO.getUserName())) {
			UserDetails userDetails = userAdminService.getUser(userTemplateVO.getUserName());
			if (null != userDetails) {
				userTemplateVO.setUserId(userDetails.getId());
			}
		}
	}

	public void prepareBaseReport(BaseReport basereport, UserTemplateVO userTemplateVO) {
		if (!StringUtils.isEmpty(userTemplateVO.getUserName())) {
			UserDetails userDetails = userAdminService.getUser(userTemplateVO.getUserName());
			if (null != userDetails) {
				userTemplateVO.setUserId(userDetails.getId());
				basereport.setUserName(userTemplateVO.getUserName());
				basereport.setFirstName(userDetails.getFirstName());
				basereport.setLastName(userDetails.getLastName());
				basereport.setEmail(userDetails.getEmail());
			}
		}
	}

	public UserDetailVO populateUserDetailVO(UserDetails userDetails, UserDetailVO userDetailVO) {

		if (userDetails.getDob() != null) {
			userDetailVO.setDob(userDetails.getDob());
		}
		if (!StringUtils.isEmpty(userDetails.getFirstName())) {
			userDetailVO.setFirstName(userDetails.getFirstName());
		}
		if (!StringUtils.isEmpty(userDetails.getLastName())) {
			userDetailVO.setLastName(userDetails.getLastName());
		}
		if (!StringUtils.isEmpty(userDetails.getEmail())) {
			userDetailVO.setEmail(userDetails.getEmail());
		}
		if (!StringUtils.isEmpty(userDetails.getMobilenumber())) {
			userDetailVO.setMobileNumber(userDetails.getMobilenumber());
		}
		if (!StringUtils.isEmpty(userDetails.getLastLoggedIn())) {
			userDetailVO.setLastLoggedIn(userDetails.getLastLoggedIn());
		}
		userDetailVO.setIsActive(userDetails.getIsActive());

		RoleVO roleVO = new RoleVO();
		roleVO.setName(userDetails.getRole().getName());
		roleVO.setRoleId(userDetails.getRole().getRoleId());
		userDetailVO.setRoleVO(roleVO);

		ProfileVO profileVo = new ProfileVO();
		profileVo.setId(userDetails.getProfile().getProfileId());
		profileVo.setName(userDetails.getProfile().getName());
		userDetailVO.setProfileVO(profileVo);
		
		return userDetailVO;
	}
}
