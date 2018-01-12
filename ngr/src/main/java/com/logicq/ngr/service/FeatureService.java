package com.logicq.ngr.service;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.ProfileVO;

public interface FeatureService {

	FeatureDetailsVO getFeatureConfig(UserDetails userDetails) throws Exception;
	
	ProfileVO getFeaturesForProfile(ProfileVO prfoileVO) throws Exception;
	
}