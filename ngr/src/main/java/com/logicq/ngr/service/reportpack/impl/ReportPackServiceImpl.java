package com.logicq.ngr.service.reportpack.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.DruidHelper;
import com.logicq.ngr.common.helper.RandomNumberHelper;
import com.logicq.ngr.constant.ReportpackEnum;
import com.logicq.ngr.dao.reportpack.ReportPackDao;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.ReportPackHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.druid.request.ReportRequest;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.report.Report;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.reportpack.ReportpackDetails;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.model.reportpack.ReportpackTemplateKey;
import com.logicq.ngr.model.response.BaseResponse;
import com.logicq.ngr.service.FeatureService;
import com.logicq.ngr.service.ProfileService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.service.reportpack.ReportPackTemplateService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.FeaturePropertyVO;
import com.logicq.ngr.vo.ReportPackDetailsVO;
import com.logicq.ngr.vo.ReportpackTemplateVO;
import com.logicq.ngr.vo.UserTemplateVO;

/**
 * 
 * This class deals with implementation of services related to ReportPack.
 *
 */
@Service
public class ReportPackServiceImpl implements ReportPackService {

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	ReportPackDao reportPackDao;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	ReportPackTemplateService reportPackTemplateService;

	@Autowired
	DruidHelper druidHelper;

	@Autowired
	ReportHelper dynsHelper;

	@Autowired
	FeatureService featureService;

	@Autowired
	ProfileService profileService;

	@Autowired
	ReportPackHelper reportPackHelper;

	private ObjectMapper mapper = ObjectFactory.getObjectMapper();

	/**
	 * This method is used to create reportpack Dashboard.
	 * 
	 * @param ReportPackDetailsVO
	 *            reportPackDetailsVO
	 * @return ReportpackDetails
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ReportPackDetailsVO createreportpack(ReportPackDetailsVO reportPackDetailsVO) throws Exception {
		UserDetails loggedInUserDetails = null;
		if (!ReportHelper.isEmpty(reportPackDetailsVO.getUserName())) {
			loggedInUserDetails = userAdminService.getUser(reportPackDetailsVO.getUserName());
		}
		saveReportpackDashboard(reportPackDetailsVO, loggedInUserDetails);
		return reportPackDetailsVO;
	}

	/**
	 * This method is helper method for saving reportpack Dashboard to Database.
	 * 
	 * @param ReportPackDetailsVO
	 *            reportPackDetailsVO, UserDetails loggedInUserDetails
	 * @return ReportpackDetails
	 */
	private void saveReportpackDashboard(ReportPackDetailsVO reportPackDetailsVO, UserDetails loggedInUserDetails)
			throws Exception {
		ReportpackDetails reportPackDetails = new ReportpackDetails();
		reportPackDetails.setName(reportPackDetailsVO.getName());
		reportPackDetails.setType(ReportpackEnum.Type.getValue());
		final String reportpackid = RandomNumberHelper.generateRandomAlphaNumericString();
		reportPackDetailsVO.setReportpackId(reportpackid);
		reportPackDetailsVO.setType(reportPackDetails.getType());
		reportPackDetails.setReportpackId(reportpackid);
		loggedInUserDetails.getProfile().getReportpacks().add(reportPackDetails);
		reportPackDetails.getProfiles().add(loggedInUserDetails.getProfile());
		reportPackDao.addReportpack(reportPackDetails);
	}

