package com.logicq.ngr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.helper.ReportDownloadHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.service.report.ReportDownloadService;
import com.logicq.ngr.vo.ExportReportVO;
import com.logicq.ngr.vo.UserTemplateVO;

/**
 * This class downloads reports in required format.
 * 
 * @author 611022163
 *
 */

@RequestMapping(value = "/api/exportreport")
@RestController
public class ExportReportController {



	@Autowired
	ReportDownloadHelper reportDownloadHelper;

	@Autowired
	ReportDownloadService reportDownloadService;
	
	@Autowired
	SecurityHelper securityHelper;	

	public static ObjectMapper mapper = new ObjectMapper();

	
	
	@RequestMapping(value = "/downloadreport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExportReportVO>  downloadReport(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		ExportReportVO exportReportVO=new ExportReportVO();
		securityHelper.setUserNameFromSecurityContext(userTemplateVO);
		exportReportVO.setDownloadURL(reportDownloadService.exportReport(userTemplateVO)); 
		return new ResponseEntity<ExportReportVO> (exportReportVO,HttpStatus.OK);
	};
	
}
