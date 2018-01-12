package com.logicq.ngr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.constant.ReportpackEnum;
import com.logicq.ngr.dao.ProfileDao;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.service.ProfileService;
import com.logicq.ngr.service.RoleService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.DashboardService;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.FeaturePropertyVO;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.RoleVO;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileDao profileDao;

	@Autowired
	RoleService roleService;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	ReportPackService reportPackService;

	@Autowired
	DashboardService dashboardService;

	ObjectMapper mapper = ObjectFactory.getObjectMapper();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProfileVO> getUserProfiles() throws Exception {
		List<ProfileVO> profileVOList = new ArrayList<ProfileVO>();
		UserDetails userdetail = userAdminService.getUser(securityHelper.getUserNameFromSecurityContext());
		List<Role> roles = getProfileAccordingToAllowedRole(userdetail.getRole().getAllowedRoles());
		roles.forEach(role -> {
			List<Profile> profiles = role.getProfiles();
			profiles.forEach((pro) -> {
				ProfileVO newprofileVO = new ProfileVO();
				newprofileVO.setId(pro.getProfileId());
				newprofileVO.setName(pro.getName());
				if (pro.getRoles().size() > 0) {
					newprofileVO.setRole(pro.getRoles().get(0).getName());
				}
				profileVOList.add(newprofileVO);
			});
		});
		return profileVOList;
	}

	@Override
	public List<ProfileVO> createProfile(ProfileVO profileVO) throws Exception {
		Profile profile = new Profile();
		Role role = null;
		if (!StringUtils.isEmpty(profileVO.getRole())) {
			role = roleService.getRole(profileVO.getRole());
		} else {
			role = roleService.getRole("User");
		}
		// This code is added when profile is assigned with some Reportpack
		// Dashboard then we need to add ReportpackJson to ReportFeatureList
		// of profile feature.(UI used this for menu purpose)
		if (!profileVO.getReportPackList().isEmpty()) {
			populateReportpackDetail(profileVO);
		}

		profile.setFeatures(mapper.writeValueAsBytes(profileVO.getFeatures()));
		profile.setName(profileVO.getName());
		profile.setRoles(new ArrayList<Role>());
		profile.getRoles().add(role);
		// assign report pack to that profile
		reportPackService.assignReportpack(profileVO.getReportPackList(), profile);
		profile = profileDao.createProfile(profile);

		List<ProfileVO> profileVOList = getUserProfiles();
		ProfileVO newprofileVO = new ProfileVO();
		newprofileVO.setId(profile.getProfileId());
		newprofileVO.setName(profile.getName());
		if (profile.getRoles().size() > 0) {
			newprofileVO.setRole(profile.getRoles().get(0).getName());
		}
		profileVOList.add(newprofileVO);
		return profileVOList;
	}

	private void populateReportpackDetail(ProfileVO profileVO) {
		List<FeaturePropertyVO> featurePropertyVOList = new ArrayList<>();
		FeaturePropertyVO featurePropertyVO = new FeaturePropertyVO();
		featurePropertyVO.setName(ReportpackEnum.Type.getValue());
		featurePropertyVO.setDisplayName(ReportpackEnum.Type.getValue().toUpperCase());
		featurePropertyVOList.add(featurePropertyVO);
		profileVO.getFeatures().getReportFeatureList().addAll(featurePropertyVOList);
	}
	
	private void removeReportpackDetails(Profile fetchedProfile)
			throws IOException, JsonParseException, JsonMappingException {
		FeatureDetailsVO dbFeatureDetails = mapper.readValue(fetchedProfile.getFeatures(),
				new TypeReference<FeatureDetailsVO>() {
				});
		List<FeaturePropertyVO> featurePropertyList = dbFeatureDetails.getReportFeatureList();
		featurePropertyList.stream()
				.filter(featureProperty -> featureProperty.getName()
						.equalsIgnoreCase(ReportpackEnum.Type.getValue()))
				.findFirst().ifPresent(featureProperty -> {
					featurePropertyList.remove(featureProperty);
				});
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Profile getProfile(Long profileId) {
		Profile profile = new Profile();
		profile.setProfileId(profileId);
		profile = profileDao.getProfile(profile);
		return profile;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Profile> getProfileByRoleName(RoleVO roleVO) throws Exception {
		Role role = roleService.getRoleByName(roleVO);
		return role.getProfiles();
	}

	@Override
	public List<ProfileVO> editProfile(ProfileVO profileVO) throws Exception {
		List<ProfileVO> profileVOList = null;
		Profile fetchedProfile = profileDao.getProfile(profileVO.getId());
		if (null != profileVO) {

			// This code is added when profile is assigned with some Reportpack
			// Dashboard then we need to add ReportpackJson to ReportFeatureList
			// of profile feature.(UI used this for menu purpose)
			if (!ReportHelper.IsNullOrEmptyForCollection(profileVO.getReportPackList())) {
				populateReportpackDetail(profileVO);
			} else {
				// If No Reportpack assign to Profile then need to delete
				// Reportpack detail from Feature json stored in DB
				removeReportpackDetails(fetchedProfile);
			}

			if (!StringUtils.isEmpty(profileVO.getName())) {
				fetchedProfile.setName(profileVO.getName());
			}
			if (!StringUtils.isEmpty(profileVO.getRole())) {
				// need to remove after Security implemnation
				Role fetchedRole = roleService.getRole(profileVO.getRole());
				fetchedProfile.setRoles(new ArrayList<Role>());
				fetchedProfile.getRoles().add(fetchedRole);
			}
			if (null != profileVO.getFeatures()) {
				byte[] features = mapper.writeValueAsBytes(profileVO.getFeatures());
				fetchedProfile.setFeatures(features);
			}

			if (!ReportHelper.IsNullOrEmptyForCollection(profileVO.getReportPackList())) {
				// assign report pack to that profile
				fetchedProfile.getReportpacks().clear();
				reportPackService.assignReportpack(profileVO.getReportPackList(), fetchedProfile);
			}
			profileDao.updateProfile(fetchedProfile);
			profileVOList = getUserProfiles();
		}
		return profileVOList;
	}

	@Override
	public List<ProfileVO> deleteProfile(ProfileVO profileVO) throws Exception {
		List<ProfileVO> profileVOList;
		Profile profileEntity = new Profile();
		profileEntity.setProfileId(profileVO.getId());
		List<UserDetails> users = userAdminService.getUsers(profileEntity.getProfileId());
		// If profileId is not assigned to any user, it will be deleted,
		// otherwise exception will be thrown
		if (users.isEmpty()) {
			profileDao.deleteProfile(profileEntity);
			profileVOList = getUserProfiles();
		} else {
			throw new BusinessException(1001);
		}
		return profileVOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Role> getProfileAccordingToAllowedRole(String allowedRole) {
		return roleService.getRoles(allowedRole);
	}

	@Override
	public List<Profile> getModifiedUsers() {
		return profileDao.getModifiedProfiles();
	}

	protected boolean isDymanic(FeaturePropertyVO featurePropertyVO) {
		Predicate<Attribute> p = attr -> attr.getName().equalsIgnoreCase(ReportType.DYNAMIC_REPORT.getValue());
		return featurePropertyVO.getReport().stream().anyMatch(p);
	}

	protected Set<String> prepareDashboardTypeSet(List<FeaturePropertyVO> reportFeatures,
			Set<String> dynamicDashboards) {
		Set<String> dashboardTypes = new HashSet<>();
		if (null != reportFeatures && !reportFeatures.isEmpty()) {
			reportFeatures.forEach(featurePropertyVo -> {
				dashboardTypes.add(featurePropertyVo.getName());
				if (isDymanic(featurePropertyVo)) {
					dynamicDashboards.add(featurePropertyVo.getName());
				}
			});
		}
		return dashboardTypes;
	}

	/**
	 * 
	 */
	@Override
	public void onProfileChange(Profile oldProfile) throws Exception {
		List<UserDetails> users = userAdminService.getUsers(oldProfile.getProfileId());
		/**
		 * Do the operation only when profile is assigned to any user
		 */
		if (null != users && !users.isEmpty()) {
			Set<String> dynamicDashboards = new HashSet<>();
			FeatureDetailsVO featureDetailsVoOP = mapper.readValue(oldProfile.getFeatures(), FeatureDetailsVO.class);
			List<FeaturePropertyVO> reportFeaturesOP = featureDetailsVoOP.getReportFeatureList();
			Set<String> dashboardsOP = prepareDashboardTypeSet(reportFeaturesOP, dynamicDashboards);
			Profile newProfile = getProfile(oldProfile.getProfileId());
			FeatureDetailsVO featureDetailsVoNP = mapper.readValue(newProfile.getFeatures(), FeatureDetailsVO.class);
			List<FeaturePropertyVO> reportFeaturesNP = featureDetailsVoNP.getReportFeatureList();
			Set<String> dashboardsNP = prepareDashboardTypeSet(reportFeaturesNP, dynamicDashboards);
			Set<String> addSet = dashboardsNP.stream()
					.filter(elem -> (!dashboardsOP.contains(elem) && dynamicDashboards.contains(elem)))
					.collect(Collectors.toSet());
			Set<String> removeSet = dashboardsOP.stream()
					.filter(elem -> (!dashboardsNP.contains(elem) && dynamicDashboards.contains(elem)))
					.collect(Collectors.toSet());

			if (null != removeSet && !removeSet.isEmpty()) {
				dashboardService.markDashboardsAndReportsAsDeleted(new HashSet<UserDetails>(users), removeSet);
				// Set<Long> userIds =
				// users.stream().map(UserDetails::getId).collect(Collectors.toSet());
				userAdminService.markUsersAsModified(users);
			}
			if (null != addSet && !addSet.isEmpty()) {
				dashboardService.createDashboardsFromProfile(new HashSet<UserDetails>(users), addSet);
			}
		}
	}

	@Override
	public void assignProfile(UserDetails user, Profile newProfile) throws Exception {
		Set<String> dynamicDashboards = new HashSet<>();
		FeatureDetailsVO featureDetailsVoOP = mapper.readValue(user.getProfile().getFeatures(), FeatureDetailsVO.class);
		List<FeaturePropertyVO> reportFeaturesOP = featureDetailsVoOP.getReportFeatureList();
		Set<String> dashboardsOP = prepareDashboardTypeSet(reportFeaturesOP, dynamicDashboards);
		FeatureDetailsVO featureDetailsVoNP = mapper.readValue(newProfile.getFeatures(), FeatureDetailsVO.class);
		List<FeaturePropertyVO> reportFeaturesNP = featureDetailsVoNP.getReportFeatureList();
		Set<String> dashboardsNP = prepareDashboardTypeSet(reportFeaturesNP, dynamicDashboards);

		Set<String> addSet = dashboardsNP.stream()
				.filter(elem -> (!dashboardsOP.contains(elem) && dynamicDashboards.contains(elem)))
				.collect(Collectors.toSet());
		Set<String> removeSet = dashboardsOP.stream()
				.filter(elem -> (!dashboardsNP.contains(elem) && dynamicDashboards.contains(elem)))
				.collect(Collectors.toSet());

		if (null != removeSet && !removeSet.isEmpty()) {
			dashboardService.markDashboardsAndReportsAsDeleted(new HashSet<UserDetails>(Arrays.asList(user)),
					removeSet);
			List<UserDetails> userList = new ArrayList<UserDetails>();
			userList.add(user);
			userAdminService.markUsersAsModified(userList);
		}

		if (null != addSet && !addSet.isEmpty()) {
			dashboardService.createDashboardsFromProfile(new HashSet<UserDetails>(Arrays.asList(user)), addSet);
		}
	}
}
