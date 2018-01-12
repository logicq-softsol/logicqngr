package com.logicq.ngr.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.TaskDao;
import com.logicq.ngr.helper.ReportDownloadHelper;
import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.UserDetails;

@Repository
public class TaskDaoImpl extends AbstractDAO<Task> implements TaskDao {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ReportDownloadHelper.class);

	private static final long serialVersionUID = 1011309194863372881L;

	@Override
	public void createTask(Task task) {
		save(task);
	}

	@Override
	public void saveORupdate(Task task) {
		saveOrUpdate(task);
	}

	@Override
	public void updateTask(Task task) {
		update(task);
	}

	@Override
	public List<Task> getTaskDetails(Long seconds) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String startTime = timeFormat.format(new Date());
		String endTime = timeFormat.format(new Date().getTime() + TimeUnit.SECONDS.toMillis(seconds));
		String query = "From Task where deleted = false AND taskExecutionDate = " + "'" + date + "'"
				+ " AND taskExecutionTime >= " + "'" + startTime + "'" + " AND taskExecutionTime <= " + "'" + endTime
				+ "' order by taskExecutionTime";
		logger.info(query);
		System.out.println(query);
		return executeQuery(query);
	}

	@Override
	public List<Task> getTaskAccordingToTaskName(String taskName) {
		StringBuilder query = new StringBuilder();
		query.append(" from Task ta where ta.taskName like'" + taskName + "%'");
		return executeQuery(query.toString());
	}

	@Override
	public List<Task> getTasksByUser(UserDetails userDetails) {
		String query = "from Task t where t.userDetails.id = " + userDetails.getId();
		return executeQuery(query);
	}

	@Override
	public void deleteTaskStatus(List<Integer> taskIds) {
		StringBuilder deleteTaskStatusQuery = new StringBuilder();
		deleteTaskStatusQuery.append("delete from TaskStatus ts where ts.task.taskId IN :list");
		updateList(deleteTaskStatusQuery.toString(), taskIds);
	}

	@Override
	public void updateUserTask(List<Task> taklist) {
		updateList(taklist);
	}

	@Override
	public List<Task> getTaskByDashboardId(List<String> dashboardIds) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from Task t where t.dashboardId IN :list");
		return (List<Task>)executeQueryWithList(selectQuery.toString(),dashboardIds);
	}

}