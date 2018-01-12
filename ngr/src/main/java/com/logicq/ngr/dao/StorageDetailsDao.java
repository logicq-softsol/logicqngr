package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.StorageDetails;
import com.logicq.ngr.model.UserDetails;

public interface StorageDetailsDao {

	public void saveStorageDetails(StorageDetails storageDetails);

	public List<StorageDetails> getMarkDeletedStorageDetails(UserDetails userDetails);

	public void update(StorageDetails storageDetails);

	public void updateStorageDetails(List<StorageDetails> storageList);

	public void deleteStorageDetails(List<StorageDetails> storagelist);

	public List<StorageDetails> getStorageDetailsByDashbordId(List<String> dashboardIds);

	public List<StorageDetails> getStorageDetails(UserDetails userDetails);
}
