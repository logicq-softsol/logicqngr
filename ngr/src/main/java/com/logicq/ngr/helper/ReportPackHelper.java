package com.logicq.ngr.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.helper.DruidHelper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.model.druid.request.ReportRequest;
import com.logicq.ngr.model.report.Report;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.model.reportpack.ReportpackTemplateKey;
import com.logicq.ngr.model.response.DruidResponse;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.ReportpackTemplateVO;

@Component
public class ReportPackHelper {
	
	private ObjectMapper mapper = ObjectFactory.getObjectMapper();
	
	@Autowired
	DruidHelper druidHelper;
	
	ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * This method is used as helper method to get Response from Driud .
	 * 
	 * @param List<ReportpackTemplate>
	 * @return List<Report>
	 */
	public List<Report> preparereportpackReports(List<ReportpackTemplate> reportpacktemplateList) throws Exception {
		List<Report> reports = new ArrayList<>();

		for (ReportpackTemplate reportpackTemplate : reportpacktemplateList) {
			Report report = new Report();
			ReportRequest reportRequest = mapper.readValue(reportpackTemplate.getJsonString(),
					new TypeReference<ReportRequest>() {
					});
			DruidResponse druidResponse = new DruidResponse();
			druidResponse.setUserTemplateId(reportpackTemplate.getReportpackTemplateKey().getReportpackTemplateId());
			String intervals = reportRequest.getIntervals();
			if (!StringUtils.isEmpty(intervals)) {
				reportRequest.setIntervals(druidHelper.generateIntervalString(intervals));
			}
			if (!ReportType.ALRAM.getValue().equals(reportpackTemplate.getReportType())) {
				druidResponse.setResponse(druidHelper.prepareDruidResponse(reportRequest));
				reportRequest.setIntervals(intervals);
				druidResponse.setReportConfiguration(reportRequest);
				report.setResponse(druidResponse);
				report.setReportType(reportpackTemplate.getReportType());
				reports.add(report);
			}
		}
		return reports;
	}

	/**
	 * This is a helper method used to convert the ReportpackTemplateVO to ReportpackTemplate
	 * 
	 * @param ReportpackTemplateVO reportpackTemplateVO
	 * @return ReportpackTemplate
	 */
	public ReportpackTemplate convertReportpackVOToEntity(ReportpackTemplateVO reportpackTemplateVO) throws Exception{
		ReportpackTemplate reportpackTemplate=new ReportpackTemplate();
		ReportpackTemplateKey reportpackTemplateKey=new ReportpackTemplateKey();
		
		if(!StringUtils.isEmpty(reportpackTemplateVO.getReportpackId())){
			reportpackTemplateKey.setReportpackId(reportpackTemplateVO.getReportpackId());
		}
		
		if(!StringUtils.isEmpty(reportpackTemplateVO.getReportpackTemplateId())){
			reportpackTemplateKey.setReportpackTemplateId(reportpackTemplateVO.getReportpackTemplateId());
		}
		
		if(!StringUtils.isEmpty(reportpackTemplateVO.getReportType())){
			reportpackTemplate.setReportType(reportpackTemplateVO.getReportType());
		}
		
		if(null!=reportpackTemplateVO.getJsonString()){
			reportpackTemplate.setJsonString(objectMapper.writeValueAsBytes(reportpackTemplateVO.getJsonString()));
		}
		reportpackTemplate.setReportpackTemplateKey(reportpackTemplateKey);
		return reportpackTemplate; 
	}
}
