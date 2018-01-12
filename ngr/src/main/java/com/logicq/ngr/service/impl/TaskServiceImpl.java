package com.logicq.ngr.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.DateHelper;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.dao.TaskDao;
import com.logicq.ngr.helper.DynsHelper;
import com.logicq.ngr.helper.ReportDownloadHelper;
import com.logicq.ngr.helper.SchedulerPropertyHelper;
import com.logicq.ngr.helper.Validator;
import com.logicq.ngr.model.StorageDetails;
import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.TaskStatus;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.response.ExportDetails;
import com.logicq.ngr.service.StorageDetailsService;
import com.logicq.ngr.service.TaskService;
import com.logicq.ngr.service.TaskStatusService;
import com.logicq.ngr.service.UserAdminService;
import com.logicq.ngr.service.report.DashboardService;
import com.logicq.ngr.service.report.ReportDownloadService;
import com.logicq.ngr.service.report.ReportService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.util.ObjectFactory;
import com.logicq.ngr.vo.TaskVO;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskDao taskDao;

	@Autowired
	TaskStatusService taskStatusService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	ReportDownloadHelper reportDownloadHelper;

	@Autowired
	DynsHelper dynsHelper;

	@Autowired
	SchedulerPropertyHelper helper;

	@Autowired
	ReportDownloadService reportDownloadService;

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	ReportService reportService;

	@Autowired
	StorageDetailsService storageDetailsService;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	Validator validator;

	private ObjectMapper mapper = ObjectFactory.getObjectMapper();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void updateTask(Task task) {
		taskDao.saveORupdate(task);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Task> getTaskDetails(Long seconds) {
		return taskDao.getTaskDetails(seconds);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void createTask(TaskVO taskVO) throws Exception {
		if ("EVENT_POLICY".equalsIgnoreCase(taskVO.getTaskName())) {
			List<Task> taskList = taskDao.getTaskAccordingToTaskName(taskVO.getTaskName());
			Map<String, Object> eventPolicyMap = taskVO.getTaskParamValue().getJsonString();
			createArchiveEventTask(taskVO, taskList, eventPolicyMap);
		} else {
			Task task = prepareTask(taskVO);
			taskDao.createTask(task);
		}
	}

	private Task prepareTask(TaskVO taskVO) throws Exception {
		Task task = new Task();

		UserTemplateVO templateVO = taskVO.getTaskParamValue();
		// Map<String, Object> taskParamValMap = taskVO.getTaskParamValue();
		String userName = templateVO.getUserName();

		task.setUserDetails(userAdminService.getUser(userName));

		if (taskVO.getTaskName() != null) {
			task.setTaskName(taskVO.getTaskName());
		} else {
			throw new BusinessException("TaskName can not be Null");
		}
		if (taskVO.getDashboardId() != null) {
			task.setDashboardId(taskVO.getDashboardId());
			Dashboard dashboard = dashboardService.getUserDashboard(taskVO.getDashboardId(),
					task.getUserDetails().getId());
			if (!validator.isValidDashboard(dashboard)) {
				throw new BusinessException(1030);
			}
		} else {
			throw new BusinessException("DashboardId can not be Null");
		}
		if (taskVO.getUserTemplateId() != null) {
			task.setTemplateId(taskVO.getUserTemplateId());
		} else {
			throw new BusinessException("UserTemplateId can not be Null");
		}

		if (taskVO.getTaskType() != null) {
			task.setTaskType(taskVO.getTaskType());
			task.setTaskExecutionDate(DateHelper.getTaskExecutionDate(taskVO));

		} else {
			throw new BusinessException("TaskType can not be Null");
		}

		if (taskVO.getTaskExecutionTime() != null) {
			task.setTaskExecutionTime(taskVO.getTaskExecutionTime());
		} else {
			throw new BusinessException("TaskExecutionTime can not be Null");
		}

		if (taskVO.getTaskParamValue() != null) {
			task.setTaskParamValue(mapper.writeValueAsBytes(taskVO.getTaskParamValue()));
		} else {
			throw new BusinessException("TaskParamValue can not be Null");
		}

		task.setTaskStatus("wait");
		return task;
	}

	/**
	 * @param taskVO
	 * @param taskList
	 * @param eventPolicyMap
	 * @throws JsonProcessingException
	 * @throws Exception
	 * @throws BusinessException
	 * @throws NumberFormatException
	 */
	public void createArchiveEventTask(TaskVO taskVO, List<Task> taskList, Map<String, Object> eventPolicyMap)
			throws JsonProcessingException, Exception, BusinessException, NumberFormatException {
		if (null != eventPolicyMap && !eventPolicyMap.isEmpty()) {
			Task inputTask = null;
			Map<String, Object> inputTaskParam = null;
			for (Object key : eventPolicyMap.keySet()) {
				inputTaskParam = (Map<String, Object>) eventPolicyMap.get(key);
				if (null != taskList && !taskList.isEmpty()) {
					for (Task task : taskList) {
						if (null != inputTaskParam.get("taskId")
								&& !inputTaskParam.get("taskId").toString().isEmpty()) {
							Integer inputTaskId = Integer.valueOf(inputTaskParam.get("taskId").toString());
							if (task.getTaskId() == inputTaskId) {
								setTaskDetails(taskVO, task, inputTaskParam);
								taskDao.updateTask(task);
							}
						}
					}

				} else {
					inputTask = new Task();
					if (key.toString().equalsIgnoreCase("archivepolicy")) {
						inputTask.setTaskName("EVENT_POLICY_ARCHIVE");
					} else if (key.toString().equalsIgnoreCase("deletepolicy")) {
						inputTask.setTaskName("EVENT_POLICY_DELETE");
					}
					setTaskDetails(taskVO, inputTask, inputTaskParam);
					taskDao.createTask(inputTask);
				}
			}
		}
	}

	/**
	 * @param taskVO
	 * @param inputTask
	 * @param inputTaskParam
	 * @throws JsonProcessingException
	 * @throws Exception
	 * @throws BusinessException
	 */
	public void setTaskDetails(TaskVO taskVO, Task inputTask, Map<String, Object> inputTaskParam)
			throws JsonProcessingException, Exception, BusinessException {

		inputTask.setTaskParamValue(mapper.writeValueAsBytes(inputTaskParam));
		taskVO.setTaskType((String) inputTaskParam.get("taskType"));
		taskVO.setTaskExecutionDate((String) inputTaskParam.get("taskExecutionDate"));
		taskVO.setTaskExecutionTime((String) inputTaskParam.get("taskExecutionTime"));
		taskVO.setTaskExecutionMonth((String) inputTaskParam.get("taskExecutionMonth"));
		taskVO.setTaskExecutionDay((String) inputTaskParam.get("taskExecutionDay"));

		if (taskVO.getTaskType() != null) {
			inputTask.setTaskType(taskVO.getTaskType());
			inputTask.setTaskExecutionDate(DateHelper.getTaskExecutionDate(taskVO));
		} else {
			throw new BusinessException("TaskType can not be Null");
		}

		if (taskVO.getTaskExecutionTime() != null) {
			inputTask.setTaskExecutionTime(taskVO.getTaskExecutionTime());
		} else {
			throw new BusinessException("TaskExecutionTime can not be Null");
		}
		inputTask.setTaskStatus("wait");
	}

	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public List<Task> getTasksByUser(String userName) {
		UserDetails userDetails = userAdminService.getUser(userName);
		return taskDao.getTasksByUser(userDetails);
	}

	@Scheduled(fixedDelayString = "${scheduled.report.delete.time.seconds}000")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void scheduledMarkDelete() {
		userAdminService.handleMarkDelete();
	}

	@Scheduled(fixedDelayString = "${scheduled.cron.time.seconds}000")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void executeReportScheduler() throws Exception {
		DateFormat timeFormat = new SimpleDateFormat(DateHelper.getCurrentTimeAsHHMMSS());
		Long seconds = Long.parseLong(helper.getTimer());
		List<Task> tasks = taskDao.getTaskDetails(seconds);
		UserTemplateVO userTemplateVO = null;
		for (Task task : tasks) {
			TaskStatus taskStatus = new TaskStatus();
			taskStatus.setTaskPickedTime(timeFormat.format(new Date()));
			task.setTaskStatus("picked");
			// taskService.updateTask(task);
			try {
				userTemplateVO = mapper.readValue(new String(task.getTaskParamValue()),
						new TypeReference<UserTemplateVO>() {
						});
				StorageDetails storageDetails = new StorageDetails();
				storageDetails.setDashboardId(userTemplateVO.getDashboardId());
				storageDetails.setUserId(userTemplateVO.getUserId());
				storageDetails.setUserTemplateId(userTemplateVO.getUserTemplateId());
				exportReports(userTemplateVO, storageDetails);
				taskStatus.setTaskExecutionStatus("Success");
				taskStatus.setComments(task.getTaskType() + " task successfully completed");
				task.setTaskStatus("wait");
				storageDetailsService.saveStorageDetails(storageDetails);

			} catch (Exception ex) {
				taskStatus.setTaskExecutionStatus("Failure");
				taskStatus.setComments(task.getTaskType() + " Task failure..." + "Reason " + ex.getMessage());

				reportDownloadHelper.sendMail(null, null, userTemplateVO.getUserName(), null, userTemplateVO,
						taskStatus.getComments());
			}
			taskStatus.setTaskEndTime(timeFormat.format(new Date()));
			reportDownloadHelper.createTaskStatus(taskStatus, task);
			reportDownloadHelper.updateTaskDetails(task);
			updateTask(task);
		}
	}

	private void exportReports(UserTemplateVO userTemplateVO, StorageDetails storageDetails) throws Exception {

		if (null == userTemplateVO.getJsonString()) {
			Map<String, Object> jsonString = new HashMap<String, Object>();
			userTemplateVO.setJsonString(jsonString);
		}

		ExportDetails exportDetails = new ExportDetails();
		exportDetails.setUserReport(reportService.getReportAccordingToFilter(userTemplateVO));
		exportDetails.setFileHeaderList(new ArrayList<>());
		exportDetails.setFileContent(reportDownloadHelper.processDruidResponseData(exportDetails));
		exportDetails.setReportType(userTemplateVO.getFileType());
		exportDetails.setReportFlag(false);
		exportDetails.getUserReport()
				.setEmail(LoadApplicationData.userMap.get(userTemplateVO.getUserName()).getEmail());
		reportDownloadHelper.generateReport(exportDetails);
		UserTemplate usertemplate = userTemplateService.getUserTemplate(userTemplateVO);
		Map<String, Object> inputjson = mapper.readValue(new String(usertemplate.getJsonString()),
				new TypeReference<Map<String, Object>>() {
				});
		String fileName = reportDownloadHelper.exctractReportName(inputjson);
		String pathURL = exportDetails.getFilePath();
		reportDownloadHelper.sendMail(pathURL, userTemplateVO.getFileType(), userTemplateVO.getUserName(), fileName,
				userTemplateVO, null);
		storageDetails.setFilePath(exportDetails.getFilePath());
		storageDetails.setFilePathUrl(exportDetails.getFilePathURL());
	}

	@Override
	public List<Task> getTasksAccordingToTaskName(String taskName) {
		return taskDao.getTaskAccordingToTaskName(taskName);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void deleteUserTaskByDashbordId(List<String> dashboardIds) {
		if (dashboardIds != null && !dashboardIds.isEmpty()) {
			List<Task> taskList = taskDao.getTaskByDashboardId(dashboardIds);
			if (taskList != null && !taskList.isEmpty()) {
				taskDao.deleteList(taskList);
			}
		}
	}

	@Override
	public void updateUserTask(UserDetails userDetails) {
		List<Task> taklist = taskDao.getTasksByUser(userDetails);
		List<Task> tasks = new ArrayList<Task>();
		taklist.forEach(task -> {
			task.setDeleted(true);
			tasks.add(task);
		});
		taskDao.updateUserTask(tasks);
	}

}