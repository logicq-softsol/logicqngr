package com.logicq.ngr.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.model.report.Template;
import com.logicq.ngr.service.report.TemplateService;

/**
 * 
 * @author 611164825
 *
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping("/api/template")
@RestController
public class TemplateController {
	private static final Logger logger = Logger.getLogger(TemplateController.class);
	
	@Autowired
	TemplateService templateService;
	
	/**
	 * resource method to add user into database
	 * 
	 * @param user
	 * @return UserDetails
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllTemplate", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Template>> getAllTemplate() {
		logger.info("getAllTemplate Controller");
		List<Template> allTemplate = templateService.getAllTemplate();
		return new ResponseEntity<List<Template>>(allTemplate, HttpStatus.OK);
	}

}
