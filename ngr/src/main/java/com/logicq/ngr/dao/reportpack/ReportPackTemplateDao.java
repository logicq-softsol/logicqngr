package com.logicq.ngr.dao.reportpack;

import java.util.List;
import java.util.Set;

import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.vo.ReportpackTemplateVO;

public interface ReportPackTemplateDao {

	ReportpackTemplate saveReportpackTemplate(ReportpackTemplate reportpackTemplate)throws Exception;
	
	List<ReportpackTemplate> getReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO)throws Exception;
	
	//getReportPackTemplatate based on ReportPackId and ReportPackTemplateId
	ReportpackTemplate getReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO)throws Exception;
	
	public ReportpackTemplate updateReportPackTemplate(ReportpackTemplate reportpackTemplate)throws Exception;
	
	List<ReportpackTemplate>  getReportpackTemplateBasedOnReportType(ReportpackTemplateVO reportpackTemplateVO,Set<String> reportType)throws Exception;

	void deleteReportPackTemplate(ReportpackTemplate reportpacktemplate);
}
