package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.TaskStatusDao;
import com.logicq.ngr.model.TaskStatus;

@Repository
public class TaskStatusDaoImpl extends AbstractDAO<TaskStatus> implements TaskStatusDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6926330101832185528L;

	@Override
	public void createTaskStatus(TaskStatus taskStatus) {
		save(taskStatus);
	}

	@Override
	public void updateTaskStatus(TaskStatus taskStatus) {
		saveOrUpdate(taskStatus);
		}

	@Override
	public List<TaskStatus> getTaskStatusByTaskId(int taskid) {
		String query = "from TaskStatus ts where ts.task='" + taskid + "'";
		return executeQuery(query);
	}
}
