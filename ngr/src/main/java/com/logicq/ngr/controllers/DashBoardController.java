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

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.MessageHelper;
import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.service.report.DashboardService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.vo.DashBoardVO;
import com.logicq.ngr.vo.MessageVO;
import com.logicq.ngr.vo.UserTemplateVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/dashBoard")
@RestController
public class DashBoardController {

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	MessageHelper messageHelper;

	@RequestMapping(value = "/copyToDashBoard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> copyToDashBoard(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			if (null != userTemplateVO) {
				securityHelper.setUserNameFromSecurityContext(userTemplateVO);
				userTemplateService.copyUserTemplate(userTemplateVO);
				messageVO = messageHelper.handleBusinessMessage(1009);
			}
		} catch (Exception e) {
			throw new BusinessException(1010);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteDashBoard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> deleteDashBoard(@RequestBody UserTemplateVO userTemplateVO) throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			if (null != userTemplateVO) {
				securityHelper.setUserNameFromSecurityContext(userTemplateVO);
				dashboardService.deleteDashboard(userTemplateVO);
				messageVO = messageHelper.handleBusinessMessage(1011);
			}
		} catch (Exception e) {
			throw new BusinessException(1012);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/adddashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dashboard> addDashboard(@RequestBody DashBoardVO dashboardVO) throws Throwable {
		
		securityHelper.setUserNameFromSecurityContext(dashboardVO);
		Dashboard dashboard = dashboardService.addDashboard(dashboardVO);
		return new ResponseEntity<Dashboard>(dashboard, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/editdashboardproperty", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> editDashBoardProperty(@RequestBody DashBoardVO dashBoardVO) throws Exception {
		MessageVO messageVO = new MessageVO();
		if (null != dashBoardVO) {
			try {
				dashboardService.updateDashBoardViewType(dashBoardVO);
				messageVO = messageHelper.handleBusinessMessage(1015);
			} catch (Exception e) {
				throw new BusinessException(1016);
			}
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/savearrangementdetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> saveArrangementDetail(@RequestBody DashBoardVO dashBoardVO) throws Exception {
		MessageVO messageVO = new MessageVO();
		if (null != dashBoardVO) {
			try {
				dashboardService.saveArrangementDetail(dashBoardVO);
				messageVO = messageHelper.handleBusinessMessage(1015);
			} catch (Exception e) {
				throw new BusinessException(1016);
			}
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}

}