	/**
	 * This method is used to copy the selected Reports to ReportPack Dashboard.
	 * The reports to be copied can be a single report or list of reports
	 * 
	 * For resonse we have used UserTemplateVo because our ReportPackDetailsVO
	 * do not contains UserTemplatedId and UserId instead it contains list of
	 * UserTemplatedId and list of UserId
	 * 
	 * @param ReportPackDetailsVO
	 *            reportPackDetailsVO
	 * @return UserReport
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void moveReportPack(List<UserTemplateVO> userTemplateVoList) throws Exception {

		List<UserTemplate> userTemplateList = null;
		Set<String> destinationReportPackId = new HashSet<>();
		Set<String> userTemplateIdList = new HashSet<>();
		Set<String> reportTypeSet = new HashSet<>();
		UserDetails loggedInUserDetails = null;
		String sourceDashboardId = null;
		String username = null;
		for (UserTemplateVO userTemplateVo : userTemplateVoList) {
			destinationReportPackId.add(userTemplateVo.getDashboardId());
			if (!ReportHelper.isEmpty(userTemplateVo.getUserTemplateId())) {
				userTemplateIdList.add(userTemplateVo.getUserTemplateId());
			}
			if (StringUtils.isEmpty(sourceDashboardId) && !StringUtils.isEmpty(userTemplateVo.getSourceDashboardId())
					&& !StringUtils.isEmpty(userTemplateVo.getUserName()) && StringUtils.isEmpty(username)) {
				sourceDashboardId = userTemplateVo.getSourceDashboardId();
				username = userTemplateVo.getUserName();
			}
		}
		if (!ReportHelper.isEmpty(username)) {
			loggedInUserDetails = userAdminService.getUser(username);
		}
		FeatureDetailsVO featureDetails = featureService.getFeatureConfig(loggedInUserDetails);
		if (null != featureDetails) {
			List<FeaturePropertyVO> featurePropertyVOList = featureDetails.getReportFeatureList();
			for (FeaturePropertyVO featurePropertyVO : featurePropertyVOList) {
				reportTypeSet.add(featurePropertyVO.getName());
			}
		}

		UserTemplateVO userTemplateVo = new UserTemplateVO();
		if (!ReportHelper.isEmpty(sourceDashboardId)) {
			userTemplateVo.setDashboardId(sourceDashboardId);
			userTemplateVo.setUserName(userTemplateVoList.get(0).getUserName());
			dynsHelper.fetchUserFromUserName(userTemplateVo);
		}
		if (!userTemplateIdList.isEmpty()) {
			userTemplateList = userTemplateService.getUserTemplateforReportPack(userTemplateVo, userTemplateIdList,
					reportTypeSet);
		} else {
			userTemplateList = userTemplateService.searchUserTemplateforReportPack(userTemplateVo, reportTypeSet);
		}
		saveReportpackTemplate(destinationReportPackId, userTemplateList);
	}

	private void saveReportpackTemplate(Set<String> destinationReportpackIdList, List<UserTemplate> userTemplateList)
			throws Exception {
		for (String destinationReportpackId : destinationReportpackIdList) {
			if (!StringUtils.isEmpty(destinationReportpackId)) {
				for (UserTemplate userTemplate : userTemplateList) {
					ReportpackTemplate reportpackTemplate = new ReportpackTemplate();
					if (null == reportpackTemplate.getReportpackTemplateKey()) {
						reportpackTemplate.setReportpackTemplateKey(new ReportpackTemplateKey());
					}
					reportpackTemplate.setJsonString(userTemplate.getJsonString());
					reportpackTemplate.setReportType(userTemplate.getReportType());
					reportpackTemplate.getReportpackTemplateKey().setReportpackId(destinationReportpackId);
					reportpackTemplate.getReportpackTemplateKey()
							.setReportpackTemplateId(userTemplate.getUserTemplateKey().getUserTemplateId());
					reportPackTemplateService.saveReportpackTemplate(reportpackTemplate);
				}
			}
		}
	}

	/**
	 * This method is used to add reports to ReportpackTemplate Table.
	 * 
	 * @param ReportPackDetailsVO
	 *            reportPackDetailsVO
	 * @return DashboardReport
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserReport addreport(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		List<ReportpackTemplate> reportpackTemplateList = new ArrayList<>();
		final String reportpackTemplateid = RandomNumberHelper.generateRandomAlphaNumericString();
		UserDetails loggedInUserDetails = null;
		if (!ReportHelper.isEmpty(reportpackTemplateVO.getUserName())) {
			loggedInUserDetails = userAdminService.getUser(reportpackTemplateVO.getUserName());
		}
		reportpackTemplateVO.setReportpackTemplateId(reportpackTemplateid);
		ReportpackTemplate reportpackTemplate = reportPackTemplateService.saveReportpackTemplate(reportpackTemplateVO);

		if (null != reportpackTemplate) {
			reportpackTemplateList.add(reportpackTemplate);
		}
		Set<ReportpackDetails> reportPackDashboardSet = loggedInUserDetails.getProfile().getReportpacks();
		Set<ReportpackDetails> finalreportPackDashboardSet = null;
		if (reportPackDashboardSet != null || reportPackDashboardSet.size() != 0) {
			finalreportPackDashboardSet = reportPackDashboardSet.stream().filter(
					reportPack -> reportPack.getReportpackId().equalsIgnoreCase(reportpackTemplateVO.getReportpackId()))
					.collect(Collectors.toSet());
		}
		ReportpackDetails reportPackDashboard = finalreportPackDashboardSet.iterator().next();
		DashboardReport resultDashboard = populateReportpackDashboardReport(reportPackDashboard,
				reportpackTemplateList);
		dashboardReports.add(resultDashboard);
		userReport.setDashboardReports(dashboardReports);
		return userReport;
	}

	/**
	 * This method is used to get reportpackDashboard based on reportpackId
	 * 
	 * @param String
	 *            reportpackId
	 * @return List<ReportpackDetails>
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ReportpackDetails getReportPack(String reportpackId) throws Exception {
		if (!StringUtils.isEmpty(reportpackId) && null != reportpackId) {
			return reportPackDao.getReportPack(reportpackId);
		} else {
			new BusinessException("reportpackId  cant be null");
		}
		return null;
	}

	/**
	 * This method is used to allocate reportpackDashboards to user Profile.
	 * 
	 * @param ReportPackDetailsVO
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void assignReportpack(Set<String> reportPackList, Profile profile) throws Exception {
		Set<ReportpackDetails> reportpackSet = new HashSet<>();
		for (String reportPack : reportPackList) {
			ReportpackDetails dbreportpackdashboard = getReportPack(reportPack);
			if (!ReportHelper.isEmpty(dbreportpackdashboard)) {
				reportpackSet.add(dbreportpackdashboard);
			}
		}
		profile.setReportpacks(reportpackSet);
	}

	/**
	 * This method is used to get all reportpackDashboard.
	 * 
	 * @return List<ReportpackDetails>
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getAllReportPack(String userName) throws Exception {
		UserReport userReport = new UserReport();
		if (!ReportHelper.isEmpty(userName)) {
			userReport.setUserName(userName);
		}
		List<DashboardReport> resultDashboardList = new ArrayList<DashboardReport>();
		List<ReportpackDetails> reportpackDashboardList = reportPackDao.getReportPack();
		if (null != reportpackDashboardList) {
			for (ReportpackDetails reportPackDashboardDetails : reportpackDashboardList) {
				ReportpackTemplateVO reportpackTemplateVO = new ReportpackTemplateVO();
				reportpackTemplateVO.setReportpackId(reportPackDashboardDetails.getReportpackId());
				List<ReportpackTemplate> reportpacktemplateList = reportPackTemplateService
						.getReportpackTemplate(reportpackTemplateVO);
				DashboardReport resultDashboard = populateReportpackDashboardReport(reportPackDashboardDetails,
						reportpacktemplateList);
				resultDashboardList.add(resultDashboard);
			}
		}
		userReport.setDashboardReports(resultDashboardList);
		return userReport;
	}

	/**
	 * This method is used as helper method for populating DashboardReport.
	 * 
	 * @param ReportpackDetails
	 *            , List<ReportpackTemplate>
	 * @return DashboardReport
	 */
	private DashboardReport populateReportpackDashboardReport(ReportpackDetails reportPackDashboard,
			List<ReportpackTemplate> reportpackTemplateList) throws Exception {
		DashboardReport resultDashboard = new DashboardReport();
		resultDashboard.setId(reportPackDashboard.getReportpackId());
		resultDashboard.setName(reportPackDashboard.getName());
		resultDashboard.setType(reportPackDashboard.getType());
		resultDashboard.setViewType(reportPackDashboard.getViewType());
		resultDashboard.setReports(populateReportPackTemplates(reportpackTemplateList));
		return resultDashboard;
	}

