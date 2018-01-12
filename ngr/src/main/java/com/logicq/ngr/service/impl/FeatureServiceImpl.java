package com.logicq.ngr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.reportpack.ReportpackDetails;
import com.logicq.ngr.service.FeatureService;
import com.logicq.ngr.service.ProfileService;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.util.UserFactory;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.FeaturePropertyVO;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.UserVO;

@Service
public class FeatureServiceImpl implements FeatureService {

	
	
	@Autowired
    ProfileService profileService;
	
	@Autowired
	ReportPackService reportPackService;
	
	ObjectMapper mapper = ObjectFactory.getObjectMapper();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public FeatureDetailsVO getFeatureConfig(UserDetails userDetails) throws Exception {
		FeatureDetailsVO featureDetails = null;
		Profile profile = userDetails.getProfile();
		if (null != profile) {
			featureDetails = mapper.readValue(new String(profile.getFeatures()), new TypeReference<FeatureDetailsVO>() {
			});
		} else {
			throw new BusinessException(1017);
		}
		return featureDetails;
	}


	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ProfileVO getFeaturesForProfile(ProfileVO profileVO) throws Exception {
		List<FeaturePropertyVO> listOfFeatureProperty=new ArrayList<>();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		Profile profile = profileService.getProfile(profileVO.getId());
		FeatureDetailsVO featureDetails = mapper.readValue(new String(profile.getFeatures()),
				new TypeReference<FeatureDetailsVO>() {
				});
		//List<ReportpackDetails> reportpackDetailsList=reportPackService.getReportPack(profileVO.getId());
		Set<ReportpackDetails> reportpackDetailsList=profile.getReportpacks();
		/*We are iterating the List<FeaturePropertyVO> so that we can take the reportpack out of 
		this "reportFeatureList" from the Response. We done this because UI want the "reportFeatureList" and
		"reportPackListResponse" separately. So we are maintaining all into "reportFeatureList" (w.r.t.db) but when returning 
		to UI as response, we keep both separate in different List*/
		
		reportpackDetailsList.forEach(reportpackDetails->{
			Attribute attribute=new Attribute();
			attribute.setId(reportpackDetails.getReportpackId());
			attribute.setDisplayName(reportpackDetails.getName());
			attribute.setName(reportpackDetails.getName());
			attributeList.add(attribute);
		});
		
		profileVO.setFeatures(featureDetails);
		profileVO.setId(profile.getProfileId());
		profileVO.setName(profile.getName());
		profileVO.setReportPackListResponse(attributeList); // This we have added in ProfileVO
		if (!StringUtils.isEmpty(profile)) {
			List<UserVO> userVoList = new ArrayList<>();
			profile.getUsers().forEach((user) -> {
				UserVO userVO = UserFactory.create(user);
				userVoList.add(userVO);
			});
			profileVO.setUsersList(userVoList);
		}
		if (null != profile.getRoles() && !profile.getRoles().isEmpty()) {
			profileVO.setRole(profile.getRoles().get(0).getName());
		}
		return profileVO;
	}

	}