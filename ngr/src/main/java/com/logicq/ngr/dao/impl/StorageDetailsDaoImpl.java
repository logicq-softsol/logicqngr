package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.StorageDetailsDao;
import com.logicq.ngr.model.StorageDetails;
import com.logicq.ngr.model.UserDetails;

@Repository
public class StorageDetailsDaoImpl extends AbstractDAO<StorageDetails> implements StorageDetailsDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 503003035955170931L;

	@Override
	public void saveStorageDetails(StorageDetails storageDetails) {
		save(storageDetails);
	}

	@Override
	public List<StorageDetails> getMarkDeletedStorageDetails(UserDetails userDetails) {
		StringBuilder query = new StringBuilder();
		query.append("from StorageDetails sd where sd.userId = " + userDetails.getId());
		query.append(" And  deleted = true");
		return executeQuery(query.toString());
	}

	@Override
	public void updateStorageDetails(List<StorageDetails> storageList) {
	   updateList(storageList);
	}

	@Override
	public void deleteStorageDetails(List<StorageDetails> storagelist) {
		deleteList(storagelist);
	}

	@Override
	public List<StorageDetails> getStorageDetailsByDashbordId(List<String> dashboardIds) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from StorageDetails sd where sd.dashboardId IN :list");
		return (List<StorageDetails>) executeQueryWithList(selectQuery.toString(), dashboardIds);
	}

	@Override
	public List<StorageDetails> getStorageDetails(UserDetails userDetails) {
		StringBuilder query = new StringBuilder();
		query.append("from StorageDetails sd where sd.userId = " + userDetails.getId());
		return executeQuery(query.toString());
	}

}