	/**
	 * This method is used as helper method for populating ReportPackTemplates.
	 * 
	 * @param List<ReportpackTemplate>
	 * @return List<Report>
	 */
	private List<Report> populateReportPackTemplates(List<ReportpackTemplate> reportpackTemplateList) throws Exception {
		List<Report> reports = new ArrayList<>();
		for (ReportpackTemplate reportpackTemplate : reportpackTemplateList) {
			Report report = new Report();
			ReportRequest reportRequest = mapper.readValue(reportpackTemplate.getJsonString(),
					new TypeReference<ReportRequest>() {
					});
			BaseResponse baseRespone = new BaseResponse<>();
			baseRespone.setReportConfiguration(reportRequest);
			baseRespone.setUserTemplateId(reportpackTemplate.getReportpackTemplateKey().getReportpackTemplateId());
			report.setResponse(baseRespone);
			report.setReportType(reportpackTemplate.getReportType());
			reports.add(report);
		}
		return reports;
	}

	/**
	 * This method is used get specific ReportpackDashboard Report.
	 * 
	 * @param ReportpackTemplateVO
	 *            reportpackTemplateVO
	 * @return UserReport
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getspecificreportpack(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		DashboardReport dashboardReport = new DashboardReport();

		UserDetails loggedInUserDetails = null;
		if (!ReportHelper.isEmpty(reportpackTemplateVO.getUserName())) {
			loggedInUserDetails = userAdminService.getUser(reportpackTemplateVO.getUserName());
		}
		userReport.setUserName(loggedInUserDetails.getUserName());
		Set<ReportpackDetails> reportPackDashboardSet = loggedInUserDetails.getProfile().getReportpacks();
		Set<ReportpackDetails> finalreportPackDashboardSet = null;
		if (reportPackDashboardSet != null || reportPackDashboardSet.size() != 0) {
			finalreportPackDashboardSet = reportPackDashboardSet.stream().filter(
					reportPack -> reportPack.getReportpackId().equalsIgnoreCase(reportpackTemplateVO.getReportpackId()))
					.collect(Collectors.toSet());
		}
		ReportpackDetails reportPackDashboard = finalreportPackDashboardSet.iterator().next();

		if (reportPackDashboard != null) {
			dashboardReport.setId(reportPackDashboard.getReportpackId());
			dashboardReport.setName(reportPackDashboard.getName());
			dashboardReport.setType(reportPackDashboard.getType());
			dashboardReport.setViewType(reportPackDashboard.getViewType());
		}
		List<ReportpackTemplate> reportpacktemplateList = reportPackTemplateService
				.getReportpackTemplate(reportpackTemplateVO);

		if (!reportpacktemplateList.isEmpty()) {
			List<Report> reportList = null;
			reportList = reportPackHelper.preparereportpackReports(reportpacktemplateList);
			dashboardReport.setReports(reportList);
		}
		dashboardReports.add(dashboardReport);
		userReport.setDashboardReports(dashboardReports);
		return userReport;
	}

	/**
	 * This method is used to get ReportpackDashboardReports(used for Login
	 * purpose)
	 * 
	 * @param UserDetails
	 *            userDetails
	 * @return List<DashboardReport>
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DashboardReport> getReportpackDashboardReports(UserDetails userDetails) throws Exception {
		List<DashboardReport> reportPackDashboardReport = new ArrayList<>();
		List<ReportpackDetails> reportPackDashboardList = null;
		Set<String> reportTypeSet = new HashSet<>();
		if (userDetails.getProfile().getProfileId() != null) {

			// reportPackDashboardList =
			// getReportPack(userDetails.getProfile().getProfileId());
			reportPackDashboardList = new ArrayList<ReportpackDetails>(userDetails.getProfile().getReportpacks());

			FeatureDetailsVO featureDetails = featureService.getFeatureConfig(userDetails);
			if (null != featureDetails) {
				List<FeaturePropertyVO> featurePropertyVOList = featureDetails.getManagement();
				for (FeaturePropertyVO featurePropertyVO : featurePropertyVOList) {

					if (!ReportHelper.isEmpty(featurePropertyVO.getFeatures())
							|| !featurePropertyVO.getFeatures().isEmpty()
							|| featurePropertyVO.getFeatures().size() != 0) {
						List<Attribute> attributelist = featurePropertyVO.getFeatures();
						attributelist.forEach(attribute -> {
							if (!ReportHelper.isEmpty(attribute.getName())) {
								reportTypeSet.add(attribute.getName());
							}
						});
					}
				}
			}
			if (null != reportPackDashboardList) {
				for (ReportpackDetails reportPackDashboardDetails : reportPackDashboardList) {
					ReportpackTemplateVO reportpackTemplateVO = new ReportpackTemplateVO();
					reportpackTemplateVO.setReportpackId(reportPackDashboardDetails.getReportpackId());
					List<ReportpackTemplate> reportpacktemplateList = reportPackTemplateService
							.getReportpackTemplateBasedOnReportType(reportpackTemplateVO, reportTypeSet);
					DashboardReport resultDashboard = new DashboardReport();
					if (null != reportPackDashboardDetails) {
						resultDashboard.setId(reportPackDashboardDetails.getReportpackId());
						resultDashboard.setName(reportPackDashboardDetails.getName());
						resultDashboard.setType(reportPackDashboardDetails.getType());
						resultDashboard.setViewType(reportPackDashboardDetails.getViewType());
					}
					resultDashboard.setReports(populateReportPackTemplates(reportpacktemplateList));
					reportPackDashboardReport.add(resultDashboard);
				}
			}
		}
		return reportPackDashboardReport;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public DashboardReport getReportPackAccordingToFilter(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		DashboardReport dashboardReport = new DashboardReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedInUserDetails = userAdminService.getUser(username);

		Set<ReportpackDetails> reportPackDashboardSet = loggedInUserDetails.getProfile().getReportpacks();
		Set<ReportpackDetails> finalreportPackDashboardSet = null;
		if (reportPackDashboardSet != null || reportPackDashboardSet.size() != 0) {
			finalreportPackDashboardSet = reportPackDashboardSet.stream().filter(
					reportPack -> reportPack.getReportpackId().equalsIgnoreCase(reportpackTemplateVO.getReportpackId()))
					.collect(Collectors.toSet());
		}
		ReportpackDetails reportpackDetails = finalreportPackDashboardSet.iterator().next();

		dashboardReport.setId(reportpackDetails.getReportpackId());
		dashboardReport.setName(reportpackDetails.getName());
		dashboardReport.setType(reportpackDetails.getType());
		dashboardReport.setViewType(reportpackDetails.getViewType());

		ReportpackTemplate reportpacktemplate = reportPackTemplateService.getReportPackTemplate(reportpackTemplateVO);
		Map<String, Object> inputjson = reportpackTemplateVO.getJsonString();

		Map<String, Object> fetchedReportPackTemplateMap = mapper
				.readValue(new String(reportpacktemplate.getJsonString()), new TypeReference<Map<String, Object>>() {
				});
		fetchedReportPackTemplateMap.putAll(inputjson);

		reportpacktemplate.setJsonString(mapper.writeValueAsBytes(fetchedReportPackTemplateMap));

		List<ReportpackTemplate> reportpackTemplates = new ArrayList<>();
		reportpackTemplates.add(reportpacktemplate);
		if (!reportpackTemplates.isEmpty()) {
			List<Report> reportList = reportPackHelper.preparereportpackReports(reportpackTemplates);
			dashboardReport.setReports(reportList);
		}
		dashboardReports.add(dashboardReport);
		return dashboardReport;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteReportPack(ReportPackDetailsVO reportPackDetailsVO) throws Exception {
		ReportpackDetails reportpack = null;
		if (!ReportHelper.isEmpty(reportPackDetailsVO.getReportpackId())) {
			reportpack = getReportPack(reportPackDetailsVO.getReportpackId());
		}
		if (null != reportpack) {
			reportPackDao.deleteReportPack(reportpack);
		}
	}

}