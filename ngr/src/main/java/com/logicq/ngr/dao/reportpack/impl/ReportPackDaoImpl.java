package com.logicq.ngr.dao.reportpack.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.reportpack.ReportPackDao;
import com.logicq.ngr.model.reportpack.ReportpackDetails;

@Repository
public class ReportPackDaoImpl extends AbstractDAO<ReportpackDetails>implements ReportPackDao {

	/**
	 * This class deals with basic CRUD Operations related to Report Pack
	 */
	private static final long serialVersionUID = 167711096759186096L;

	@Override
	public void addReportpack(ReportpackDetails reportPackDetails) {
		save(reportPackDetails);
	}

	@Override
	public ReportpackDetails getReportPack(String reportpackId) {
		String query = "from ReportpackDetails rp where rp.reportpackId='" + reportpackId + "'";
		return executeQueryForUniqueRecord(query);
	}
	
	@Override
	public List<ReportpackDetails> getReportPack() {
		String query = "from ReportpackDetails";
		return executeQuery(query);
	}

	@Override
	public void deleteReportPack(ReportpackDetails reportpackDetails) {
		delete(reportpackDetails);
	}
}
