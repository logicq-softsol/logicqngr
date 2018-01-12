package com.logicq.ngr.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.StorageDetailsDao;
import com.logicq.ngr.model.StorageDetails;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.service.StorageDetailsService;
import com.logicq.ngr.service.UserAdminService;

@Service
public class StorageDetailsServiceImpl implements StorageDetailsService {

	@Autowired
	StorageDetailsDao storageDetailsDao;

	@Autowired
	UserAdminService userAdminService;

	private static final Logger logger = Logger.getLogger(StorageDetailsServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void saveStorageDetails(StorageDetails storageDetails) {
		storageDetailsDao.saveStorageDetails(storageDetails);
	}

	/**
	 * This method use for deleting file.
	 * 
	 * @param filePath
	 */
	public static void deleteFolder(String filePath) {

		File directory = new File(filePath);
		// make sure directory exists
		if (directory.exists()) {
			try {
				delete(directory);
			} catch (IOException ex) {
				logger.error("Delete folder structure failed ", ex);
			}
		}
	}

	/**
	 * This method will delete directory and file also.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			// if file, then delete it
			file.delete();
		}
	}

	@Override
	public void updateStorageDetails(UserDetails userDetails) {
		List<StorageDetails> storageDetails = storageDetailsDao.getStorageDetails(userDetails);
		List<StorageDetails> storageList = new ArrayList<>();
		storageDetails.forEach(storage -> {
			storage.setDeleted(true);
			storageList.add(storage);
		});
		storageDetailsDao.updateStorageDetails(storageList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUserStorageByDashbordId(List<String> dashboardIds, boolean isUserDelete) {

		if (dashboardIds != null && !dashboardIds.isEmpty()) {
			List<StorageDetails> storageDetails = storageDetailsDao.getStorageDetailsByDashbordId(dashboardIds);
			if (storageDetails != null && !storageDetails.isEmpty()) {
				if (isUserDelete) { // if delete user , delete complete folder
									// structure of that user
					storageDetails.forEach(storage -> {
						deleteFolder(storage.getFilePath());
					});
				} else {

					storageDetails.forEach(storage -> {
						String filStr = storage.getFilePath();
						String filePath = filStr.substring(0,
								filStr.indexOf(storage.getDashboardId()) + storage.getDashboardId().length());
						deleteFolder(filePath);
					});

				}
				storageDetailsDao.deleteStorageDetails(storageDetails);
			}
		}
	}
}
