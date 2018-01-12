package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.TaskStatus;

public interface TaskStatusService {
	void createTaskStatus(TaskStatus taskStatus) throws Exception;

	void updateTaskStatus(TaskStatus taskStatus);
	
	List<TaskStatus> getTaskStatusByTaskId(int taskid);
}
