package com.logicq.ngr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import com.logicq.ngr.model.KeyIdentifier;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.common.SiteOption;
import com.logicq.ngr.model.inventory.EntityType;
import com.logicq.ngr.service.KeyIdentifierService;
import com.logicq.ngr.service.SiteOptionService;
import com.logicq.ngr.service.inventory.PropertyCacheService;
import com.logicq.ngr.service.security.LoginService;
import com.logicq.ngr.util.UserFactory;
import com.logicq.ngr.vo.ReportPropertyVO;
import com.logicq.ngr.vo.UserVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class LoadApplicationData {

	SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	public static HashMap<String, UserVO> userMap = new HashMap<String, UserVO>();
	public static HashMap<String, String> siteOptionMap = new HashMap<String, String>();
	public static Map<String, String> attributesMap = new HashMap<String, String>();
	public static Map<String, KeyIdentifier> operatorsMap = new HashMap<String, KeyIdentifier>();
	public static Map<String, ReportPropertyVO> reportProperty = new HashMap<String, ReportPropertyVO>();

	@Autowired
	LoginService loginService;

	@Autowired
	SiteOptionService siteOptionService;

	@Autowired
	KeyIdentifierService keyIdentifierService;

	@Autowired
	PropertyCacheService propertyCacheService;

	@PostConstruct
	public void loadUsers() throws Exception {
		// final LoadDataAsync loadDataAsync = new LoadDataAsync(siteOptionMap,
		// attributesMap, operatorsMap,
		// reportProperty);
		// loadDataAsync.setLoginService(loginService);
		// loadDataAsync.setKeyIdentifierService(keyIdentifierService);
		// loadDataAsync.setPropertyCacheService(propertyCacheService);
		// loadDataAsync.setSiteOptionService(siteOptionService);
		// if(userMap.isEmpty()){
		List<UserDetails> userDetails = loginService.loadUsers();
		userDetails.forEach((usr) -> {
			UserVO uservo = UserFactory.create(usr);
			userMap.put(uservo.getUsername(), uservo);
		});

		// asyncTaskExecutor.execute(loadDataAsync);

		if (siteOptionMap.isEmpty()) {
			List<SiteOption> siteOptionData = siteOptionService.loadSiteOption();
			siteOptionData.forEach((siteData) -> {
				// SiteOptionVo siteOptionVo =
				// SiteOptionFactory.create(siteData);
				siteOptionMap.put(siteData.getDimension(), siteData.getValue());
			});
		}
		if (operatorsMap.isEmpty()) {
			List<KeyIdentifier> KeyIdentifiers = keyIdentifierService.getKeyIdentifiers();
			KeyIdentifiers.forEach((keyidentifer) -> {
				operatorsMap.put(keyidentifer.getType(), keyidentifer);
			});
		}
		if (attributesMap.isEmpty()) {
			Map<String, String> entityAttributes = propertyCacheService.loadAttributesFromProperty();
			if (!entityAttributes.isEmpty()) {
				attributesMap.putAll(entityAttributes);
			}
		}

		if (reportProperty.isEmpty()) {
			List<EntityType> entityList = propertyCacheService.getAllEntityTypeList();
			if (null != entityList && !entityList.isEmpty()) {
				entityList.forEach((entity) -> {
					try {
						ReportPropertyVO entityProperty = propertyCacheService
								.loadDefaultPropertiesAccordingToUser(null, entity.getName());
						reportProperty.put(entity.getName(), entityProperty);
					} catch (Exception ex) {

					}
				});
			}

		}
	}

}
