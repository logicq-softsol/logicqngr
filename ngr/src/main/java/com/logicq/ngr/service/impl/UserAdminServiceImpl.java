package com.logicq.ngr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.client.ldap.LDAPAuthenticationProvider;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.DateHelper;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.dao.DashboardDAO;
import com.logicq.ngr.dao.RoleDao;
import com.logicq.ngr.dao.UserAdminDao;
import com.logicq.ngr.helper.DynsHelper;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.service.ProfileService;
import com.logicq.ngr.service.RoleService;
import com.logicq.ngr.service.StorageDetailsService;
import com.logicq.ngr.service.TaskService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.DashboardService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.util.UserFactory;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.FeaturePropertyVO;
import com.logicq.ngr.vo.ProfileUserVO;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.RoleUserVo;
import com.logicq.ngr.vo.UserDetailVO;
import com.logicq.ngr.vo.UserVO;

import javassist.NotFoundException;

/**
 * 
 * @author 611164825
 *
 */
@Service
@Transactional
public class UserAdminServiceImpl implements UserAdminService {

	@Autowired
	UserAdminDao userAdminDao;

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	RoleService roleService;

	@Autowired
	ProfileService profileService;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	DashboardDAO dashboardDao;

	@Autowired
	ReportHelper reportHelper;

	@Autowired
	RoleDao roleDao;

	@Autowired
	DynsHelper dynsHelper;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	TaskService taskService;

	@Autowired
	StorageDetailsService storageDetailsService;

	private ObjectMapper mapper = ObjectFactory.getObjectMapper();
	private static final Logger logger = Logger.getLogger(UserAdminServiceImpl.class);

	/**
	 * @return
	 * @throws Exception
	 * 
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserDetails addUser(UserDetails user) throws Exception {
		LDAPAuthenticationProvider ldapAuthenticationProvider = new LDAPAuthenticationProvider();
		String ein = ldapAuthenticationProvider.validateUser(user.getUserName());
		if (StringUtils.isEmpty(ein)) {
			throw new NotFoundException("Boat Id " + user.getUserName() + " is not valid !");
		} else {
			user.setEin(ein);
		}
		// Prepare Dashboard for User
		List<Dashboard> dashboards = new ArrayList<Dashboard>();
		Profile profile = user.getProfile();
		if (null != profile && null != profile.getProfileId()) {
			profile = profileService.getProfile(profile.getProfileId());
			// this code will change after ID implementation
			FeatureDetailsVO featureDetails = mapper.readValue(new String(profile.getFeatures()),
					new TypeReference<FeatureDetailsVO>() {
					});
			List<FeaturePropertyVO> featurPropertyList = featureDetails.getReportFeatureList();

			if (null != featurPropertyList && !featurPropertyList.isEmpty()) {
				for (FeaturePropertyVO featurProperty : featurPropertyList) {
					List<Attribute> dashboardFeaturelist = featurProperty.getDashboard();
					List<Attribute> reportFeaturelist = featurProperty.getReport();
					if (null != dashboardFeaturelist && !dashboardFeaturelist.isEmpty()) {
						Dashboard dashboard = reportHelper.prepareDashBoardReport(featurProperty.getDisplayName(),
								featurProperty.getName(), ReportType.DEFAULT, user);
						dashboards.add(dashboard);
					}

					if (null != reportFeaturelist && !reportFeaturelist.isEmpty()) {
						for (Attribute reportfeature : reportFeaturelist) {
							if (ReportType.DYNAMIC_REPORT.getValue().equals(reportfeature.getName())) {
								Dashboard dashboard = reportHelper.prepareDashBoardReport(
										featurProperty.getDisplayName(), featurProperty.getName(), null, user);
								dashboards.add(dashboard);
								break;
							}
						}

					}
				}
			}
			user.setDashboards(dashboards);
			// User role set according to User name
			addRoleForUser(user);
			// User add
			user.setEnabled(Boolean.TRUE);
			userAdminDao.addUser(user);
		}
		return userAdminDao.getUser(user.getUserName());
	}

	public void addRoleForUser(UserDetails user) throws Exception {
		Role role = roleService.getRole(user.getRole().getName());
		if (null != role) {
			user.getRole().setRoleId(role.getRoleId());
		} else {
			throw new BusinessException("role can not be null.");
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	@ExceptionHandler(Exception.class)
	public UserDetailVO updateUser(UserDetailVO userDetailVO) throws Exception {
		UserDetails userDetails = null;
		UserDetailVO userDetailVoInfo = new UserDetailVO();
		UserDetails loggedInUserDetails = getUser(securityHelper.getUserNameFromSecurityContext());
		String allowedRoles = loggedInUserDetails.getRole().getAllowedRoles();

		if (!StringUtils.isEmpty(userDetailVO.getUserName())) {
			userDetails = (UserDetails) getUser(userDetailVO.getUserName());
		} else {
			throw new BusinessException(1014);
		}

		if (!StringUtils.isEmpty(userDetailVO.getRoleVO())) {

			UserDetails userdetail = getUser(userDetailVO.getUserName());
			Role dbRole = roleService.getRole(userdetail.getRole().getName());
			List<Role> roleList = roleService.getRoles(allowedRoles);

			if (roleList.contains(dbRole)) {
				for (Role allowedRole : roleList) {

					if (userDetailVO.getRoleVO().getName().equalsIgnoreCase((allowedRole.getName()))) {

						userDetails.setRole(roleDao.getRole(userDetailVO.getRoleVO().getName()));
						if (!StringUtils.isEmpty(userDetailVO.getProfileVO())) {
							Profile fetchedProfile = profileService.getProfile(userDetailVO.getProfileVO().getId());
							profileService.assignProfile(userdetail, fetchedProfile);
							userDetails.setProfile(fetchedProfile);
						}
					}
				}
			} else {
				throw new BusinessException(1013);
			}
		}

		if (userDetailVO.getDob() != null) {
			userDetails.setDob(userDetailVO.getDob());
		}
		if (!StringUtils.isEmpty(userDetailVO.getFirstName())) {
			userDetails.setFirstName(userDetailVO.getFirstName());
		}
		if (!StringUtils.isEmpty(userDetailVO.getLastName())) {
			userDetails.setLastName(userDetailVO.getLastName());
		}
		if (!StringUtils.isEmpty(userDetailVO.getEmail())) {
			userDetails.setEmail(userDetailVO.getEmail());
		}
		if (!StringUtils.isEmpty(userDetailVO.getMobileNumber())) {
			userDetails.setMobilenumber(userDetailVO.getMobileNumber());
		}
		if (!StringUtils.isEmpty(userDetailVO.getLastLoggedIn())) {
			userDetails.setLastLoggedIn(userDetailVO.getLastLoggedIn());
		}
		if (!ReportHelper.isEmpty(userDetailVO.getIsActive()))
			userDetails.setIsActive(userDetailVO.getIsActive());
		userAdminDao.updateUser(userDetails);
		UserVO uservo = UserFactory.create(userDetails);
		LoadApplicationData.userMap.put(userDetails.getUserName(), uservo);
		dynsHelper.populateUserDetailVO(userDetails, userDetailVoInfo);
		return userDetailVoInfo;
	}

	/**
	 * 
	 */

	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void markAsUserDelete(String userName) throws Exception {
		if (!StringUtils.isEmpty(userName)) {
			UserDetails userDetails = (UserDetails) getUser(userName);
			List<Dashboard> dashboards = userDetails.getDashboards();

			dashboardService.updateDashBoard(userDetails);
			taskService.updateUserTask(userDetails);
			storageDetailsService.updateStorageDetails(userDetails);

			userDetails.setDeleted(true);
			userDetails.setEnabled(false);
			userAdminDao.updateUserdetails(userDetails);

		}
	}

