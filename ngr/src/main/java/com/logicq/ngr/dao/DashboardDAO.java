package com.logicq.ngr.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.vo.UserTemplateVO;

public interface DashboardDAO {

	public List<Dashboard> getUserDashboards(UserTemplateVO userTemplateVO) throws Exception;

	public Dashboard getUserDashboard(String dashboardId, Long userId) throws Exception;

	public Dashboard addDashboard(Dashboard dashboard);

	public Dashboard getDashboardType(String userId);

	public void deleteDashboard(Dashboard dashboard);

	public void update(Dashboard dashboard);

	public void updateDashboard(List<Dashboard> dashboards);

	public List<Dashboard> getDeletedMarkDashboard();

	Dashboard updateDashboard(Dashboard dashboard);

	List<Dashboard> getDashboardsAccodingToType(Map<String, Set> values) throws Exception;

	public void deleteMarkDashboard(List<Dashboard> markDeletDashboardList);
}
