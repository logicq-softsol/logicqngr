package com.logicq.ngr.service.reportpack.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.dao.reportpack.ReportPackTemplateDao;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.ReportPackHelper;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.report.Report;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.service.reportpack.ReportPackTemplateService;
import com.logicq.ngr.vo.ReportpackTemplateVO;

/**
 * 
 * This class deals with implementation of services related to ReportPackTemplate.
 *
 */
@Service
@Transactional
public class ReportPackTemplateServiceImpl implements ReportPackTemplateService {
	
	@Autowired
	ReportPackTemplateDao reportPackTemplateDao;
	
	@Autowired
	ReportPackService reportPackService;
	
	@Autowired
	ReportPackHelper reportPackHelper;
	
	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * This method is used to save Report Pack Template
	 * 
	 * @param ReportpackTemplateVO reportpackTemplateVO
	 * @return ReportpackTemplate
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ReportpackTemplate saveReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		return reportPackTemplateDao.saveReportpackTemplate(reportPackHelper.convertReportpackVOToEntity(reportpackTemplateVO));
	}
	
	/**
	 * This method is used to save ReportpackTemplate into db
	 * 
	 * @param ReportpackTemplate reportpackTemplate
	 * @return ReportpackTemplate
	 */
	@Override
	public ReportpackTemplate saveReportpackTemplate(ReportpackTemplate reportpackTemplate) throws Exception {
		return reportPackTemplateDao.saveReportpackTemplate(reportpackTemplate);
	}

	/**
	 * This method is used to get Report Pack Template based on reportpackId
	 * 
	 * @param ReportpackTemplateVO reportpackTemplateVO
	 * @return List<ReportpackTemplate>
	 */
	@Override
	public List<ReportpackTemplate> getReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		return reportPackTemplateDao.getReportpackTemplate(reportpackTemplateVO);
	}

	/**
	 * This method is used to edit Report Pack Template and update it into db
	 * 
	 * @param ReportpackTemplateVO reportpackTemplateVO
	 * @return ReportpackTemplateVO
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserReport editReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		DashboardReport dashboardReport = new DashboardReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		ReportpackTemplate reportpackTemplate=reportPackTemplateDao.getReportPackTemplate(reportpackTemplateVO);
		List<ReportpackTemplate> reportpacktemplateList =new ArrayList<>();
		Map<String, Object> fetchReportPackTemplateMap = objectMapper.readValue(new String(reportpackTemplate.getJsonString()),
				new TypeReference<Map<String, Object>>() {
				});
		fetchReportPackTemplateMap.putAll(reportpackTemplateVO.getJsonString());
		reportpackTemplate.setJsonString(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(fetchReportPackTemplateMap));
		reportPackTemplateDao.updateReportPackTemplate(reportpackTemplate);
		reportpacktemplateList.add(reportpackTemplate);
		List<Report> report=reportPackHelper.preparereportpackReports(reportpacktemplateList);
		dashboardReport.setReports(report);
		dashboardReports.add(dashboardReport);
		userReport.setDashboardReports(dashboardReports);
		//reportpackTemplateVO.setJsonString(fetchReportPackTemplateMap);
		return userReport;
	}

	@Override
	public List<ReportpackTemplate> getReportpackTemplateBasedOnReportType(ReportpackTemplateVO reportpackTemplateVO,
			Set<String> reportTypeSet) throws Exception {
		return reportPackTemplateDao.getReportpackTemplateBasedOnReportType(reportpackTemplateVO, reportTypeSet);
	}

	@Override
	public ReportpackTemplate getReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		return reportPackTemplateDao.getReportPackTemplate(reportpackTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		List<ReportpackTemplate> reportpacktemplateList = getReportpackTemplate(reportpackTemplateVO);
		if (!ReportHelper.IsNullOrEmptyForCollection(reportpacktemplateList)) {
			reportpacktemplateList.forEach((reportpackTemplate) -> {
				reportPackTemplateDao.deleteReportPackTemplate(reportpackTemplate);
			});
		}
	}
}