	/**
	 * 
	 */
	@ExceptionHandler(Exception.class)
	public List<UserDetails> getAllUser(String allowedRole) {
		return userAdminDao.getAllUser(allowedRole);
	}

	@Override
	public UserDetails getUser(String userName) {
		return userAdminDao.getUser(userName);
	}

	@Override
	public List<UserVO> getActiveUsers(String role) {
		List<UserVO> userVoList = new ArrayList<>();
		List<UserDetails> userDetailList = userAdminDao.getActiveUsers(role);
		userDetailList.forEach((user) -> {
			UserVO userVO = UserFactory.create(user);
			userVoList.add(userVO);
		});

		return userVoList;
	}

	@Override
	public List<UserDetails> getAllUsers() {
		return userAdminDao.getAllUsers();
	}

	@Override
	public List<UserDetails> getUsers(long profileId) {
		return userAdminDao.getUsers(profileId);
	}

	@Override
	public List<ProfileUserVO> getProfilesAccordingToRole(Role userrole) {
		List<ProfileUserVO> profileUserList = new ArrayList<>();
		if (!StringUtils.isEmpty(userrole.getAllowedRoles())) {
			String allowedRole = userrole.getAllowedRoles();
			List<Role> roles = roleService.getRoles(allowedRole);
			roles.forEach((role) -> {
				role.getProfiles().forEach((profile) -> {
					ProfileUserVO profileUserVo = new ProfileUserVO();
					ProfileVO profileVo = new ProfileVO();
					profileVo.setId(profile.getProfileId());
					profileVo.setName(profile.getName());
					profileUserVo.setProfileVO(profileVo);
					RoleUserVo roleUserVo = new RoleUserVo();
					Set<UserDetails> userDetailsList = profile.getUsers();
					roleUserVo.setTotalUsers(userDetailsList.size());
					for (UserDetails user : userDetailsList) {
						if (user.getEnabled()) {
							roleUserVo.setEnabledUsers(Math.addExact(roleUserVo.getEnabledUsers(), 1));
						} else {
							roleUserVo.setDisabledUsers(Math.addExact(roleUserVo.getDisabledUsers(), 1));
						}
					}
					profileUserVo.setRoleUserVO(roleUserVo);
					profileUserList.add(profileUserVo);

				});
			});
		}
		return profileUserList;
	}

