package com.shinetech.dalian.mikado.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.PackagingDao;
import com.shinetech.dalian.mikado.dao.SeedDao;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.service.SeedService;

/**
 * 
 * @author abc
 *
 */
@Service
public class SeedServiceImpl implements SeedService {
	@Autowired
	private SeedDao seedDao;
	@Autowired
	private PackagingDao packagingDao;
	
	/**
	 * Get seed by id
	 */
	@Override
	public SeedEntity getSeedById(Integer id) {
		return seedDao.getSeedById(id);
	}
	
	/**
	 * Insert a seed information
	 */
	@Override
	public void saveSeed(SeedEntity seed) {
		seedDao.saveSeed(seed);
		
	}
	
	/**
	 * Get all seeds from DB
	 */
	@Override
	public List<SeedEntity> getAllSeeds() {
		return seedDao.listAllSeeds();
	}

//	@Override
//	public List<SeedEntity> getSeedsListByPage(BsTablePagination bsPage) {
//		return seedDao.getSeedsListByPage(bsPage);
//	}
	
	/**
	 * Get summary of seed 
	 */
	@Override
	public int getAllSeedSize() {
		return seedDao.getAllSeedSize();
	}
	
	/**
	 * Get summary of seed by condition:variety,supplier,importLicence
	 */
	@Override
	public int getAllSeedSizeByCondition(Object variety, Object supplier,Object importLicence) {
		return 	seedDao.getAllSeedSizeByCondition(variety, supplier, importLicence);
	}
	
	/**
	 * Get seed data by search condition
	 * server side pagination function is being used
	 */
	@Override
	public Map<String, Object> getSeedByPagination(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SeedEntity> rows = new ArrayList<SeedEntity>();
		String speciesName = (String) params.get("speciesName");
		String importNumber = (String) params.get("importNumber");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		Boolean speciesNameFlag = true,importNumberFlag = true;
		if(speciesName == null || "".equals(speciesName.trim())){
			speciesNameFlag = false;
		}
		if(importNumber == null || "".equals(importNumber.trim())){
			importNumberFlag = false;
		}
		
		/*
		 * Get all seeds
		 */
		if( importNumberFlag == false && speciesNameFlag == false){
			total = seedDao.listSeedsByParams(null).size();
			rows = seedDao.listSeedsByParams(params);
		}
		/*
		 * Get seed list by commercial name
		 */
		if(importNumberFlag == false && speciesNameFlag == true){
			total = seedDao.listSeedsBySpeciesName(speciesName, null, null).size();
			rows = seedDao.listSeedsBySpeciesName(speciesName, start, maxResult);
		}
		/*
		 * Get seed list by import number
		 */
		if(importNumberFlag == true && speciesNameFlag == false){
			total = seedDao.listSeedsByImportNumber(importNumber, null, null).size();
			rows = seedDao.listSeedsByImportNumber(importNumber, start, maxResult);
		}
		/*
		 * Get seed list by commercial name and import number
		 */
		if(importNumberFlag == true && speciesNameFlag == true){
			total = seedDao.listSeedByImportNumberAndSpeciesName(importNumber, speciesName, null, null).size();
			rows = seedDao.listSeedByImportNumberAndSpeciesName(importNumber, speciesName, start, maxResult);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}

	@Override
	public void deleteSeed(SeedEntity seed) {
		seedDao.deleteSeed(seed);
		
	}
	
	/**
	 * Get all seed name 
	 */
	@Override
	public List<String> getSeedName() {
		return seedDao.listSeedName();
	}
	
	/**
	 * delete seed by id
	 */
	@Override
	public void deleteSeedById(Integer id) {
		
		seedDao.deleteSeedById(id);
		
	}
	
	/**
	 * Check if seed is associated by other tables
	 */
	@Override
	public boolean checkSeedBeAssociated(Integer seedId) {
		List<ProductionsEntity> productionList = packagingDao.listProductionsBySeedId(seedId);
		if(productionList != null && productionList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Get commercial names from seed table
	 */
	@Override
	public List<String> getConmercialNamesFromSeeds() {
		return seedDao.listUniqueConmercialNames();
	}
	
	/**
	 * Get species from seed
	 */
	@Override
	public List<SpeciesEntity> getSpeciesFromSeed() {
		
		return seedDao.listSpeciesFromSeed();
	}
	
	/**
	 * Get commercial name by species id
	 */
	@Override
	public List<String> getConmercialNameBySpecies(Integer speciesId) {
		
		return seedDao.listConmercialNameBySpecies(speciesId);
	}

	@Override
	public List<SeedEntity> getSeedList() {
		
		return seedDao.listAllSeeds();
	}

	@Override
	public void updateSurplusWeight(SeedEntity seed) {
		SeedEntity seedEntity = seedDao.getSeedById(seed.getId());
		seedEntity.setSurplusWeight(seed.getSurplusWeight());
		seedDao.saveSeed(seedEntity);
		
	}

	@Override
	public String getLotNumber(String code){
		 SeedEntity seedEntity =seedDao.getLastSeedLotNumber(code);
		 if(seedEntity==null){
			 return code+"0001";
		 }else{
			 String lastLotNumber=seedEntity.getLotNumber();
			 String index=lastLotNumber.substring(5);
			 Integer intIndex=Integer.parseInt(index);
			 intIndex++;
			 index=intIndex.toString();
			 int length=index.length();
			 for(int i=0;i<4-length;i++){
				 index="0"+index;
			 }
			 return code+index;
		 }
	}
}