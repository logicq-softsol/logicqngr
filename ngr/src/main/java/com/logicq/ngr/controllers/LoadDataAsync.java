package com.logicq.ngr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.KeyIdentifier;
import com.logicq.ngr.model.common.SiteOption;
import com.logicq.ngr.model.inventory.EntityType;
import com.logicq.ngr.service.KeyIdentifierService;
import com.logicq.ngr.service.SiteOptionService;
import com.logicq.ngr.service.inventory.PropertyCacheService;
import com.logicq.ngr.service.security.LoginService;
import com.logicq.ngr.vo.ReportPropertyVO;


public class LoadDataAsync implements Runnable{
	
	
	private  HashMap<String, String> siteOptionMap;
	private  Map<String, String> attributesMap;
	private  Map<String, KeyIdentifier> operatorsMap;
	private  Map<String, ReportPropertyVO> reportProperty;
	private LoginService loginService;
	private SiteOptionService siteOptionService;
	private KeyIdentifierService keyIdentifierService;
	private PropertyCacheService propertyCacheService;
	
	
	
	public LoadDataAsync(HashMap<String, String> siteOptionMap,Map<String, String> attributesMap,Map<String, KeyIdentifier> operatorsMap,Map<String, ReportPropertyVO> reportProperty){
		this.siteOptionMap=siteOptionMap;
		this.attributesMap=attributesMap;
		this.operatorsMap=operatorsMap;
		this.reportProperty=reportProperty;
	}



	@Override
	public void run() {
		if (this.siteOptionMap.isEmpty()) {
			List<SiteOption> siteOptionData = this.siteOptionService.loadSiteOption();
			siteOptionData.forEach((siteData) -> {
				// SiteOptionVo siteOptionVo =
				// SiteOptionFactory.create(siteData);
				this.siteOptionMap.put(siteData.getDimension(), siteData.getValue());
			});
		}
		if (this.operatorsMap.isEmpty()) {
			List<KeyIdentifier> KeyIdentifiers = this.keyIdentifierService.getKeyIdentifiers();
			KeyIdentifiers.forEach((keyidentifer) -> {
				this.operatorsMap.put(keyidentifer.getType(), keyidentifer);
			});
		}
		if (this.attributesMap.isEmpty()) {
			Map<String, String> entityAttributes = this.propertyCacheService.loadAttributesFromProperty();
			if (!entityAttributes.isEmpty()) {
				this.attributesMap.putAll(entityAttributes);
			}
		}

		if (this.reportProperty.isEmpty()) {
			List<EntityType> entityList = this.propertyCacheService.getAllEntityTypeList();
			if (null != entityList && !entityList.isEmpty()) {
				entityList.forEach((entity) -> {
					try {
						ReportPropertyVO entityProperty = this.propertyCacheService
								.loadDefaultPropertiesAccordingToUser(null, entity.getName());
						this.reportProperty.put(entity.getName(), entityProperty);
					} catch (Exception ex) {

					}
				});
			}

		}
	}



	public LoginService getLoginService() {
		return loginService;
	}



	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}



	public SiteOptionService getSiteOptionService() {
		return siteOptionService;
	}



	public void setSiteOptionService(SiteOptionService siteOptionService) {
		this.siteOptionService = siteOptionService;
	}



	public KeyIdentifierService getKeyIdentifierService() {
		return keyIdentifierService;
	}



	public void setKeyIdentifierService(KeyIdentifierService keyIdentifierService) {
		this.keyIdentifierService = keyIdentifierService;
	}



	public PropertyCacheService getPropertyCacheService() {
		return propertyCacheService;
	}



	public void setPropertyCacheService(PropertyCacheService propertyCacheService) {
		this.propertyCacheService = propertyCacheService;
	}



	public HashMap<String, String> getSiteOptionMap() {
		return siteOptionMap;
	}



	public Map<String, String> getAttributesMap() {
		return attributesMap;
	}



	public Map<String, KeyIdentifier> getOperatorsMap() {
		return operatorsMap;
	}



	public Map<String, ReportPropertyVO> getReportProperty() {
		return reportProperty;
	}


	
	
}
