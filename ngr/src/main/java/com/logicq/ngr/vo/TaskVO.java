package com.logicq.ngr.vo;

import java.io.Serializable;

/**
 * 
 * @author 611147071
 *
 */
public class TaskVO extends MessageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 322949719295587119L;

	private int taskId;

	private String taskExecutionTime;

	private String taskExecutionDay;

	private String taskType;

	private String taskExecutionMonth;

	private String taskExecutionDate;

	private String taskStatus;

	private String taskName;

	private String userName;

	private String dashboardId;

	private String userTemplateId;

	// private Map<String, Object> taskParamValue;

	private UserTemplateVO taskParamValue;

	public UserTemplateVO getTaskParamValue() {
		return taskParamValue;
	}

	public void setTaskParamValue(UserTemplateVO taskParamValue) {
		this.taskParamValue = taskParamValue;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskExecutionTime() {
		return taskExecutionTime;
	}

	public void setTaskExecutionTime(String taskExecutionTime) {
		this.taskExecutionTime = taskExecutionTime;
	}

	public String getTaskExecutionDate() {
		return taskExecutionDate;
	}

	public void setTaskExecutionDate(String taskExecutionDate) {
		this.taskExecutionDate = taskExecutionDate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskExecutionDay() {
		return taskExecutionDay;
	}

	public void setTaskExecutionDay(String taskExecutionDay) {
		this.taskExecutionDay = taskExecutionDay;
	}

	public String getTaskExecutionMonth() {
		return taskExecutionMonth;
	}

	public void setTaskExecutionMonth(String taskExecutionMonth) {
		this.taskExecutionMonth = taskExecutionMonth;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getUserTemplateId() {
		return userTemplateId;
	}

	public void setUserTemplateId(String userTemplateId) {
		this.userTemplateId = userTemplateId;
	}

	@Override
	public String toString() {
		return "TaskVO [taskId=" + taskId + ", taskExecutionTime=" + taskExecutionTime + ", taskExecutionDay="
				+ taskExecutionDay + ", taskType=" + taskType + ", taskExecutionMonth=" + taskExecutionMonth
				+ ", taskExecutionDate=" + taskExecutionDate + ", taskStatus=" + taskStatus + ", taskName=" + taskName
				+ ", userName=" + userName + ", dashboardId=" + dashboardId + ", userTemplateId=" + userTemplateId
				+ ", taskParamValue=" + taskParamValue + "]";
	}
}
