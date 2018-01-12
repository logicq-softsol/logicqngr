package com.logicq.ngr.service.report.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.DateHelper;
import com.logicq.ngr.common.helper.DruidHelper;
import com.logicq.ngr.common.helper.RandomNumberHelper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.helper.ReportDownloadHelper;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.Validator;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.common.Configuration;
import com.logicq.ngr.model.druid.request.ReportRequest;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.report.Report;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.report.UserTemplateKey;
import com.logicq.ngr.model.response.BaseResponse;
import com.logicq.ngr.model.response.DruidResponse;
import com.logicq.ngr.service.FeatureService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.ReportService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.service.reportpack.ReportPackTemplateService;
import com.logicq.ngr.vo.ArrangementDetail;
import com.logicq.ngr.vo.ArrangementReportDetail;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
@Transactional
public class ReportServiceimpl implements ReportService {

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	UserAdminService userAdminService;



	@Autowired
	DruidHelper druidHelper;

	@Autowired
	ReportDownloadHelper reportDownloadHelper;

	@Autowired
	ReportHelper dynsHelper;

	@Autowired
	FeatureService featureService;

	@Autowired
	ReportHelper reportHelper;

	@Autowired
	ReportPackService reportPackService;

	@Autowired
	Validator validator;

	@Autowired
	ReportPackTemplateService reportPackTemplateService;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getUserReport(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		List<DashboardReport> reportPackDashboardReports = null;
		UserDetails userDetails = null;
		if (!StringUtils.isEmpty(userTemplateVO.getUserName())) {
			userDetails = userAdminService.getUser(userTemplateVO.getUserName());
			userReport.setUserName(userTemplateVO.getUserName());
			userReport.setIsEnabled(userDetails.getEnabled());
			userReport.setFirstName(userDetails.getFirstName());
			userReport.setLastName(userDetails.getLastName());
			userReport.setEmail(userDetails.getEmail());
			userReport.setLastLoggedIn(DateHelper.convertDateIntoString(userDetails.getLastLoggedIn()));
			if (null != userDetails) {
				userReport.setRoles(ReportHelper.splitStringWithComma(userDetails.getRole().getAllowedRoles()));
				userTemplateVO.setUserId(userDetails.getId());
				userReport.setFeatureConfig(featureService.getFeatureConfig(userDetails));

				// This call is used to add all Reports of ReportPack dashboard
				// to List<DashboardReport> dashboardReports.
				reportPackDashboardReports = reportPackService.getReportpackDashboardReports(userDetails);
			}
		}

		// assign all ReportpackDetails Reports to Final dashboardReports.
		if (null != reportPackDashboardReports) {
			dashboardReports.addAll(reportPackDashboardReports);
		}

		// becaz of below code dashboards are getting repeated for userDeatil.
		/*
		 * List<Dashboard> dashboards =
		 * userTemplateService.getUserDashboards(userTemplateVO);//userDetails.
		 * getDashboards(); if (null == dashboards) { dashboards =
		 * userTemplateService.getUserDashboards(userTemplateVO); }
		 */
		List<Dashboard> dashboards = userTemplateService.getUserDashboards(userTemplateVO);
		if (!dashboards.isEmpty()) {
			for (Dashboard dashboard : dashboards) {
				if (validator.isValidDashboard(dashboard)) {
					DashboardReport dashboardReport = new DashboardReport();
					if (null != dashboard) {
						dashboardReport.setId(dashboard.getDashboardId());
						dashboardReport.setName(dashboard.getName());
						dashboardReport.setType(dashboard.getType());
						dashboardReport.setViewType(dashboard.getViewType());
					}

					if (!StringUtils.isEmpty(dashboard.getDashboardId())) {
						userTemplateVO.setDashboardId(dashboard.getDashboardId());
					}
					List<UserTemplate> userTemplates = userTemplateService.searchUserTemplate(userTemplateVO);
					List<Report> reportList = getUserReportTemplates(userTemplates);
					dashboardReport.setReports(reportList);
					dashboardReports.add(dashboardReport);
				}
			}
			userReport.setDashboardReports(dashboardReports);
		}
		return userReport;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getSpecificUserReports(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		UserDetails userDetails = null;
		if (null != userTemplateVO && null != userTemplateVO.getUserName()) {
			userDetails = userAdminService.getUser(userTemplateVO.getUserName());

			dynsHelper.fetchUserFromUserName(userTemplateVO);
			userReport.setUserName(userTemplateVO.getUserName());

			List<DashboardReport> dashboardReports = new ArrayList<>();
			List<UserTemplate> listUserTemplate = userTemplateService.searchUserTemplate(userTemplateVO);
			Dashboard dashboard = userTemplateService.getUserDashboard(userTemplateVO);
			DashboardReport dashboardReport = new DashboardReport();
			dashboardReport.setId(userTemplateVO.getDashboardId());
			
			if (validator.isValidDashboard(dashboard)) {
				if (dashboard != null) {
					dashboardReport.setName(dashboard.getName());
					dashboardReport.setType(dashboard.getType());
					dashboardReport.setViewType(dashboard.getViewType());
				}

				if (!listUserTemplate.isEmpty()) {
					List<Report> reportList = prepareUserReports(listUserTemplate);
					dashboardReport.setReports(reportList);
				}
				dashboardReports.add(dashboardReport);
				userReport.setDashboardReports(dashboardReports);
			}else{
				throw new BusinessException(1030);
			}
				
		
		
		dashboardReport.setId(userTemplateVO.getDashboardId());
		if (dashboard != null) {
			dashboardReport.setName(dashboard.getName());
			dashboardReport.setType(dashboard.getType());
			dashboardReport.setViewType(dashboard.getViewType());

			if (StringUtils.isEmpty(userTemplateVO.getUserTemplateId())) {
				// assign arrangement value to dashboardReport
				populateArrangementDetail(listUserTemplate, dashboard, dashboardReport);
			}
		}
		
	
		
		}
		return userReport;	
	}

	// Method used to populate ArrangementDetail of DashboardReport.
	private void populateArrangementDetail(List<UserTemplate> listUserTemplate, Dashboard dashboard,
			DashboardReport dashboardReport) throws Exception {
		if (ReportHelper.isEmpty(dashboard.getArrangement()) || dashboard.getArrangement().length == 0) {
			dashboardReport.setArrangement(null);
		} else {
			List<ArrangementDetail> dbArrangement = mapper.readValue(new String(dashboard.getArrangement()),
					new TypeReference<List<ArrangementDetail>>() {
					});
			List<ArrangementReportDetail> reports = new ArrayList<>();
			List<ArrangementReportDetail> reportsFinal = new ArrayList<>();
			dbArrangement.forEach(arrangementDetail -> {
				reports.addAll(arrangementDetail.getReports());
			});
			if (null != reports && !reports.isEmpty()) {
				listUserTemplate.forEach(userTemplate -> {
					if (!compareArrangementDeatil(userTemplate.getUserTemplateKey().getUserTemplateId(), reports)) {
						ArrangementReportDetail arrangementReportDetail = new ArrangementReportDetail();
						arrangementReportDetail.setReportId(userTemplate.getUserTemplateKey().getUserTemplateId());
						try {
							populateArrangementReportType(arrangementReportDetail, userTemplate);
						} catch (Exception e) {
							e.printStackTrace();
						}
						reportsFinal.add(arrangementReportDetail);
					}
				});
			}
			dbArrangement.get(0).getReports().addAll(reportsFinal);
			dashboardReport.setArrangement(dbArrangement);
		}
	}

	// used for arrangement. Method used to check whether userTemplateid is
	// present in ArrangementReportDetailList
	private boolean compareArrangementDeatil(String userTemplateid, List<ArrangementReportDetail> reports) {
		Predicate<ArrangementReportDetail> p = arrangementReportDetail -> arrangementReportDetail.getReportId()
				.equalsIgnoreCase(userTemplateid);
		return reports.stream().anyMatch(p);
	}

	// used for arrangement
	private void populateArrangementReportType(ArrangementReportDetail arrangementReportDetail,
			UserTemplate userTemplate) throws JsonParseException, JsonMappingException, IOException {
		ReportRequest reportRequest = new ReportRequest();
		if (null != userTemplate.getJsonString()) {
			reportRequest = mapper.readValue(userTemplate.getJsonString(), new TypeReference<ReportRequest>() {
			});
			Configuration configuration = reportRequest.getConfiguration();
			arrangementReportDetail.setType(configuration.getSubType());
		}
	}

	private List<Report> prepareUserReports(List<UserTemplate> usertemplates) throws Exception {
		List<Report> reports = new ArrayList<>();
		List<UserTemplate> alramtemplates = new ArrayList<>();
		for (UserTemplate usrTemplate : usertemplates) {
			if (validator.isValidUserTemplate(usrTemplate)) {
				Report report = new Report();
				ReportRequest reportRequest = mapper.readValue(usrTemplate.getJsonString(),
						new TypeReference<ReportRequest>() {
						});
				// Set Report Base response for all druid request
				DruidResponse druidResponse = new DruidResponse();
				druidResponse.setUserTemplateId(usrTemplate.getUserTemplateKey().getUserTemplateId());

				String intervals = reportRequest.getIntervals();
				if (!StringUtils.isEmpty(intervals)) {
					reportRequest.setIntervals(druidHelper.generateIntervalString(intervals));
				}

				if (!ReportType.ALRAM.getValue().equalsIgnoreCase(usrTemplate.getReportType())) {
					druidResponse.setResponse(druidHelper.prepareDruidResponse(reportRequest));
					reportRequest.setIntervals(intervals); // interval string
															// like
					// 1 day' set in UI response
					druidResponse.setReportConfiguration(reportRequest);
					report.setResponse(druidResponse);
					report.setReportType(usrTemplate.getReportType());
					reports.add(report);
				} else {

					alramtemplates.add(usrTemplate);
				}
			}
		}
	
		return reports;
	}

	private List<Report> getUserReportTemplates(List<UserTemplate> usertemplates) throws Exception {
		List<Report> reports = new ArrayList<>();
		for (UserTemplate usrTemplate : usertemplates) {
			Report report = new Report();
			ReportRequest reportRequest = mapper.readValue(usrTemplate.getJsonString(),
					new TypeReference<ReportRequest>() {
					});
			BaseResponse baseRespone = new BaseResponse<>();
			baseRespone.setReportConfiguration(reportRequest);
			baseRespone.setUserTemplateId(usrTemplate.getUserTemplateKey().getUserTemplateId());
			report.setResponse(baseRespone);
			reports.add(report);
		}
		return reports;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		UserTemplate userTemplate = new UserTemplate();
		UserTemplateKey userTemplateKey = new UserTemplateKey();

		if (null != userTemplateVO.getDashboardId()) {
			userTemplateKey.setDashboardId(userTemplateVO.getDashboardId());
		}
		if (null != userTemplateVO.getUserTemplateId()) {
			userTemplateKey.setUserTemplateId(userTemplateVO.getUserTemplateId());
		}
		userTemplate.setUserTemplateKey(userTemplateKey);
		userTemplateService.deleteUserTemplate(userTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getReportAccordingToFilter(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();

		if (null != userTemplateVO && null != userTemplateVO.getUserName()) {
			dynsHelper.fetchUserFromUserName(userTemplateVO);
			userReport.setUserName(userTemplateVO.getUserName());
			List<DashboardReport> dashboardReports = new ArrayList<>();
			DashboardReport dashboardReport = new DashboardReport();
			Dashboard dashboard = userTemplateService.getUserDashboard(userTemplateVO);
			if (null != dashboard && validator.isValidDashboard(dashboard)) {
				dashboardReport.setId(userTemplateVO.getDashboardId());
				dashboardReport.setName(dashboard.getName());
				dashboardReport.setType(dashboard.getType());
				dashboardReport.setViewType(dashboard.getViewType());

				UserTemplate fetchedTemplate = userTemplateService.getUserTemplate(userTemplateVO);
				Map<String, Object> inputjson = userTemplateVO.getJsonString();
				Map<String, Object> fetchedTemplateMap = mapper.readValue(new String(fetchedTemplate.getJsonString()),
						new TypeReference<Map<String, Object>>() {
						});
				fetchedTemplateMap.putAll(inputjson);
				/**
				 * Need to check whether json is replaced in DB. It should not
				 * replace
				 */
				fetchedTemplate.setJsonString(mapper.writeValueAsBytes(fetchedTemplateMap));

				List<UserTemplate> userTemplates = new ArrayList<>();
				userTemplates.add(fetchedTemplate);
				if (!userTemplates.isEmpty()) {
					List<Report> reportList = prepareUserReports(userTemplates);
					dashboardReport.setReports(reportList);
				}
				dashboardReports.add(dashboardReport);
				userReport.setDashboardReports(dashboardReports);
			} else {
				throw new BusinessException(1030);
			}

		}
		return userReport;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserReport addUserReport(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		userReport.setUserName(userTemplateVO.getUserName());
		dynsHelper.prepareBaseReport(userReport, userTemplateVO);

		List<DashboardReport> dashboardReports = new ArrayList<>();
		DashboardReport dashboardReport = new DashboardReport();
		Dashboard dashboard = userTemplateService.getUserDashboard(userTemplateVO);
		Map<String, Object> inputjson = userTemplateVO.getJsonString();

		// need to modfiy this code
		UserTemplate userTemplate = new UserTemplate();
		UserTemplateKey userTemplateKey = new UserTemplateKey();
		userTemplateKey.setUserDetails(dashboard.getUserDetails());
		userTemplate.setUserTemplateKey(userTemplateKey);

		// prepare Save User Template
		userTemplate = prepareUserTemplateForSave(userTemplate, userTemplateVO, inputjson);

		List<UserTemplate> userTemplates = new ArrayList<>();
		userTemplates.add(userTemplate);
		if (!userTemplates.isEmpty()) {
			dashboardReport.setReports(prepareUserReports(userTemplates));
		}

		if (null != dashboard) {
			dashboardReport.setId(dashboard.getDashboardId());
			dashboardReport.setName(dashboard.getName());
			dashboardReport.setType(dashboard.getType());
			dashboardReport.setViewType(dashboard.getViewType());
		}

		dashboardReports.add(dashboardReport);
		userReport.setDashboardReports(dashboardReports);
		userTemplateService.saveUserTemplate(userTemplate);
		return userReport;
	}

	/**
	 * Call comes here when addUserReport is invoked
	 * 
	 * @param userTemplateVO
	 * @param inputjson
	 * @return
	 * @throws JsonProcessingException
	 */
	private UserTemplate prepareUserTemplateForSave(UserTemplate userTemplate, UserTemplateVO userTemplateVO,
			Map<String, Object> inputjson) throws Exception {

		if (null == userTemplateVO.getUserTemplateId()) {
			// During edit UserReport a invoked
			if (!StringUtils.isEmpty(userTemplateVO.getDashboardId())
					&& !StringUtils.isEmpty(userTemplateVO.getUserId())) {
				userTemplate.getUserTemplateKey().setDashboardId(userTemplateVO.getDashboardId());
			}
			userTemplate.getUserTemplateKey().setUserTemplateId(RandomNumberHelper.generateRandomAlphaNumericString()); // During
			// addUserReport
		}
		userTemplate.setJsonString((mapper.writeValueAsBytes(inputjson)));
		// userTemplate.setReportType(String.valueOf(((Map<String, Object>)
		// inputjson.get("configuration")).get("type")));
		userTemplate.setReportType(userTemplateVO.getType());
		userTemplate.getUserTemplateKey().setDashboardId(userTemplateVO.getDashboardId());
		userTemplate.setReportType(userTemplateVO.getReportType());
		return userTemplate;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserReport editUserReport(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();

		dynsHelper.fetchUserFromUserName(userTemplateVO);
		userReport.setUserName(userTemplateVO.getUserName());

		List<DashboardReport> dashboardReports = new ArrayList<>();
		DashboardReport dashboardReport = new DashboardReport();
		Dashboard dashboard = userTemplateService.getUserDashboard(userTemplateVO);

		UserTemplate usertemplate = userTemplateService.getUserTemplate(userTemplateVO);
		Map<String, Object> fetchedUserTemplateMap = mapper.readValue(usertemplate.getJsonString(),
				new TypeReference<Map<String, Object>>() {
				});
		Map<String, Object> inputjsonmap = userTemplateVO.getJsonString();

		fetchedUserTemplateMap.putAll(inputjsonmap);
		// need to modfiy this code

		usertemplate = prepareUserTemplateForSave(usertemplate, userTemplateVO, fetchedUserTemplateMap);

		List<UserTemplate> userTemplates = new ArrayList<>();
		userTemplates.add(usertemplate);
		if (!userTemplates.isEmpty()) {
			List<Report> reportList = prepareUserReports(userTemplates);
			dashboardReport.setReports(reportList);
		}

		dashboardReport.setId(dashboard.getDashboardId());
		dashboardReport.setName(dashboard.getName());
		dashboardReport.setType(dashboard.getType());
		dashboardReport.setViewType(dashboard.getViewType());
		dashboardReports.add(dashboardReport);
		userReport.setDashboardReports(dashboardReports);
		userTemplateService.editUserTemplate(usertemplate);
		return userReport;
	}

}