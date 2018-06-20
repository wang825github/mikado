package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.GroupsEntity;
import com.shinetech.dalian.mikado.entity.ImportNameEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.ProvinceEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;

/**
 * 
 * @author abc
 * @param <E>
 *
 */
public interface DictionaryDao {
	/**
	 * Get import name by id
	 * @param id
	 * @return an import name information
	 */
	ImportNameEntity getImportNameById(Integer id);
	
	/**
	 * Get importer by id
	 * @param id
	 * @return an importer information
	 */
	ImporterEntity getImporterById(Integer id);
	
	/**
	 * Get supplier by id
	 * @param id
	 * @return a supplier information
	 */
	SupplierEntity getSupplierById(Integer id);
	
	/**
	 * Get packing unit by id
	 * @param id
	 * @return a packing unit information
	 */
	PackingUnitEntity getPackingUnitById(Integer id);
	
	/**
	 * Get logistics company by id
	 * @param id
	 * @return a logistics company information
	 */
	LogisticCompanyEntity getLogisticCompanyById(Integer id);
	
	/**
	 * Get sales manager by id
	 * @param id
	 * @return a sales manager information
	 */
	SaleManagerEntity getSaleManagerByid(Integer id);
	
	/**
	 * Get species by id
	 * @param id
	 * @return a species information
	 */
	SpeciesEntity getSpeciesById(Integer id);
	
	/**
	 * Insert a species information into DB
	 * @param species
	 */
	void saveSpecies(SpeciesEntity species);
	
	/**
	 * Insert a packing unit information into DB
	 * @param packingUnit
	 */
	void savePackingUnit(PackingUnitEntity packingUnit);
	
	/**
	 * Insert an import name information into DB
	 * @param importName
	 */
	void saveImportName(ImportNameEntity importName);
	
	/**
	 * Insert an importer information into DB
	 * @param importer
	 */
	void saveImporter(ImporterEntity importer);
	/**
	 * Insert a supplier information into DB
	 * @param supplier
	 */
	void saveSupplier(SupplierEntity supplier);
	
	/**
	 * Insert a sales manager information into DB
	 * @param saleManager
	 */
	void saveSaleManager(SaleManagerEntity saleManager);
	
	/**
	 * Insert a logistics company information into DB
	 * @param logisticsCompany
	 */
	void saveLogisticsCompany(LogisticCompanyEntity logisticsCompany);
	
	/**
	 * Delete a species from DB
	 * @param variety
	 */
	void deleteSpecies(SpeciesEntity species);
	
	/**
	 * Delete a packing unit from DB
	 * @param packages
	 */
	void deletePackingUnit(PackingUnitEntity packingUnit);
	
	/**
	 * Delete an import name from DB
	 * @param importName
	 */
	void deleteImportName(ImportNameEntity importName);
	
	/**
	 * Delete an importer from DB
	 * @param importer
	 */
	void deleteImporter(ImporterEntity importer);
	
	/**
	 * Delete a supplier from DB
	 * @param supplier
	 */
	void deleteSupplier(SupplierEntity supplier);
	
	/**
	 * Delete a sale manager from DB
	 * @param saleManager
	 */
	void deleteSaleManager(SaleManagerEntity saleManager);
	
	/**
	 * Delete a logistics company from DB
	 * @param logisticCompany
	 */
	void deleteLogisticCompany(LogisticCompanyEntity logisticCompany);
	
	/**
	 * Get all Species from DB
	 * @return species list
	 */
	List<SpeciesEntity> listAllSpecies();
	
	/**
	 * Get import names by species id
	 * @param speciesId
	 * @return importer name list
	 */
	List<ImportNameEntity> listImportNameBySpeciesId(Integer speciesId);
	
	/**
	 * Get all import names from DB
	 * @return importer name list
	 */
	List<ImportNameEntity> listAllImportName();
	
	/**
	 * Get all importers from DB
	 * @return importer list
	 */
	List<ImporterEntity> listAllImporter();
	
	/**
	 * Get all suppliers from DB
	 * @return supplier list
	 */
	List<SupplierEntity> listAllSupplier();
	