	@Override
	public List<RoleUserVo> getUsersAccordingToAllowedRole(String allowedRole) {
		List<Role> roleList = roleService.getRoles(allowedRole);
		List<RoleUserVo> roleUserVoList = new ArrayList<>();
		roleList.forEach((role) -> {
			RoleUserVo roleUserVo = new RoleUserVo();
			List<UserDetails> userList = role.getUserdetails();
			roleUserVo.setRoleType(role.getName());
			roleUserVo.setTotalUsers(userList.size());
			for (UserDetails user : userList) {
				if (user.getEnabled()) {
					roleUserVo.setEnabledUsers(Math.addExact(roleUserVo.getEnabledUsers(), 1));
				} else {
					roleUserVo.setDisabledUsers(Math.addExact(roleUserVo.getDisabledUsers(), 1));
				}
			}
			roleUserVoList.add(roleUserVo);
		});

		return roleUserVoList;
	}

	@Override
	public void enableDisableUser(UserDetails userVo) {

		userAdminDao.enableDisableUser(userVo);
	}

	@Override
	public UserVO getUserInformation(String userName) {
		UserVO userVO = new UserVO();
		UserDetails ud = userAdminDao.getUser(userName);
		userVO.setFirstname(ud.getFirstName());
		userVO.setLastname(ud.getLastName());
		userVO.setEmail(ud.getEmail());
		userVO.setRole(ud.getRole().getName());
		userVO.setEin(ud.getEin());
		userVO.setUsername(ud.getUserName());
		userVO.setMobileNumber(ud.getMobilenumber());
		userVO.setDob(DateHelper.convertDateIntoString(ud.getDob()));
		return userVO;
	}

	@Override
	public UserVO updateUser(UserVO userVO, String userName) throws Exception {
		UserVO userRes = new UserVO();
		UserDetails details = null;
		UserDetails userDetails = userAdminDao.getUser(userName);
		userDetails.setEmail(userVO.getEmail());
		userDetails.setFirstName(userVO.getFirstname());
		userDetails.setLastName(userVO.getLastname());
		userDetails.setMobilenumber(userVO.getMobileNumber());
		details = userAdminDao.updateUser(userDetails);
		userRes.setFirstname(details.getFirstName());
		userRes.setLastname(details.getLastName());
		userRes.setEmail(details.getEmail());
		userRes.setMobileNumber(details.getMobilenumber());
		return userRes;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateUserDetails(UserDetails userDetails) throws Exception {
		if (!userDetails.getIsActive()) {
			userDetails.setLastLoggedIn(new Date());
		}
		userAdminDao.update(userDetails);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void handleMarkDelete() {
		List<UserDetails> userList = userAdminDao.getMarkAsDeletedUser();
		List<UserDetails> deletedUserList = new ArrayList<>();
		List<Dashboard> dashboardList = new ArrayList<>();
		List<Dashboard> markDeleteDashboardList = new ArrayList<>();
		List<String> dashboardIds = new ArrayList<String>();

		if (null != userList && !userList.isEmpty()) {
			userList.forEach(user -> {
				dashboardList.addAll(user.getDashboards());
				if (user.isDeleted()) {
					deletedUserList.add(user);
				}
			});
			dashboardList.forEach(dashboard -> {
				if (dashboard.isDeleted()) {
					markDeleteDashboardList.add(dashboard);
					dashboardIds.add(dashboard.getDashboardId());
				}
			});

			try {

				dashboardService.deleteMarkDashboard(markDeleteDashboardList);
				userTemplateService.deleteUserTemplateByDashboardId(dashboardIds);
				userTemplateService.removeMarkDeletedUserTemplates(userList);
				taskService.deleteUserTaskByDashbordId(dashboardIds);

				if (deletedUserList != null && !deletedUserList.isEmpty()) {
					storageDetailsService.deleteUserStorageByDashbordId(dashboardIds,true);
					userAdminDao.deleteUsers(deletedUserList);
				}else {
					storageDetailsService.deleteUserStorageByDashbordId(dashboardIds,false);
				}

			} catch (Exception e) {
				logger.error("User deletion schedular failed", e);
			}
		}
	}

	@Override
	public void markUsersAsModified(List<UserDetails> userdetails) {
		List<UserDetails> modifiedUserList = new ArrayList<>();

		userdetails.forEach(user -> {
			user.setModified(true);
			modifiedUserList.add(user);
		});
		userAdminDao.markUsersAsModified(modifiedUserList);
	}

}
