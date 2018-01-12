package com.logicq.ngr.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

	private static final long serialVersionUID = 114654645465464L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int taskId;

	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "TASK_STATUS")
	private String taskStatus;

	@NotNull
	@Column(name = "TASK_EXECUTIONTIME", nullable = false)
	private String taskExecutionTime;

	@NotNull
	@Column(name = "TASK_EXECUTIONDATE", nullable = false)
	private String taskExecutionDate;

	@Column(name = "TASK_TYPE")
	private String taskType;

	@Lob
	@Column(name = "TASK_PARAM_VALUE")
	private byte[] taskParamValue;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private UserDetails userDetails;

	@Column(name = "DASHBOARD_ID")
	private String dashboardId;

	@Column(name = "USERTEMPLATE_ID")
	private String templateId;

	@Column(name = "DELETED", nullable = false, columnDefinition = "boolean default false")
	private boolean deleted;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "task")
	private List<TaskStatus> taskStatusList;
	
	

	public List<TaskStatus> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<TaskStatus> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public byte[] getTaskParamValue() {
		return taskParamValue;
	}

	public void setTaskParamValue(byte[] taskParamValue) {
		this.taskParamValue = taskParamValue;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", taskStatus=" + taskStatus
				+ ", taskExecutionTime=" + taskExecutionTime + ", taskExecutionDate=" + taskExecutionDate
				+ ", taskType=" + taskType + ", taskParamValue=" + Arrays.toString(taskParamValue) + "]";
	}

}