	/**
	 * Get all seeds from DB
	 * @return seed list
	 */
	List<SeedEntity> listSeeds();
	
	/**
	 * Get all packing units from DB
	 * @return packing unit list
	 */
	List<PackingUnitEntity> listPackingUnits();
	
	/**
	 * Get all species from DB
	 * When return to front end,display species by pagination function
	 * @param param
	 * @return species list
	 */
	List<SpeciesEntity> listSpeciesByParam(Map<String, Object> param);
	
	/**
	 * Get all packing units from DB
	 * When return to front end,display packing units by pagination function
	 * @param param
	 * @return packing unit list
	 */
	List<PackingUnitEntity> listPackingUnitByParams(Map<String, Object> param);
	
	/**
	 * Get all import names from DB
	 * When return to front end,display import names by pagination function
	 * @param param
	 * @return import name list
	 */
	List<ImportNameEntity> listImportNamesByParams(Map<String, Object> param);
	
	/**
	 * Get all importers from DB
	 * When return to front end,display importers by pagination function
	 * @param param
	 * @return importer list
	 */
	List<ImporterEntity> listImportersByParams(Map<String, Object> param);
	
	/**
	 * Get all suppliers from DB
	 * When return to front end,display suppliers by pagination function
	 * @param param
	 * @return supplier list
	 */
	List<SupplierEntity> listSuppliersByParams(Map<String, Object> param);
	
	/**
	 * Get all sales managers from DB
	 * When return to front end,display sales managers by pagination function
	 * @param param
	 * @return sales manager list
	 */
	List<SaleManagerEntity> listSaleManagersByParams(Map<String, Object> param);
	
	/**
	 * Get all logistics companies from DB
	 * When return to front end,display logistics companies by pagination function
	 * @param param
	 * @return logistics company list
	 */
	List<LogisticCompanyEntity> listLogisticCompaniesByParam(Map<String, Object> param);
	
	/**
	 * Get all logistics companies from DB
	 * @return logistics company list
	 */
	List<LogisticCompanyEntity> listLogisticsCompanies();
	
	/**
	 * Get all customers from DB
	 * @return customers list
	 */
	List<CustomerEntity> listCustomers();
	
	/**
	 * Get all sale managers from DB
	 * @return sales managers list
	 */
	List<SaleManagerEntity> listSaleManagers();
	
	/**
	 * Get productions by packing unit id
	 * @param packingUnitId
	 * @return production list
	 */
	List<ProductionsEntity> listProductionsByPackingUnitId(Integer packingUnitId);
	
	/**
	 * Get all areas from DB
	 * @return area list
	 */
	List<AreaEntity> listAreas();
	
	/**
	 * Get provinces by area id
	 * @param areaId
	 * @return province list
	 */
	List<ProvinceEntity> listProvinceByAreaId(Integer areaId);
	
	/**
	 * Get city by province id
	 * @param provinceId
	 * @return city list
	 */
	List<CityEntity> listCityByProvinceId(Integer provinceId);
	
	/**
	 * Get sales managers by area id
	 * @param areaId
	 * @return sales manager list
	 */
	List<SaleManagerEntity> listSaleManagerByAreaId(Integer areaId);
	
	/**
	 * Get all cities from DB
	 * @return city list
	 */
	List<CityEntity> listCities();

	boolean checkSpeciesNameExists(SpeciesEntity species);

	boolean checkPackingUnitExists(PackingUnitEntity packingUnit);

	boolean checkImportNameExists(ImportNameEntity importName);

	boolean checkImporterExists(ImporterEntity importer);

	boolean checkSupplierExists(SupplierEntity supplier);

	boolean checkSaleManagerExists(SaleManagerEntity saleManager);

	boolean checkLogisticsExists(LogisticCompanyEntity logisticsCompany);

	List<VarietyEntity> listVarietyByParam(Map<String, Object> param);

	List<String> listConmercialNameFromVarity();

	List<VarietyEntity> listVarieties();
	
	List<VarietyEntity> listVarietiesBySpecies(int id);

	VarietyEntity getVarietyById(Integer id);


 
}
