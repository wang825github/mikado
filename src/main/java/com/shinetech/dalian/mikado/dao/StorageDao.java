package com.shinetech.dalian.mikado.dao;

import com.shinetech.dalian.mikado.entity.StorageEntity;
/**
 * 
 * @author abc
 *
 */
public interface StorageDao {
	/**
	 * Insert storage into DB
	 * @param storage
	 */
	void saveStorage(StorageEntity storage);
	
	/**
	 * Get storage information by ID
	 * @param id
	 * @return
	 */
	StorageEntity getStorageById(Integer id);
	
	/**
	 * Delete storage by id
	 * @param id
	 */
	void deleteStorageById(Integer id);
	
	/**
	 * Update a storage information
	 * @param storage
	 */
	void editStorage(StorageEntity storage);
}
