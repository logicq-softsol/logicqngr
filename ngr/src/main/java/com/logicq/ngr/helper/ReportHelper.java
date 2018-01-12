package com.logicq.ngr.helper;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logicq.ngr.common.helper.RandomNumberHelper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.BaseReport;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.vo.UserTemplateVO;

// Need to change as its not going to use as helper . It service need to change
@Component
public class ReportHelper {

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

	
	public void prepareBaseReport(BaseReport basereport ,UserTemplateVO userTemplateVO) {
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

	/**
	 * Prepare dashboard details
	 * @param type
	 * @param name
	 * @param viewType
	 * @param user
	 * @return
	 */
	public Dashboard prepareDashBoardReport(String name, String type, ReportType viewType, UserDetails user) {
		Dashboard dashboard = new Dashboard();
		dashboard.setName(name);
		dashboard.setType(type);
		if (null != viewType) {
			dashboard.setViewType(viewType.getValue());
		}
		dashboard.setUserDetails(user);
		dashboard.setDashboardId(RandomNumberHelper.generateRandomAlphaNumericString());
		return dashboard;
	}
	
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str) || "null".equals(str));
	}
	
	public static <T> boolean IsNullOrEmptyForCollection(Collection<T> list) {
	    return list == null || list.isEmpty();
	}
	public static String[] splitString(String str,String regx) {
		if(!isEmpty(str)){
			return  str.split(regx);
		}
		return null;
	}
	
	public static String[] splitStringWithComma(String str) {
		return splitString(str,",");
	}
}
