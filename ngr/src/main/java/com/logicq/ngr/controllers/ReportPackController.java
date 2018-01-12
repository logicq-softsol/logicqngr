package com.logicq.ngr.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.MessageHelper;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.report.UserReport;
import com.logicq.ngr.service.reportpack.ReportPackService;
import com.logicq.ngr.service.reportpack.ReportPackTemplateService;
import com.logicq.ngr.vo.MessageVO;
import com.logicq.ngr.vo.ReportPackDetailsVO;
import com.logicq.ngr.vo.ReportpackTemplateVO;
import com.logicq.ngr.vo.UserTemplateVO;

/**
 * 
 * This class is the entry point for all Report Pack requests
 *
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/reportpack")
@RestController
public class ReportPackController {

	@Autowired
	ReportPackService reportPackService;
	
	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	MessageHelper messageHelper;
	
	@Autowired
	ReportPackTemplateService reportPackTemplateService;
	

	@RequestMapping(value = "/createreportpack", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReportPackDetailsVO> createreportpack(@RequestBody ReportPackDetailsVO reportPackDetailsVO)
			throws Exception {
		securityHelper.setUserNameFromSecurityContext(reportPackDetailsVO);
		reportPackService.createreportpack(reportPackDetailsVO);
		return new ResponseEntity<ReportPackDetailsVO>(reportPackDetailsVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/movereporttoreportpack", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> moveReportToReportPack(@RequestBody List<UserTemplateVO> userTemplateVoList)
			throws Exception {
		MessageVO messageVO = new MessageVO();
		try{
			for (UserTemplateVO userTemplateVO : userTemplateVoList) {
				securityHelper.setUserNameFromSecurityContext(userTemplateVO);
			}
			reportPackService.moveReportPack(userTemplateVoList);
			messageVO=messageHelper.handleBusinessMessage(1018);
		}catch(Exception ex){
			throw new BusinessException(1019);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/addreport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> addreport(@RequestBody ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		securityHelper.setUserNameFromSecurityContext(reportpackTemplateVO);
		UserReport userReport = reportPackService.addreport(reportpackTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	@RequestMapping(value = "/movedashboardreports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> moveDashboardReports(@RequestBody UserTemplateVO userTemplateVo)
			throws Exception {
		MessageVO messageVO=new MessageVO();
		try{
		securityHelper.setUserNameFromSecurityContext(userTemplateVo);
		List<UserTemplateVO> userTemplateVoList=new ArrayList<UserTemplateVO>();
		userTemplateVoList.add(userTemplateVo);	
		reportPackService.moveReportPack(userTemplateVoList);
		messageVO=messageHelper.handleBusinessMessage(1020);
		}catch(Exception ex){
			throw new BusinessException(1021);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getallreportpack", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserReport> getAllReportPack() throws Exception {
		String userName=securityHelper.getUserNameFromSecurityContext();
		UserReport userReport=reportPackService.getAllReportPack(userName);
           return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getspecificreportpackreport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> getspecificreportpackreport(@RequestBody ReportpackTemplateVO reportpackTemplateVO)
			throws Exception {
		securityHelper.setUserNameFromSecurityContext(reportpackTemplateVO);
		UserReport userReport=reportPackService.getspecificreportpack(reportpackTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

	@RequestMapping(value = "/editreportpacktemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> editReportPackTemplate(@RequestBody ReportpackTemplateVO reportpackTemplateVO)throws Exception {
		UserReport userReport=reportPackTemplateService.editReportPackTemplate(reportpackTemplateVO);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getreportpackaccordingtofilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DashboardReport> getReportPackAccordingToFilter(@RequestBody ReportpackTemplateVO reportPacktemplatevo)
			throws Throwable {
		DashboardReport dashboardReport = reportPackService.getReportPackAccordingToFilter(reportPacktemplatevo);
		return new ResponseEntity<DashboardReport>(dashboardReport, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletereportpack", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> deleteReportPackTemplate(@RequestBody ReportpackTemplateVO reportpackTemplateVO)
			throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			if (null != reportpackTemplateVO) {
				if (!ReportHelper.isEmpty(reportpackTemplateVO.getReportpackTemplateId())) {
					reportPackTemplateService.deleteReportPackTemplate(reportpackTemplateVO);
					messageVO = messageHelper.handleBusinessMessage(1024);
				} else {
					ReportPackDetailsVO reportPackDetailsVO = new ReportPackDetailsVO();
					reportPackDetailsVO.setReportpackId(reportpackTemplateVO.getReportpackId());
					reportPackService.deleteReportPack(reportPackDetailsVO);
					reportPackTemplateService.deleteReportPackTemplate(reportpackTemplateVO);
					messageVO = messageHelper.handleBusinessMessage(1025);
				}
			}
		} catch (Exception e) {
			throw new BusinessException(1026);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}
}
