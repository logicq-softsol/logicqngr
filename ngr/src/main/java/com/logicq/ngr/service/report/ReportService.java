package com.logicq.ngr.service.report;

import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.vo.UserTemplateVO;

public interface ReportService {
	
	public UserReport getUserReport(UserTemplateVO userTemplateVO) throws Exception;

	public UserReport getSpecificUserReports(UserTemplateVO userTemplateVO) throws Exception;

	public UserReport getReportAccordingToFilter(UserTemplateVO userTemplateVO) throws Exception;

	public void deleteUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	public UserReport addUserReport(UserTemplateVO userTemplateVO) throws Exception;

	public UserReport editUserReport(UserTemplateVO userTemplateVO) throws Exception;
	
}