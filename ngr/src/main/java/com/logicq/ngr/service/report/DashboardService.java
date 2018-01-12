package com.logicq.ngr.service.report;

import java.util.List;
import java.util.Set;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.vo.DashBoardVO;
import com.logicq.ngr.vo.UserTemplateVO;

public interface DashboardService {

	public Dashboard addDashboard(DashBoardVO dashBoardVO) throws Exception;

	public void deleteDashboard(UserTemplateVO userTemplateVO) throws Exception;

	public Dashboard getUserDashboard(String dashboardId, Long userId) throws Exception;

	void updateDashBoardViewType(DashBoardVO dashBoardVo) throws Exception;

	public void updateDashBoard(UserDetails userDetails);

	public void deleteMarkDashboard(List<Dashboard> markDeletDashboardList);

	void saveArrangementDetail(DashBoardVO dashBoardVo) throws Exception;

	List<Dashboard> getDashboardsAccodingToType(Set<String> dashboardTypes, Set<UserDetails> users) throws Exception;

	void markDashboardsAndReportsAsDeleted(Set<UserDetails> users, Set<String> dashboardType) throws Exception;

	void createDashboardsFromProfile(Set<UserDetails> users, Set<String> newDashboardsTypes) throws Exception;

}
