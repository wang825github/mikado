package com.shinetech.dalian.mikado.service;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;

public interface SeedService {
	/**
	 * Get seed by id
	 * @param id
	 * @return SeedEntity
	 */
	SeedEntity getSeedById(Integer id);

	void saveSeed(SeedEntity seed);
	
	/**
	 * Get all seeds from DB
	 * @return
	 */
	List<SeedEntity> getAllSeeds();

//	List<SeedEntity> getSeedsListByPage(BsTablePagination bsPage);
	
	void deleteSeed(SeedEntity seed);
	
	void deleteSeedById(Integer id);
	/**
	 * Count quantity of seed 
	 * @return seed summary
	 */
	int getAllSeedSize();
	
	/**
	 * Count quantity of seed by condition
	 * @param variety
	 * @param supplier
	 * @param importLicence
	 * @return seed summary
	 */
	int getAllSeedSizeByCondition(Object variety, Object supplier,
			Object importLicence);
	
	/**
	 * Get seed from DB
	 * server side pagination function is being used
	 * @param params
	 * @return
	 */
	Map<String, Object> getSeedByPagination(Map<String, Object> params);
	
	/**
	 * get all seeds' name from DB
	 */
	List<String> getSeedName();
	
	/**
	 * Check if seed is associated by other table
	 * @param id
	 * @return true if being associated,else return false
	 */
	boolean checkSeedBeAssociated(Integer id);
	
	/**
	 * Get commercial name from seed table
	 * @return commercial name string list
	 */
	List<String> getConmercialNamesFromSeeds();
	
	/**
	 * Get species from seed
	 * @return SpeciesEntity list
	 */
	List<SpeciesEntity> getSpeciesFromSeed();
	
	/**
	 * Get Commercial name by species id
	 * @param speciesId commercial name string list
	 * @return
	 */
	List<String> getConmercialNameBySpecies(Integer speciesId);

	List<SeedEntity> getSeedList();

	void updateSurplusWeight(SeedEntity seed);
	
	String getLotNumber(String code);
}
