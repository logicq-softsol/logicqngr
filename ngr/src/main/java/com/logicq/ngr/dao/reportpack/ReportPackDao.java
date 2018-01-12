package com.logicq.ngr.dao.reportpack;

import java.util.List;

import com.logicq.ngr.model.reportpack.ReportpackDetails;

public interface ReportPackDao {

	void addReportpack(ReportpackDetails reportPackDashboardDetails)throws Exception;

	ReportpackDetails getReportPack(String reportpackId)throws Exception;
	
	List<ReportpackDetails> getReportPack();

	void deleteReportPack(ReportpackDetails reportpackDetails);
}
