package com.logicq.ngr.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.service.reportpack.ReportPackTemplateService;
import com.logicq.ngr.vo.ReportpackTemplateVO;
import com.logicq.ngr.vo.UserTemplateVO;

@EnableAspectJAutoProxy(proxyTargetClass=true)
@RequestMapping(value = "/api/admin")
@RestController
public class ReportUserTemplateController {
	
	@Autowired
	UserTemplateService userTemplateService;
	
	@Autowired
    ReportPackTemplateService reportPackTemplateService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/fetchUserTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserTemplateVO>> fetchUserTemplate(@RequestBody UserTemplateVO userTemplateVO)
			throws Exception {
		List<UserTemplateVO> userTemplateList = null;

		if (null != userTemplateVO) {
			userTemplateList = userTemplateService.fetchUserTemplate(userTemplateVO);
			if (!userTemplateList.isEmpty()) {
				return new ResponseEntity<List<UserTemplateVO>>(userTemplateList, HttpStatus.OK);
			}
		}

		if (!ReportHelper.isEmpty(userTemplateVO.getType())
				&& userTemplateVO.getType().equalsIgnoreCase("reportpack")) {
			ReportpackTemplateVO reportpackTemplateVO = new ReportpackTemplateVO();
			reportpackTemplateVO.setReportpackId(userTemplateVO.getDashboardId());
			reportpackTemplateVO.setReportpackTemplateId(userTemplateVO.getUserTemplateId());
			ReportpackTemplate reportpackTemplate = reportPackTemplateService
					.getReportPackTemplate(reportpackTemplateVO);
			if (null != reportpackTemplate) {
				userTemplateVO.setJsonString(
						mapper.readValue(reportpackTemplate.getJsonString(), new TypeReference<Map<String, Object>>() {
						}));
			}
			userTemplateList = new ArrayList<>();
			userTemplateList.add(userTemplateVO);
			if (!userTemplateList.isEmpty()) {
				return new ResponseEntity<List<UserTemplateVO>>(userTemplateList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<List<UserTemplateVO>>(userTemplateList, HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value="/saveUserTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTemplateVO> saveUserTemplate(@RequestBody UserTemplateVO usertTemplateVO) throws Exception {
				
		if(null!=usertTemplateVO)
		{
			userTemplateService.saveUserTemplate(usertTemplateVO);
			return new ResponseEntity<UserTemplateVO>(usertTemplateVO, HttpStatus.OK);
		}
			return new ResponseEntity<UserTemplateVO>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value="/editUserTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTemplateVO> editUserTemplate(@RequestBody UserTemplateVO usertTemplateVO) throws Exception {
		if(null!=usertTemplateVO)
		{
			userTemplateService.editUserTemplate(usertTemplateVO);
			return new ResponseEntity<UserTemplateVO>(usertTemplateVO, HttpStatus.OK);
		}
			return new ResponseEntity<UserTemplateVO>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/deleteUserTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTemplate> deleteUserTemplate(@RequestBody UserTemplate userTemplate) throws Exception {
		if(null!=userTemplate)
		{
		userTemplateService.deleteUserTemplate(userTemplate);
		return new ResponseEntity<UserTemplate>(userTemplate, HttpStatus.OK);
		}
		return new ResponseEntity<UserTemplate>(HttpStatus.BAD_REQUEST);
	}

}
