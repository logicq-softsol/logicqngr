package com.logicq.ngr.service.reportpack;

import java.util.List;
import java.util.Set;

import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.vo.ReportpackTemplateVO;

public interface ReportPackTemplateService {

	ReportpackTemplate saveReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO)throws Exception;
	
	ReportpackTemplate saveReportpackTemplate(ReportpackTemplate reportpackTemplate)throws Exception;
	
	List<ReportpackTemplate> getReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO)throws Exception;
	
	UserReport editReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception;
	
	List<ReportpackTemplate>  getReportpackTemplateBasedOnReportType(ReportpackTemplateVO reportpackTemplateVO,Set<String> reportTypeSet)throws Exception;
	
	ReportpackTemplate getReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO)throws Exception;
	
	void deleteReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception;
}
