package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;

/**
 * 
 * @author abc
 *
 */
public interface SeedDao {
	
	/**
	 * Get seed by id
	 * @param id
	 * @return seed information
	 */
	SeedEntity getSeedById(Integer id);
	
	void saveSeed(SeedEntity seed);
	
	void deleteSeed(SeedEntity seed);
	
	void deleteSeedById(Integer id);
	
	/**
	 * count the quantity of all seeds
	 * @return the count of seeds
	 */
	int getAllSeedSize();
	
	/**
	 * count the quantity of seed by variety,supplier and import license
	 * @param variety
	 * @param supplier
	 * @param importLicence
	 * @return the count of seeds
	 */
	int getAllSeedSizeByCondition(Object variety,Object supplier,Object importLicence);
	
	/**
	 * Get all seed from DB
	 * @return seed list
	 */
	List<SeedEntity> listAllSeeds();
	
	/**
	 * Get all seed from DB
	 * When return to front end,display seed by pagination function
	 * @param param
	 * @return seed list
	 */
	List<SeedEntity> listSeedsByParams(Map<String, Object> param);
	
	/**
	 * Get seeds from DB by import name
	 * When return to front end,display seed by pagination function
	 * @param importNameId
	 * @param start
	 * @param maxResult
	 * @return seed list
	 */
	List<SeedEntity> listSeedsByImportNameId(String importNameId,Integer start,Integer maxResult);
	
	/**
	 * Get seeds by import name and purchase day
	 * When return to front end,display seed by pagination function
	 * @param importName
	 * @param purchaseDay
	 * @param start
	 * @param maxResult
	 * @return seed list
	 */
	List<SeedEntity> listSeedsByImportNameIdAndPurchaseDay(String importName,String purchaseDay,Integer start,Integer maxResult);
	
	/**
	 * Get seeds by import number
	 * When return to front end,display seed by pagination function
	 * @param speciesId
	 * @param start
	 * @param maxResult
	 * @return seed list
	 */
	List<SeedEntity> listSeedsByImportNumber(String speciesId,Integer start,Integer maxResult);
	
	/**
	 * Get all seeds name from DB
	 * @return seed name string list
	 */
	List<String> listSeedName();
	
	/**
	 * Get seeds by commercial name 
	 * When return to front end,display seed by pagination function
	 * @param conmercialName
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<SeedEntity> listSeedsBySpeciesName(String conmercialName,Integer start,Integer maxResult);
	
	/**
	 * Get seed by import number and commercial name
	 * When return to front end,display seed by pagination function
	 * @param importNumber
	 * @param conmercialName
	 * @param start
	 * @param maxResult
	 * @return seed list
	 */
	List<SeedEntity> listSeedByImportNumberAndSpeciesName(String importNumber,String conmercialName,Integer start,Integer maxResult);
	
	/**
	 * Get seed by species id
	 * 
	 * @param speciesId
	 * @return seed list
	 */
	List<SeedEntity> listSeedsBySpeciesId(Integer speciesId);
	
	/**
	 * Get commercial names from DB,if some names are the same, then just return one of them
	 * @return
	 */
	List<String> listUniqueConmercialNames();
	
	/**
	 * Get species from seed table
	 * @return species list
	 */
	List<SpeciesEntity> listSpeciesFromSeed();
	
	/**
	 * Get commercial names by species
	 * @param speciesId
	 * @return commercial names string list
	 */
	List<String> listConmercialNameBySpecies(Integer speciesId);

	SeedEntity getLastSeedLotNumber(String code);

	List<SeedEntity> listSeedsByIDs(String ids);

//	List<SeedEntity> getSeedsListByPage(BsTablePagination bsPage);
	
//	List<SeedEntity> listSeedsByPurchaseDay(String purchseDay,Integer start,Integer maxResult);
	
//	List<SeedEntity> listSeedsBySpeciesIdAndPurchaseDay(String speciesId,String purchaseDay,Integer start,Integer maxResult);
	
//	List<SeedEntity> listSeedBySpeciesIdAndImportNameId(String speciesId,String importNameId,Integer start,Integer maxResult);
	
//	List<SeedEntity> listSeedByAllSearches(String speciesId,String conmercialName,String purchaseDay,Integer start,Integer maxResult);
	
//	List<SeedEntity> listSeedsByConmercialNameAndPurchaseDay(String conmercialName, String purchaseDay,Integer start,Integer maxResult);
}
