package com.logicq.ngr.vo;

import java.io.Serializable;

public class TaskStatusVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7762201048280048839L;

	private int statusId;
	
	private String comments;

	private String taskStatus;

	private Boolean isTaskActive;
	
	private int taskid;

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Boolean getIsTaskActive() {
		return isTaskActive;
	}

	public void setIsTaskActive(Boolean isTaskActive) {
		this.isTaskActive = isTaskActive;
	}

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	@Override
	public String toString() {
		return "TaskStatusVO [statusId=" + statusId + ", comments=" + comments + ", taskStatus=" + taskStatus
				+ ", isTaskActive=" + isTaskActive + ", taskid=" + taskid + "]";
	}
}
