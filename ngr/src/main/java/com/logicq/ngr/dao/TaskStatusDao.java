package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.TaskStatus;

public interface TaskStatusDao {
	void createTaskStatus(TaskStatus taskStatus);

	void updateTaskStatus(TaskStatus taskStatus);
	
	List<TaskStatus> getTaskStatusByTaskId(int taskid);
}
