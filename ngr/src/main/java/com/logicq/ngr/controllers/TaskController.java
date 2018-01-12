package com.logicq.ngr.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.ngr.helper.SecurityHelper;
import com.logicq.ngr.model.Task;
import com.logicq.ngr.service.TaskService;
import com.logicq.ngr.vo.TaskVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SecurityHelper securityHelper;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskVO> createTask(@RequestBody TaskVO taskVO) throws Exception {
		taskService.createTask(taskVO);
		taskVO.setMessage("Task created Succesfully.");
		taskVO.setMessagecode(200);
		return new ResponseEntity<TaskVO>(taskVO, HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "/gettasksbyuser", method =RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> getTaskByUser() {
		List<Task> tasks=null;
		try {
			tasks = taskService.getTasksByUser(securityHelper.getUserNameFromSecurityContext());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Task>>(tasks, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
}
