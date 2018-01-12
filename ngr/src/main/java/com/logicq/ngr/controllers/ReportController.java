package com.logicq.ngr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.service.report.ReportService;
import com.logicq.ngr.vo.UserTemplateVO;;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/reports")
@RestController
public class ReportController {


	@Autowired
	ReportService reportService;

	@Autowired
	SecurityHelper securityHelper;
	
	/**
	 * 
	 * @param userTemplateVO
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/addUserReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> addUserReport(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = null;
		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		userReport = reportService.addUserReport(userTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	@RequestMapping(value = "/edituserreport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> editUserReport(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = null;
		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		userReport = reportService.editUserReport(userTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	/**
	 * 
	 * @param userTemplateVO
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/getuserreports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> getUserReports(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		UserReport userReport = reportService.getUserReport(userTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	/**
	 * 
	 * @param dashboardid
	 * @param templateid
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/getspecificuserreport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> getUserReportsSpecificToTemplate(@RequestBody UserTemplateVO userTemplateVO)
			throws Exception {

		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		UserReport userReport = reportService.getSpecificUserReports(userTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	/**
	 * 
	 * @param templatevo
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/getreportaccordingtofilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> getReportAccordingToFilter(@RequestBody UserTemplateVO templatevo)
			throws Throwable {
		UserReport userReport = null;

		securityHelper.setUserNameFromSecurityContext(templatevo);
		userReport = reportService.getReportAccordingToFilter(templatevo);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	/**
	 * 
	 * @param usertemplate
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/deletetemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTemplateVO> deleteTemplate(@RequestBody UserTemplateVO userTemplateVO) throws Throwable {
		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		reportService.deleteUserTemplate(userTemplateVO);
		return new ResponseEntity<UserTemplateVO>(userTemplateVO, HttpStatus.OK);
	}

}