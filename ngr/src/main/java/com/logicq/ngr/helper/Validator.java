package com.logicq.ngr.helper;

import org.springframework.stereotype.Component;

import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.UserTemplate;

@Component
public class Validator {
	
	public boolean isValidDashboard(Dashboard dashBoardDetails) {
		return !dashBoardDetails.isDeleted();
	}
	
	public boolean isValidTask(Task task){
		return !task.isDeleted();
	}
	
	
	public boolean isValidUserTemplate(UserTemplate userTemplate) {
		return !userTemplate.isDeleted();
	}
	
}
