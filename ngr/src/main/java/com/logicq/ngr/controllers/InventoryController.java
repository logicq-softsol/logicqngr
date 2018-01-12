package com.logicq.ngr.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.inventory.PropertyCacheService;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.ReportPropertyVO;;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/inventory")
@Controller
public class InventoryController {

	
	@Autowired
	PropertyCacheService propertyCacheService;
	
	@Autowired
	UserAdminService userAdminService;
	
	@Autowired
	SecurityHelper securityHelper;
		
	@RequestMapping(value = "/loadpropertiesforentitytype", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReportPropertyVO> loadPropertiesForEntityType(
			@RequestParam(value = "entityType", required = false) String entityType) throws Exception {
		ReportPropertyVO results = null;
		String userName = securityHelper.getUserNameFromSecurityContext();
		if (!ReportHelper.isEmpty(userName)) {
			UserDetails userDetails = userAdminService.getUser(userName);
			results = propertyCacheService.loadDefaultPropertiesAccordingToUser(userDetails, entityType);
			propertyCacheService.getAllFeatureProperty();

		}
		return new ResponseEntity<ReportPropertyVO>(results, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/loadAllFeatureProperty", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FeatureDetailsVO> allFeatureProperty() throws Exception {
		FeatureDetailsVO results = null;
		String userName = securityHelper.getUserNameFromSecurityContext();
		if (!ReportHelper.isEmpty(userName)) {
			results=propertyCacheService.getAllFeatureProperty();
		}
		return new ResponseEntity<FeatureDetailsVO>(results, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getdashboardsetup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Attribute>> getDashboardDataSetup() throws Exception {
		Map<String,Attribute>results = null;
		String userName = securityHelper.getUserNameFromSecurityContext();
		if (!ReportHelper.isEmpty(userName)) {
			results=propertyCacheService.getFeatureSetup();
		}
		return new ResponseEntity<Map<String,Attribute>>(results, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/loadspecificproperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Attribute>> loadSpecificProperties(
			@RequestParam(value = "entityid", required = true) String entityid,
			@RequestParam(value = "entityname", required = true) String entityname,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "displayName", required = false) String displayName,
			@RequestParam(value = "fillterPattern", required = false) String fillterPattern) throws Exception {
		Attribute queryAttribute = new Attribute();
		queryAttribute.setId(id);
		queryAttribute.setName(name);
		queryAttribute.setDisplayName(displayName);
		Attribute queryEntity = new Attribute();
		queryEntity.setId(entityid);
		queryEntity.setName(entityname);
		List<Attribute> attributes=propertyCacheService.loadSpecificProperties(queryAttribute,queryEntity,fillterPattern);
		return new ResponseEntity<List<Attribute>>(attributes, HttpStatus.OK);
	}
	
	
}
