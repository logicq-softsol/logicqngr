package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.UserDetails;

public interface TaskDao {
	void createTask(Task task);

	void saveORupdate(Task task);

	List<Task> getTaskDetails(Long seconds);

	List<Task> getTaskAccordingToTaskName(String taskName);

	void updateTask(Task task);

	List<Task> getTasksByUser(UserDetails userDetails);

	void updateUserTask(List<Task> taklist);

	List<Task> getTaskByDashboardId(List<String> dashboardIds);

	void deleteList(List<Task> taskList);

	void deleteTaskStatus(List<Integer> taskIds);

}