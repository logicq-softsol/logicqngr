package com.logicq.ngr.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.MessageHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.service.FeatureService;
import com.logicq.ngr.service.ProfileService;
import com.logicq.ngr.service.RoleService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.util.UserFactory;
import com.logicq.ngr.vo.MessageVO;
import com.logicq.ngr.vo.ProfileUserVO;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.RoleUserVo;
import com.logicq.ngr.vo.RoleVO;
import com.logicq.ngr.vo.UserDetailVO;
import com.logicq.ngr.vo.UserVO;

/**
 * 
 * @author 611164825
 *
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping("/api/admin")
@RestController
public class AdminController {

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	RoleService roleService;

	@Autowired
	ProfileService profileService;

	@Autowired
	FeatureService featureService;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	MessageHelper messageHelper;

	/**
	 * 
	 * resource method to add user into database
	 * 
	 * @param user
	 * @return UserDetails
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> addUser(@RequestBody UserDetails user) throws Exception {
		UserDetails userdetail = null;
		if (null != LoadApplicationData.userMap.get(user.getUserName())) {
			throw new BusinessException(1001);
		}

		if (null != user) {
			userdetail = userAdminService.addUser(user);
			UserVO uservo = UserFactory.create(user);
			LoadApplicationData.userMap.put(user.getUserName(), uservo);
			return new ResponseEntity<UserDetails>(userdetail, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserDetails>(userdetail, HttpStatus.OK);
	}

	// Need to check why dashboard come in admin control

	/**
	 * resource method to update user in database
	 * 
	 * @param user
	 * @return UserDetails
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailVO> updateUser(@RequestBody UserDetailVO user) throws Exception {
		UserDetailVO userDetailresult = null;

		if (null != user) {
			userDetailresult = userAdminService.updateUser(user);
			return new ResponseEntity<UserDetailVO>(userDetailresult, HttpStatus.OK);
		}
		return new ResponseEntity<UserDetailVO>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * resource method to delete user based on userid
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> deleteUser(@RequestBody UserDetails userDetails) throws Exception {
		MessageVO messageVO = new MessageVO();
		String userName = userDetails.getUserName();
		if (!StringUtils.isEmpty(userName)) {
			try {
				userAdminService.markAsUserDelete(userName);
				LoadApplicationData.userMap.remove(userName);
				messageVO = messageHelper.handleBusinessMessage(1003);
				
				return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
			} catch (Exception ex) {
				throw new BusinessException(1004);
			}
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * resource method to fetch all user from database
	 * 
	 * @return List<UserDetails>
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getAllUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDetails>> getAllUser() throws Exception {
		// String userName = securityHelper.getUserNameFromSecurityContext();
		UserDetails userdetail = userAdminService.getUser(securityHelper.getUserNameFromSecurityContext());
		String roleName = userdetail.getRole().getName();
		if (StringUtils.isEmpty(roleName)) {
			roleName = "User";
		}
		List<UserDetails> userdetails = userAdminService.getAllUser(userdetail.getRole().getAllowedRoles());
		return new ResponseEntity<List<UserDetails>>(userdetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/getuserroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Role>> getUserRoles() throws Exception {
		List<Role> roles = roleService.getAllRoles();
		return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
	}

	@RequestMapping(value = "/getuserprofiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProfileVO>> getUserProfiles() throws Exception {
		List<ProfileVO> profileVOList = profileService.getUserProfiles();
		return new ResponseEntity<List<ProfileVO>>(profileVOList, HttpStatus.OK);
	}

//	@RequestMapping(value = "/getfeatures", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<FeatureVO>> getFeatures(@RequestBody FeatureVO featureVO) throws Exception {
//		List<FeatureVO> features = featureService.getFeatures(featureVO);
//		return new ResponseEntity<List<FeatureVO>>(features, HttpStatus.OK);
//	}

	@RequestMapping(value = "/getfeaturesforprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileVO> getFeaturesForProfile(@RequestBody ProfileVO profileVO) throws Exception {
		ProfileVO profilevo = featureService.getFeaturesForProfile(profileVO);
		return new ResponseEntity<ProfileVO>(profilevo, HttpStatus.OK);
	}

	@RequestMapping(value = "/editprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProfileVO>> editProfile(@RequestBody ProfileVO profileVO) throws Exception {
		Profile oldProfile = profileService.getProfile(profileVO.getId());
		List<ProfileVO> profiles = profileService.editProfile(profileVO);
		profileService.onProfileChange(oldProfile);
		return new ResponseEntity<List<ProfileVO>>(profiles, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProfileVO>> deleteProfile(@RequestBody ProfileVO profileVO) throws Exception {
		List<ProfileVO> profiles = profileService.deleteProfile(profileVO);
		return new ResponseEntity<List<ProfileVO>>(profiles, HttpStatus.OK);
	}

	@RequestMapping(value = "/createprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProfileVO>> createProfile(@RequestBody ProfileVO profileVO) throws Exception {
		List<ProfileVO> profileVOList = profileService.createProfile(profileVO);
		return new ResponseEntity<List<ProfileVO>>(profileVOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getprofilebyrolename", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getProfileByRoleName(@RequestBody RoleVO role) throws Exception {
		List<Profile> listProfile = profileService.getProfileByRoleName(role);

		return new ResponseEntity<List<Profile>>(listProfile, HttpStatus.OK);
	}

	/**
	 * // Get UserName from Security Context // Load user For that user Name //
	 * you will get Role For that User and check allowed role for that user in
	 * pojo //fire Query in User details where role in( list of allowed role)
	 * with active flag true //Return result
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getactiveusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserVO>> getActiveUsers() throws Exception {
		List<UserVO> userVoList = null;
		String userName = securityHelper.getUserNameFromSecurityContext();
		UserDetails userdetail = userAdminService.getUser(userName);
		String allowedroles = userdetail.getRole().getAllowedRoles();
		if (StringUtils.isEmpty(allowedroles)) {
			allowedroles = "User";
		}
		userVoList = userAdminService.getActiveUsers(allowedroles);
		return new ResponseEntity<List<UserVO>>(userVoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getusersbyprofile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProfileUserVO>> getUsersByProfile() throws Exception {
		List<ProfileUserVO> profileuserlist = null;
		UserDetails userdetail = userAdminService.getUser(securityHelper.getUserNameFromSecurityContext());
		if (null != userdetail.getRole()) {
			profileuserlist = userAdminService.getProfilesAccordingToRole(userdetail.getRole());
		}

		return new ResponseEntity<List<ProfileUserVO>>(profileuserlist, HttpStatus.OK);
	}

	@RequestMapping(value = "/getusersbyallowedrole", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoleUserVo>> getUsersByAllowedRole() throws Exception {
		List<RoleUserVo> roleList = null;
		UserDetails userdetail = userAdminService.getUser(securityHelper.getUserNameFromSecurityContext());
		roleList = userAdminService.getUsersAccordingToAllowedRole(userdetail.getRole().getAllowedRoles());

		return new ResponseEntity<List<RoleUserVo>>(roleList, HttpStatus.OK);
	}

	@RequestMapping(value = "/enabledisableuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> enableDisableUser(@RequestBody UserDetails userVo) throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			userAdminService.enableDisableUser(userVo);
			messageVO.setMessage("success");
			messageVO.setMessagecode(200);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/getuserinfo", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> getUserInformation() throws Exception {
		UserVO details = null;
		try {
			details = userAdminService.getUserInformation(securityHelper.getUserNameFromSecurityContext());
		} catch (Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<UserVO>(details, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserVO>(details, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateuserprofile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> updateUser(@RequestBody UserVO userVO) {
		UserVO userRes = null;
		try {
			userRes = userAdminService.updateUser(userVO, securityHelper.getUserNameFromSecurityContext());
		} catch (Exception e) {
			return new ResponseEntity<UserVO>(userRes, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserVO>(userRes, HttpStatus.OK);
	}
}
