package com.shinetech.dalian.mikado.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.SeedDao;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;

/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class SeedDaoImpl implements SeedDao {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * Get seed by id
	 */
	@Override
	public SeedEntity getSeedById(Integer id) {
		return baseDao.get(SeedEntity.class, id);
	}
	
	/**
	 * Insert a seed into DB
	 */
	@Override
	public void saveSeed(SeedEntity seed) {
		baseDao.save(seed);
		
	}
	
	/**
	 * Get seed list from DB
	 */
	@Override
	public List<SeedEntity> listAllSeeds() {
		return baseDao.getAll(SeedEntity.class);
	}

//	@Override
//	public List<SeedEntity> getSeedsListByPage(BsTablePagination bsPage) {
//		return baseDao.getAllByPage(SeedEntity.class, bsPage);
//	}
	
	/**
	 * Count the quantity of seed in DB
	 */
	@Override
	public int getAllSeedSize() {
		return baseDao.getCount(SeedEntity.class);
	}

	/**
	 * Count the quantity of seed by variety name and supplier and import licence
	 */
	@Override
	public int getAllSeedSizeByCondition(Object varietyName, Object supplier,
			Object importLicence) {
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("varietyName", varietyName);
		map.put("supplier", supplier);
		map.put("importLicence", importLicence);
		return baseDao.getCountByCondition(SeedEntity.class, map);
		
	}
	
	/**
	 * Get seed list by pagination function
	 */
	@Override
	public List<SeedEntity> listSeedsByParams(Map<String, Object> param) {
		String hql = " From SeedEntity order by id desc";
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}

//	@Override
//	public List<SeedEntity> getSeedsByPurchaseDay(String purchaseDay,Integer start,Integer maxResult) {
//		String hql = " From SeedEntity where purchase_day = '" + purchaseDay +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Get seed list by import name id
	 * server side pagination function is being used
	 */
	@Override
	public List<SeedEntity> listSeedsByImportNameId(String importNameId, Integer start,
			Integer maxResult) {
		String hql = " From SeedEntity where import_name_id = " + Integer.parseInt(importNameId);
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get seed list by import name id and purchase day server side pagination
	 * function is being used
	 */
	@Override
	public List<SeedEntity> listSeedsByImportNameIdAndPurchaseDay(
			String importNameId, String purchaseDay, Integer start,
			Integer maxResult) {
		String hql = " From SeedEntity where purchase_day = '" + purchaseDay
				+ "' and import_name_id = " + Integer.parseInt(importNameId);
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get seed list by import number
	 * server side pagination function is being used
	 */
	@Override
	public List<SeedEntity> listSeedsByImportNumber(String importNumber, Integer start,
			Integer maxResult) {
		String hql = " From SeedEntity where importNumber like '%" + importNumber.trim() +"%' order by id desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

//	@Override
//	public List<SeedEntity> getSeedsBySpeciesIdAndPurchaseDay(String speciesId,
//			String purchaseDay, Integer start, Integer maxResult) {
//		String hql = " From SeedEntity where purchase_day = '" + purchaseDay +"' and species_id = " + Integer.parseInt(speciesId);
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<SeedEntity> getSeedBySpeciesIdAndImportNameId(String speciesId,
//			String importNameId, Integer start, Integer maxResult) {
//		String hql = " From SeedEntity where species_id = " + Integer.parseInt(speciesId) +" and import_name_id = " + Integer.parseInt(importNameId);
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<SeedEntity> getSeedByAllSearches(String speciesId,
//			String conmercialName, String purchaseDay, Integer start, Integer maxResult) {
//		String hql = " From SeedEntity where purchase_day = '" + purchaseDay
//				+ "' and species_id = " + Integer.parseInt(speciesId)
//				+ " and conmercialName like '%" + conmercialName.trim() +"%'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Delete seed information from DB
	 */
	@Override
	public void deleteSeed(SeedEntity seed) {
		
		baseDao.delete(seed);
	}
	
	/**
	 * Get seed name list from DB
	 */
	@Override
	public List<String> listSeedName() {
		List<String> seedList = baseDao.getFieldByTableName("seed", "name");
		return seedList;
	}
	
	/**
	 * Delete seed by id
	 */
	@Override
	public void deleteSeedById(Integer id) {
		
		baseDao.delete(SeedEntity.class, id);
		
	}
	
	/**
	 * Get seed list by commercial name
	 * server side pagination function is being user
	 */
	@Override
	public List<SeedEntity> listSeedsBySpeciesName(String speciesName,
			Integer start, Integer maxResult) {
		
		String hql = " From SeedEntity where species.speciesNameCn like '%" + speciesName.trim() +"%' order by id desc";
		
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

//	@Override
//	public List<SeedEntity> getSeedsByConmercialNameAndPurchaseDay(
//			String conmercialName, String purchaseDay, Integer start,
//			Integer maxResult) {
//		String hql = " From SeedEntity where conmercialName like '%" + conmercialName.trim() +"%' and purchase_day = '" + purchaseDay +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Get seed list by commercial name and import number
	 * server side pagination function is being user
	 */
	@Override
	public List<SeedEntity> listSeedByImportNumberAndSpeciesName(
			String importNumber, String speciesName, Integer start,
			Integer maxResult) {
		String hql = " From SeedEntity where species.speciesNameCn like '%"
				+ speciesName.trim() + "%' and importNumber like '%"
				+ importNumber.trim() + "%' order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

	/**
	 * Get seed list by species id
	 */
	@Override
	public List<SeedEntity> listSeedsBySpeciesId(Integer speciesId) {
		String hql = "  From SeedEntity where species_id = " + speciesId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get seed list by species id
	 */
	@Override
	public List<SeedEntity> listSeedsByIDs(String ids) {
		String hql = "  From SeedEntity where id in(" + ids+")";
		return baseDao.execute(hql);
	}
	
	/**
	 * Get commercial name from seed table
	 * if some seeds' commercial name,then just count one
	 */
	@Override
	public List<String> listUniqueConmercialNames() {
		String hql = " select DISTINCT conmercialName From SeedEntity";
		return baseDao.execute(hql);
	}
	
	/**
	 * Get species list from seed table
	 */
	@Override
	public List<SpeciesEntity> listSpeciesFromSeed() {
		String sql = "select * from species where id in (select species_id from seed)";
		return baseDao.executeSQL(sql, SpeciesEntity.class);
	}
	
	/**
	 * Get commercial name list form seed tabl by species id
	 */
	@Override
	public List<String> listConmercialNameBySpecies(Integer speciesId) {
		String hql = "select conmercialName from SeedEntity where species.id = " + speciesId;
		return baseDao.execute(hql);
	}
	
	@Override
	public SeedEntity getLastSeedLotNumber(String code) {
		String hql = " From SeedEntity where lotNumber like '" + code +"%' ORDER BY lotNumber desc";
		List<SeedEntity> seeds= baseDao.execute(hql);
		if(seeds!=null&&!seeds.isEmpty()){
			return seeds.get(0);
		}else{
			return null;
		}
	}

}
