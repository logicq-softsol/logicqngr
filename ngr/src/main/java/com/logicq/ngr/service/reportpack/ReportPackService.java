package com.logicq.ngr.service.reportpack;

import java.util.List;
import java.util.Set;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.model.reportpack.ReportpackDetails;
import com.logicq.ngr.vo.ReportPackDetailsVO;
import com.logicq.ngr.vo.ReportpackTemplateVO;
import com.logicq.ngr.vo.UserTemplateVO;

public interface ReportPackService {
	
	ReportPackDetailsVO createreportpack(ReportPackDetailsVO reportPackDetailsVO) throws Exception;
	
	void  moveReportPack(List<UserTemplateVO> userTemplateVoList) throws Exception;

	UserReport addreport(ReportpackTemplateVO reportpackTemplateVO) throws Exception;

	UserReport getAllReportPack(String userName) throws Exception;

	ReportpackDetails getReportPack(String reportpackId) throws Exception;
	
	UserReport getspecificreportpack(ReportpackTemplateVO reportpackTemplateVO)throws Exception;

	List<DashboardReport> getReportpackDashboardReports(UserDetails userDetails) throws Exception;
	
	DashboardReport getReportPackAccordingToFilter(ReportpackTemplateVO reportpackTemplateVO) throws Exception;

	void assignReportpack(Set<String> reportPackList, Profile profile) throws Exception;

	void deleteReportPack(ReportPackDetailsVO reportPackDetailsVO) throws Exception;
}
