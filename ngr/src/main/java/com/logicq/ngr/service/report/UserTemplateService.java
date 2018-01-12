package com.logicq.ngr.service.report;

import java.util.List;
import java.util.Set;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.vo.UserTemplateVO;

public interface UserTemplateService {

	List<UserTemplate> searchUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	UserTemplate getUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	void editUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	void saveUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	void saveUserTemplate(UserTemplate userTemplate) throws Exception;

	void deleteUserTemplate(UserTemplate userTemplate) throws Exception;

	public List<Dashboard> getUserDashboards(UserTemplateVO userTemplateVO) throws Exception;

	public List<UserTemplate> getUserTemplatesOfDashboard(String dashboardId);

	public Dashboard getUserDashboard(UserTemplateVO userTemplateVO) throws Exception;

	List<UserTemplateVO> fetchUserTemplate(UserTemplateVO userTemplateVO);
	
	void editUserTemplate(UserTemplate usertemplate);

	void copyUserTemplate(UserTemplateVO usertemplateVO) throws Exception;

	void saveAlarmTemplate(Dashboard alarmDashBoard) throws Exception;

	List<UserTemplate> searchUserTemplateforReportPack(UserTemplateVO userTemplateVO,Set<String> reportTypeSet) throws Exception;
	
	//This method is added for Report Pack Feature to get Multiple Reports based on dashboardId and list of usertemplateIds and List of Report Type
	public List<UserTemplate> getUserTemplateforReportPack(UserTemplateVO userTemplateVo,Set<String> userTemplateIdList, Set<String> reportTypeSet) throws Exception;
		
	void deleteUserTemplate(UserDetails userDtl);

	void deleteUserTemplateByDashboardId(List<String> dashboardIds) throws Exception;

	void removeMarkDeletedUserTemplates(List<UserDetails> userDtls) throws Exception;
	
	void markUserTemplatesAsDeleted(Set<String> userTemplateIds);
}
