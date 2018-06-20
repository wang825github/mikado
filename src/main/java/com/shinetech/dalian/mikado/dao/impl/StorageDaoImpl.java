package com.shinetech.dalian.mikado.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinetech.dalian.mikado.basedao.BaseDao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.StorageDao;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;

/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class StorageDaoImpl implements StorageDao {
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * Insert storage into DB
	 */
	@Override
	public void saveStorage(StorageEntity storage) {
		
		baseDao.save(storage);

	}
	
	/**
	 * Get storage by id
	 */
	@Override
	public StorageEntity getStorageById(Integer id) {
		
		return baseDao.get(StorageEntity.class, id);
	}
	
	/**
	 * Delete storage by id
	 */
	@Override
	public void deleteStorageById(Integer id) {
		
		baseDao.delete(StorageEntity.class, id);
		
	}
	
	/**
	 * Edit storage information
	 */
	@Override
	public void editStorage(StorageEntity storage) {
		
		baseDao.save(storage);
		
	}

}
