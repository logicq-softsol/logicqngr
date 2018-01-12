package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.StorageDetails;
import com.logicq.ngr.model.UserDetails;

public interface StorageDetailsService {

	public void saveStorageDetails(StorageDetails storageDetails);

	public void updateStorageDetails(UserDetails userDetails);

	public void deleteUserStorageByDashbordId(List<String> dashboardIds, boolean b);
}
