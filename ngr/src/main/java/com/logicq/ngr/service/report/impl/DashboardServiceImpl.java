package com.logicq.ngr.service.report.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.helper.RandomNumberHelper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.dao.DashboardDAO;
import com.logicq.ngr.dao.UserTemplateDao;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.report.UserTemplateKey;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.DashboardService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.vo.DashBoardVO;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	DashboardDAO dashboardDao;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	UserTemplateService userTemplateService;
	
	@Autowired
	UserTemplateDao userTemplateDao;
	
	@Autowired
	ReportHelper dynsHelper;

	@Autowired
	ReportHelper reportHelper;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Dashboard addDashboard(DashBoardVO dashBoardVO) throws Exception {
		Dashboard dashboard = new Dashboard();
		if (null != dashBoardVO) {
			UserTemplateVO userTemplateVO = new UserTemplateVO();
			userTemplateVO.setUserName(dashBoardVO.getUserName());
			dynsHelper.fetchUserFromUserName(userTemplateVO);

			if (!StringUtils.isEmpty(dashBoardVO.getDashBoardViewType())
					&& dashBoardVO.getDashBoardViewType().equalsIgnoreCase("default")) {
				resetDashboardViewType(userTemplateVO);
			}
			dashboard.setName(dashBoardVO.getDashBoardName());
			dashboard.setType(dashBoardVO.getDashBoardType());
			dashboard.setViewType(dashBoardVO.getDashBoardViewType());
			UserDetails userDetails = userAdminService.getUser(dashBoardVO.getUserName());
			dashboard.setUserDetails(userDetails);
			if (StringUtils.isEmpty(dashboard.getType())) {
				dashboard.setType("normal");
			}
			dashboard.setDashboardId(RandomNumberHelper.generateRandomAlphaNumericString());
			dashboardDao.addDashboard(dashboard);
		}
		return dashboard;
	}

	/**
	 * @param dashBoardVO
	 * @param listOfDashBoard
	 * @throws Exception
	 */
	public void resetDashboardViewType(UserTemplateVO userTemplateVO) throws Exception {

		List<Dashboard> listOfDashBoard = dashboardDao.getUserDashboards(userTemplateVO);
		for (Dashboard dashboardVal : listOfDashBoard) {
			if (!StringUtils.isEmpty(dashboardVal.getViewType())
					&& dashboardVal.getViewType().equalsIgnoreCase("default")) {
				dashboardVal.setViewType(null);
				dashboardDao.addDashboard(dashboardVal);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDashboard(UserTemplateVO userTemplateVO) throws Exception {
		UserDetails userDetails = userAdminService.getUser(userTemplateVO.getUserName());
		UserTemplate userTemplate = new UserTemplate();
		UserTemplateKey usertempkey = new UserTemplateKey();
		usertempkey.setDashboardId(userTemplateVO.getDashboardId());
		usertempkey.setUserDetails(userDetails);
		userTemplate.setUserTemplateKey(usertempkey);
		userTemplateDao.deleteUserTemplateBasedOnUserDetails(userTemplate);

		Dashboard dbDashboard = dashboardDao.getUserDashboard(userTemplateVO.getDashboardId(), userDetails.getId());
		dashboardDao.deleteDashboard(dbDashboard);

	}

	@Override
	public Dashboard getUserDashboard(String dashboardId, Long userId) throws Exception {
		return dashboardDao.getUserDashboard(dashboardId, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDashBoardViewType(DashBoardVO dashBoardVO) throws Exception {
		UserTemplateVO userTemplateVO = new UserTemplateVO();
		userTemplateVO.setUserName(dashBoardVO.getUserName());
		reportHelper.fetchUserFromUserName(userTemplateVO);

		if (!StringUtils.isEmpty(dashBoardVO.getDashBoardViewType())
				&& dashBoardVO.getDashBoardViewType().equalsIgnoreCase("default")) {
			resetDashboardViewType(userTemplateVO);
		}

		List<Dashboard> listOfDashBoard = dashboardDao.getUserDashboards(userTemplateVO);
		for (Dashboard dashboard : listOfDashBoard) {

			if (!StringUtils.isEmpty(dashBoardVO.getDashboardId()) && (null != dashBoardVO.getDashboardId())
					&& dashBoardVO.getDashboardId().equals(dashboard.getDashboardId())) {

				if (!ReportHelper.isEmpty(dashBoardVO.getDashBoardName())) {
					dashboard.setName(dashBoardVO.getDashBoardName());
				}
				if (!ReportHelper.isEmpty(dashBoardVO.getDashBoardViewType())) {
					dashboard.setViewType(dashBoardVO.getDashBoardViewType());
				}
				dashboardDao.addDashboard(dashboard);
			}
		}
	}

	@Override
	public void updateDashBoard(UserDetails userDetails) {
		List<Dashboard> dashboadList = userDetails.getDashboards();
		List <Dashboard> dashboards = new ArrayList<>();
		userDetails.getDashboards().forEach(dashboard -> {
			dashboard.setDeleted(true);
		});
	 	dashboardDao.updateDashboard(dashboards);
	}

	@Override
	public void deleteMarkDashboard(List<Dashboard> markDeletDashboardList) {
		dashboardDao.deleteMarkDashboard(markDeletDashboardList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveArrangementDetail(DashBoardVO dashBoardVo) throws Exception {
		UserTemplateVO userTemplateVO = new UserTemplateVO();
		if (!ReportHelper.isEmpty(dashBoardVo.getUserName())) {
			userTemplateVO.setUserName(dashBoardVo.getUserName());
		}
		reportHelper.fetchUserFromUserName(userTemplateVO);
		Dashboard dashboard = getUserDashboard(dashBoardVo.getDashboardId(), userTemplateVO.getUserId());
		if (!ReportHelper.isEmpty(dashBoardVo.getArrangement())) {
			dashboard.setArrangement(mapper.writeValueAsBytes(dashBoardVo.getArrangement()));
		}
	}
	
	@Override
	public List<Dashboard> getDashboardsAccodingToType(Set<String> dashboardTypes, Set<UserDetails> users)
			throws Exception {
		Set<Long> userIds = users.stream().map(UserDetails::getId).collect(Collectors.toSet());
		Map<String, Set> values = new HashMap();
		values.put("type", dashboardTypes);
		values.put("userDetails.id", userIds);

		return dashboardDao.getDashboardsAccodingToType(values);
	}

	@Override
	public void markDashboardsAndReportsAsDeleted(Set<UserDetails> users, Set<String> dashboadTypes) throws Exception {
		List<Dashboard> dashboards = getDashboardsAccodingToType(dashboadTypes, users);
		List<Dashboard> dashboardList = new ArrayList<>();
		if(dashboards!=null && !dashboards.isEmpty()) {
			dashboards.forEach(dashboard -> {
				dashboard.setDeleted(true);
				dashboardList.add(dashboard);
			});
		
			dashboardDao.updateDashboard(dashboardList);
		}
		
		List<UserTemplate> userTemplatesDR = new ArrayList();
		/**
		 * get dashboardReport dashboard, and check for userTemplates having
		 * same report type which need to be deleted. Then, mark these
		 * userTemplates as deleted
		 */
		List<Dashboard> dashboardReportDashboards = getDashboardsAccodingToType(
				new HashSet<String>(Arrays.asList(ReportType.DASH_BOARD.getValue())), users);
		dashboardReportDashboards.forEach(dRDash -> {
			userTemplatesDR.addAll(userTemplateService.getUserTemplatesOfDashboard(dRDash.getDashboardId()));
		});

		List<UserTemplate> markToBeDeletedUTs = userTemplatesDR.stream()
				.filter(elem -> (dashboadTypes.contains(elem.getReportType()))).collect(Collectors.toList());
		if(null != markToBeDeletedUTs && !markToBeDeletedUTs.isEmpty()) {
			Set<String> userTemplateIds = markToBeDeletedUTs.stream().map(UserTemplate::getUserTemplateKey)
					.map(UserTemplateKey::getUserTemplateId).collect(Collectors.toSet());
			userTemplateService.markUserTemplatesAsDeleted(userTemplateIds);
		}
	}

	@Override
	public void createDashboardsFromProfile(Set<UserDetails> users, Set<String> newDashboardsTypes) throws Exception {
		for (UserDetails user : users) {
			List<Dashboard> dashboards = new ArrayList<Dashboard>();
			newDashboardsTypes.forEach(dashboardType -> {
				Dashboard dashboard = reportHelper.prepareDashBoardReport(
						dashboardType + RandomNumberHelper.generateRandomNumber(), dashboardType, null, user);
				dashboards.add(dashboard);
			});
			user.setDashboards(dashboards);
			userAdminService.updateUserDetails(user);
		}
	}

	
}
