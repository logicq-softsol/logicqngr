package com.logicq.ngr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.TaskStatusDao;
import com.logicq.ngr.model.TaskStatus;
import com.logicq.ngr.service.TaskStatusService;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
	
	@Autowired
	TaskStatusDao taskStatusDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void createTaskStatus(TaskStatus taskStatus) throws Exception {
		taskStatusDao.createTaskStatus(taskStatus);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void updateTaskStatus(TaskStatus taskStatus) {
		taskStatusDao.updateTaskStatus(taskStatus);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TaskStatus> getTaskStatusByTaskId(int taskid) {
		return taskStatusDao.getTaskStatusByTaskId(taskid);
	}

}
