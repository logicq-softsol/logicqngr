package com.logicq.ngr.service.report.impl;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.dao.DashboardDAO;
import com.logicq.ngr.dao.TemplateDao;
import com.logicq.ngr.dao.UserAdminDao;
import com.logicq.ngr.dao.UserTemplateDao;
import com.logicq.ngr.model.AbstractTemplate;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.report.UserTemplateKey;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.AbstractTemplateService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
@Transactional
public class UserTemplateServiceImpl implements UserTemplateService {

	@Autowired
	UserTemplateDao userTemplateDao;

	@Autowired
	DashboardDAO dashboardDao;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	AbstractTemplateService abstractTemplateService;

	@Autowired
	UserAdminDao userAdminDao;

	private ObjectMapper mapper = ObjectFactory.getObjectMapper();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplate> searchUserTemplate(UserTemplateVO userTemplateVO) throws Exception {

		return userTemplateDao.searchUserTemplate(userTemplateVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		userTemplateDao.editUserTemplate(userTemplateVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		userTemplateDao.saveUserTemplate(userTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Dashboard> getUserDashboards(UserTemplateVO userTemplateVO) throws Exception {
		return dashboardDao.getUserDashboards(userTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplate> getUserTemplatesOfDashboard(String dashboardId) {
		return userTemplateDao.getUserTemplatesOfDashboard(dashboardId);
	}

	/*
	 * @Override public Dashboard getUserDashboard(String dashboardId, String
	 * userId) throws Exception { return
	 * dashboardDao.getUserDashboard(dashboardId,userId); }
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveUserTemplate(UserTemplate userTemplate) throws Exception {
		userTemplateDao.saveUserTemplate(userTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserTemplate getUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		return userTemplateDao.getUserTemplate(userTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplateVO> fetchUserTemplate(UserTemplateVO userTemplateVO) {
		return userTemplateDao.fetchUserTemplate(userTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUserTemplate(UserTemplate userTemplate) throws Exception {
		userTemplateDao.deleteUserTemplate(userTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editUserTemplate(UserTemplate usertemplate) {
		userTemplateDao.editUserTemplate(usertemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Dashboard getUserDashboard(UserTemplateVO userTemplateVO) throws Exception {
		return dashboardDao.getUserDashboard(userTemplateVO.getDashboardId(), userTemplateVO.getUserId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void copyUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		UserDetails userDetails = userAdminService.getUser(userTemplateVO.getUserName());
		Dashboard dashboard = dashboardDao.getUserDashboard(userTemplateVO.getDashboardId(), userDetails.getId());
		userTemplateVO.setDashboardId(userTemplateVO.getSourceDashboardId());
		UserTemplate dbUserTemplate = userTemplateDao.getUserTemplate(userTemplateVO);
		UserTemplate usertemplate = new UserTemplate();
		UserTemplateKey usertempkey = new UserTemplateKey();
		usertempkey.setUserTemplateId(dbUserTemplate.getUserTemplateKey().getUserTemplateId());
		usertempkey.setDashboardId(dashboard.getDashboardId());
		// usertempkey.setTemplateId(dbUserTemplate.getUserTemplateKey().getTemplateId());
		usertempkey.setUserDetails(userDetails);
		usertemplate.setUserTemplateKey(usertempkey);
		usertemplate.setReportType(dbUserTemplate.getReportType());
		usertemplate.setJsonString(dbUserTemplate.getJsonString());
		userTemplateDao.saveUserTemplate(usertemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveAlarmTemplate(Dashboard alarmDashBoard) throws Exception {
		UserTemplateVO userTemplateVO = new UserTemplateVO();
		AbstractTemplate alarmTemplate = abstractTemplateService.getAlarmTemplate(alarmDashBoard.getType());
		Map<String, Object> fetchedAlramTemplate = mapper.readValue(alarmTemplate.getJson(),
				new TypeReference<Map<String, Object>>() {
				});
		userTemplateVO.setUserId(alarmDashBoard.getUserDetails().getId());
		userTemplateVO.setUserName(alarmDashBoard.getUserDetails().getUserName());
		userTemplateVO.setDashboardId(alarmDashBoard.getDashboardId());
		userTemplateVO.setTemplateId(String.valueOf(alarmTemplate.getId()));
		userTemplateVO.setJsonString(fetchedAlramTemplate);
		if (ReportType.REALTIME.getValue().equals(alarmDashBoard.getViewType())) {
			userTemplateVO.setReportType(alarmDashBoard.getViewType());
		} else {
			userTemplateVO.setReportType(ReportType.ALRAM.getValue());
		}

		saveUserTemplate(userTemplateVO);
	}

	@Override
	public List<UserTemplate> searchUserTemplateforReportPack(UserTemplateVO userTemplateVO, Set<String> reportTypeSet)
			throws Exception {
		return userTemplateDao.searchUserTemplateforReportPack(userTemplateVO, reportTypeSet);

	}

	/**
	 * This method is added for Report Pack Feature to get Multiple Reports
	 * based on dashboardId and list of usertemplateIds
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplate> getUserTemplateforReportPack(UserTemplateVO userTemplateVo,
			Set<String> userTemplateIdList, Set<String> reportTypeSet) throws Exception {
		return userTemplateDao.getUserTemplateforReportPack(userTemplateVo, userTemplateIdList, reportTypeSet);
	}

	@Override
	public void deleteUserTemplate(UserDetails userDtl) {
		userTemplateDao.deleteUserTemplate(userDtl);

	}

	@Override
	public void deleteUserTemplateByDashboardId(List<String> dashboardIds) throws Exception {
		if (dashboardIds != null && !dashboardIds.isEmpty()) {
			Map<String, Set> values = new HashMap();
			values.put("userTemplateKey.dashboardId", new HashSet<String>(dashboardIds));
			List<UserTemplate> usertemplates = userTemplateDao.getUserTemplateByDashboardIds(values);
			userTemplateDao.deleteUserTemplateByDashboardId(usertemplates);
		}
	}

	@Override
	public void removeMarkDeletedUserTemplates(List<UserDetails> userDtls) throws Exception {
		if (userDtls != null && !userDtls.isEmpty()) {
			List<Long> userIds = userDtls.stream().map(UserDetails::getId).collect(Collectors.toList());
			List<UserTemplate> userTemplates = userTemplateDao.getMarkDeleteUserTemplate(userIds);
			if (userTemplates != null && !userTemplates.isEmpty()) {
				userTemplateDao.removeMarkDeletedUserTemplates(userTemplates);
			}
		}
	}

	@Override
	public void markUserTemplatesAsDeleted(Set<String> userTemplateIds) {
		userTemplateDao.markUserTemplatesAsDeleted(userTemplateIds);
	}

}
