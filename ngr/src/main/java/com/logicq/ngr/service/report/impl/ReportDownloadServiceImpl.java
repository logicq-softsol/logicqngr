package com.logicq.ngr.service.report.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.helper.ReportDownloadHelper;
import com.logicq.ngr.model.response.ExportDetails;
import com.logicq.ngr.service.report.ReportDownloadService;
import com.logicq.ngr.service.report.ReportService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
@Transactional
public class ReportDownloadServiceImpl implements ReportDownloadService {

	@Autowired
	ReportService reportService;

	@Autowired
	ReportDownloadHelper reportDownloadHelper;

	ObjectMapper mapper = ObjectFactory.getObjectMapper();
	

	@Override
	public String exportReport(UserTemplateVO userTemplateVO) throws Exception {
		if (null != LoadApplicationData.siteOptionMap) {
			String thresholdValue = LoadApplicationData.siteOptionMap.get("threshold");
			if (!StringUtils.isEmpty(thresholdValue) && null != thresholdValue) {
				Map<String, Object> paginationMap = new HashMap<>();
				paginationMap.put("pagenumber", 1);
				paginationMap.put("pagesize", Integer.valueOf(LoadApplicationData.siteOptionMap.get("threshold")));
							
				if (null != userTemplateVO.getJsonString()) {
					userTemplateVO.getJsonString().put("pagination", paginationMap);
				}else if (null == userTemplateVO.getJsonString()) {
					Map<String,Object> jsonString = new HashMap<String,Object>();
					jsonString.put("pagination", paginationMap);
					userTemplateVO.setJsonString(jsonString);
				}
			}
		}

		ExportDetails exportDetails = new ExportDetails();
		exportDetails.setUserReport(reportService.getReportAccordingToFilter(userTemplateVO));
		exportDetails.setFileHeaderList(new ArrayList<>());
		exportDetails.setFileContent(reportDownloadHelper.processDruidResponseData(exportDetails));
		exportDetails.setReportType(userTemplateVO.getFileType());
		exportDetails.getUserReport().setEmail(LoadApplicationData.userMap.get(userTemplateVO.getUserName()).getEmail());
		exportDetails.setReportFlag(true);
		return reportDownloadHelper.generateReport(exportDetails);

	}

}