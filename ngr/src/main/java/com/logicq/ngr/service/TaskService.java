package com.logicq.ngr.service;

import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.vo.TaskVO;

public interface TaskService {
	
	void createTask(TaskVO taskVO) throws Exception;

	void updateTask(Task task);

	List<Task> getTaskDetails(Long seconds);
	
	List<Task> getTasksByUser(String userName);
	
	List<Task> getTasksAccordingToTaskName(String taskName);

	void deleteUserTaskByDashbordId(List<String> dashboardIds);

	void updateUserTask(UserDetails userDetails);

}