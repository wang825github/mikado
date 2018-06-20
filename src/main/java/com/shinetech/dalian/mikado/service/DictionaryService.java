package com.shinetech.dalian.mikado.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;















import org.apache.commons.lang.StringUtils;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.CustomerDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.DictionaryDao;
import com.shinetech.dalian.mikado.dao.SeedDao;
import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
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
 *
 */
@Service
public class DictionaryService {
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private SeedDao seedDao;
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * Get species list
	 * @return SpeciesEntity list
	 */
	public List<SpeciesEntity> getSpeciesList(){
		return dictionaryDao.listAllSpecies();
	}
	
	/**
	 * Get import name list by species id
	 * @param speciesId
	 * @return ImportNameEntity list
	 */
	public List<ImportNameEntity> getImportNameBySpeciesId(Integer speciesId){
		return dictionaryDao.listImportNameBySpeciesId(speciesId);
	}
	
	/**
	 * Get import name by id
	 * @param id
	 * @return ImportNameEntity
	 */
	public ImportNameEntity getImportNameById(Integer id){
		return dictionaryDao.getImportNameById(id);
	}
	
	/**
	 * Get importer by id
	 * @param id
	 * @return ImporterEntity
	 */
	public ImporterEntity getImporterById(Integer id){
		return dictionaryDao.getImporterById(id);
	}
	
	/**
	 * Get supplier by id
	 * @param id
	 * @return SupplierEntity
	 */
	public SupplierEntity getSupplierById(Integer id){
		return dictionaryDao.getSupplierById(id);
	}
	
	/**
	 * Get import name list
	 * @return ImportNameEntity list
	 */
	public List<ImportNameEntity> getImportNameList(){
		return dictionaryDao.listAllImportName();
	}
	
	/**
	 * Get importer list
	 * @return ImporterEntity
	 */
	public List<ImporterEntity> getImporterList(){
		return dictionaryDao.listAllImporter();
	}
	
	/**
	 * Get supplier list
	 * @return SupplierEntity list
	 */
	public List<SupplierEntity> getSupplierList(){
		return dictionaryDao.listAllSupplier();
	}
	
	/**
	 * Get seed list
	 * @return SeedEntity list
	 */
	public List<SeedEntity> getSeedList(){
		return dictionaryDao.listSeeds();
	}
	
	/**
	 * Get packing unit list
	 * @return PackingUnitEntity list
	 */
	public List<PackingUnitEntity> getPackingUnitList(){
		return dictionaryDao.listPackingUnits();
	}
	
	/**
	 * Get species from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with species list and paginations parameters
	 */
	public Map<String, Object> getSpeciesByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SpeciesEntity> rows = new ArrayList<SpeciesEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listSpeciesByParam(null).size();
		rows = dictionaryDao.listSpeciesByParam(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Get packing unit from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with packing unit list and paginations parameters
	 */
	public Map<String, Object> getPackingUnitByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<PackingUnitEntity> rows = new ArrayList<PackingUnitEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listPackingUnitByParams(null).size();
		rows = dictionaryDao.listPackingUnitByParams(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
		
	}
	
	/**
	 * Get import name from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with import name list and paginations parameters
	 */
	public Map<String, Object> getImportNameByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImportNameEntity> rows = new ArrayList<ImportNameEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listImportNamesByParams(null).size();
		rows = dictionaryDao.listImportNamesByParams(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Get importer from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with importer list and paginations parameters
	 */
	public Map<String, Object> getImporterByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImporterEntity> rows = new ArrayList<ImporterEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listImportersByParams(null).size();
		rows = dictionaryDao.listImportersByParams(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Get supplier from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with supplier list and paginations parameters
	 */
	public Map<String, Object> getSupplierByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SupplierEntity> rows = new ArrayList<SupplierEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listSuppliersByParams(null).size();
		rows = dictionaryDao.listSuppliersByParams(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Get sales manager from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with sales manager list and paginations parameters
	 */
	public Map<String, Object> getSaleManagerByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SaleManagerEntity> rows = new ArrayList<SaleManagerEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listSaleManagersByParams(null).size();
		rows = dictionaryDao.listSaleManagersByParams(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Get logistics company from DB by search conditions
	 * server side pagination function is being user
	 * @param param
	 * @return map with logistics company list and paginations parameters
	 */
	public Map<String, Object> getLogisticsCompanyByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<LogisticCompanyEntity> rows = new ArrayList<LogisticCompanyEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listLogisticCompaniesByParam(null).size();
		rows = dictionaryDao.listLogisticCompaniesByParam(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	public Map<String, Object> getVarietyByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<VarietyEntity> rows = new ArrayList<VarietyEntity>();
		
		int total = 0;
		
		total = dictionaryDao.listVarietyByParam(null).size();
		rows = dictionaryDao.listVarietyByParam(param);
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Insert species into DB
	 * @param species
	 */
	public void saveSpecies(SpeciesEntity species){
		dictionaryDao.saveSpecies(species);
	}
	
	/**
	 * Insert PackingUnit into DB
	 * @param packingUnit
	 */
	public void savePackingUnit(PackingUnitEntity packingUnit){
		dictionaryDao.savePackingUnit(packingUnit);
	}
	
	/**
	 * Insert ImportName into DB
	 * @param importName
	 */
	public void saveImportName(ImportNameEntity importName){
		
		dictionaryDao.saveImportName(importName);
	}
	
	/**
	 * Insert Importer into DB
	 * @param importer
	 */
	public void saveImporter(ImporterEntity importer){
		dictionaryDao.saveImporter(importer);
	}
	
	/**
	 * Insert Supplier into DB
	 * @param supplier
	 */
	public void saveSupplier(SupplierEntity supplier){
		dictionaryDao.saveSupplier(supplier);
	}
	
	/**
	 * Insert SaleManager into DB
	 * @param saleManager
	 */
	public void saveSaleManager(SaleManagerEntity saleManager){
		dictionaryDao.saveSaleManager(saleManager);
	}
	
	/**
	 * Insert LogisticsCompany into DB
	 * @param logisticsCompany
	 */
	public void saveLogisticsCompany(LogisticCompanyEntity logisticsCompany){
		dictionaryDao.saveLogisticsCompany(logisticsCompany);
	}
	
	/**
	 * Delete species form DB
	 * @param variety
	 */
	public void deleteSpecies(SpeciesEntity variety){
		dictionaryDao.deleteSpecies(variety);
	}
	
	public void deletePackingUnit(PackingUnitEntity packages){
		dictionaryDao.deletePackingUnit(packages);
	}
	
	public void deleteImportName(ImportNameEntity importName){
		dictionaryDao.deleteImportName(importName);
	}
	
	public void deleteImporter(ImporterEntity importer){
		dictionaryDao.deleteImporter(importer);
	}
	
	public void deleteSupplier(SupplierEntity supplier){
		dictionaryDao.deleteSupplier(supplier);
	}
	
	public void deleteSaleManager(SaleManagerEntity saleManager){
		dictionaryDao.deleteSaleManager(saleManager);
	}
	
	public void deleteLogisticCompany(LogisticCompanyEntity logisticCompany){
		dictionaryDao.deleteLogisticCompany(logisticCompany);
	}
	
	/**
	 * Get logistics company list
	 * @return LogisticCompanyEntity list
	 */
	public List<LogisticCompanyEntity> getLogisticsCompanyList(){
		return dictionaryDao.listLogisticsCompanies();
	}
	
	/**
	 * Get customer list
	 * @return CustomerEntity list
	 */
	public List<CustomerEntity> listCustomers(){
		return dictionaryDao.listCustomers();
	}
	
	/**
	 * Get sale manager list
	 * @return SaleManagerEntity list
	 */
	public List<SaleManagerEntity> getSaleManagerList(){
		return dictionaryDao.listSaleManagers();
	}
	
	/**
	 * Get species by id
	 * @param id
	 * @return SpeciesEntity
	 */
	public SpeciesEntity getSpeciesById(Integer id) {
		
		return dictionaryDao.getSpeciesById(id);
	}
	
	/**
	 * Check if species is being associated by other tables
	 * @param species
	 * @return
	 */
	public boolean checkSpeciesBeAssociated(SpeciesEntity species){
		List<VarietyEntity> varietyList =dictionaryDao.listVarietiesBySpecies(species.getId());
		if(varietyList != null && varietyList.size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * check if packing unit is being associated by other tables
	 * @param packingUnit
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkPackingUnitBeAssociated(PackingUnitEntity packingUnit){
		List<ProductionsEntity> productionList = dictionaryDao.listProductionsByPackingUnitId(packingUnit.getId());
		if(productionList != null && productionList.size() >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * check if ImportName is being associated by other tables
	 * @param importName
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkImportNameBeAssociated(ImportNameEntity importName) {
		List<SeedEntity> seedList = seedDao.listSeedsByImportNameId(importName.getId().toString(), null, null);
		if(seedList != null && seedList.size() > 0){
			return true;
		}else {
			return false;
		}
		
	}
	
	/**
	 * check if importer is being associated by other tables
	 * @param entity
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkImporterBeAssociated(ImporterEntity importer) {
		List<DataManageEntity> dataManageList = dataManageDao.listDataManageByImporterId(importer.getId());
		if(dataManageList != null && dataManageList.size() > 0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * check if Supplier is being associated by other tables
	 * @param supplier
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkSupplierBeAssociated(SupplierEntity supplier) {
		List<DataManageEntity> dataManageList =  dataManageDao.listDataManageBySupplierId(supplier.getId());
		if(dataManageList != null && dataManageList.size() > 0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * check if saleManager is being associated by other tables
	 * @param saleManager
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkSaleBeAssociated(SaleManagerEntity saleManager) {
		List<CustomerEntity> customerList = customerDao.listCustomerBySaleId(saleManager.getId());
		if(customerList != null && customerList.size() > 0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * check if logisticsCompany is being associated by other tables
	 * @param logisticsCompany
	 * @return true is associated by other tables, else return false
	 */
	public boolean checkLogisticBeAssociated(LogisticCompanyEntity logisticsCompany) {
		List<DataManageEntity> dataManageList = dataManageDao.listDataManageByLogisticId(logisticsCompany.getId());
		if(dataManageList != null && dataManageList.size() > 0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Get all area from DB
	 * @return area list
	 */
	public List<AreaEntity> getAreaList() {
		
		return dictionaryDao.listAreas();
	}
	
	/**
	 * Get province list by area id
	 * @param areaId
	 * @return ProvinceEntity list
	 */
	public List<ProvinceEntity> getProvinceByAreaId(Integer areaId) {
		
		return dictionaryDao.listProvinceByAreaId(areaId);
	}
	
	/**
	 * Get city lit by province id
	 * @param provinceId
	 * @return CityEntity list
	 */
	public List<CityEntity> getCityByProvinceId(Integer provinceId) {
		
		return dictionaryDao.listCityByProvinceId(provinceId);
	}
	
	/**
	 * Get sale manager by area id
	 * @param areaId
	 * @return SaleManagerEntity list
	 */
	public List<SaleManagerEntity> getSaleManagerByAreaId(Integer areaId) {
		
		return dictionaryDao.listSaleManagerByAreaId(areaId);
	}
	
	/**
	 * Get packing unit by id
	 * @param packingUnitId
	 * @return PackingUnitEntity
	 */
	public PackingUnitEntity getPackingUnitById(Integer packingUnitId) {
		return dictionaryDao.getPackingUnitById(packingUnitId);
	}
	
	/**
	 * Get city list from DB
	 * @return CityEntity list
	 */
	public List<CityEntity> getCityLists() {
		
		return dictionaryDao.listCities();
	}
	
	/**
	 * Check species name exists or not in DB
	 * @param species
	 * @return true is exists,else return false
	 */
	public boolean checkSpeciesNameExists(SpeciesEntity species) {
		return dictionaryDao.checkSpeciesNameExists(species);
		
	}
	
	/**
	 * Check packingUnit specifications exists or not in DB
	 * @param packingUnit
	 * @return true is exists,else return false
	 */
	public boolean checkPackingUnitExists(PackingUnitEntity packingUnit) {
		
		return dictionaryDao.checkPackingUnitExists(packingUnit);
		
	}
	
	/**
	 * Check importName exists or not in DB
	 * @param importName
	 * @return true is exists,else return false
	 */
	public boolean checkImportNameExists(ImportNameEntity importName) {
		return dictionaryDao.checkImportNameExists(importName);
		
	}
	
	/**
	 * Check importer name exists or not in DB
	 * @param importer
	 * @return true is exists,else return false
	 */
	public boolean checkImporterExists(ImporterEntity importer) {
		return dictionaryDao.checkImporterExists(importer);
		
	}
	
	/**
	 * Check supplier name exists or not in DB
	 * @param supplier
	 * @return true is exists,else return false
	 */
	public boolean checkSupplierExists(SupplierEntity supplier) {
		
		return dictionaryDao.checkSupplierExists(supplier);
		
	}
	
	/**
	 * Check saleManager name exists or not in DB
	 * @param saleManager
	 * @return true is exists,else return false
	 */
	public boolean checkSaleManagerExists(SaleManagerEntity saleManager) {
		
		return dictionaryDao.checkSaleManagerExists(saleManager);
		
	}
	
	/**
	 * Check logisticsCompany name exists or not in DB
	 * @param logisticsCompany
	 * @return true is exists,else return false
	 */
	public boolean checkLogisticsExists(LogisticCompanyEntity logisticsCompany) {
		
		return dictionaryDao.checkLogisticsExists(logisticsCompany);
	}

	/**
	 * @param variety
	 */
	public void saveVariety(VarietyEntity variety) {
		baseDao.save(variety);
		
	}
	/**
	 * @param variety
	 * @param id
	 */
	public void delVariety(VarietyEntity variety,int id) {
		baseDao.delete(VarietyEntity.class, id);
	}
	public boolean checkVariety(VarietyEntity variety) {
		String hql = null;
//		if(variety.getId() == null){
//			hql = " From VarietyEntity where varietyName = '" + StringUtils.trim(variety.getVarietyName())+"'";
//		}else{
//			hql = " From VarietyEntity where varietyName = '" + StringUtils.trim(variety.getVarietyName()) +"' and id <>" + variety.getId();
//		}
		
		if(variety.getId() == null){
			hql = " From VarietyEntity where varietyCode = '" + StringUtils.trim(variety.getVarietyCode())+"'";
		}else{
			hql = " From VarietyEntity where varietyCode = '" + StringUtils.trim(variety.getVarietyCode()) +"' and id <>" + variety.getId();
		}
		List<VarietyEntity> lvarietylist = baseDao.execute(hql);
		if(lvarietylist == null || lvarietylist.size() == 0){
			return false;
		}
		return true;
	}



	public List<String> getConmercialNameFromVarity() {
		// TODO Auto-generated method stub
		return dictionaryDao.listConmercialNameFromVarity();
	}

	public List<VarietyEntity> getVarietyList() {
		// TODO Auto-generated method stub
		return dictionaryDao.listVarieties();
	}
 

	public boolean checkGroups(GroupsEntity groups) {
		String hql = null;
		if(groups.getId() == null){
			hql = " From GroupsEntity where nameCn = '" + StringUtils.trim(groups.getNameCn())+"'";
		}else{
			hql = " From GroupsEntity where nameCn = '" + StringUtils.trim(groups.getNameCn()) +"' and id <>" + groups.getId();
		}
		List<VarietyEntity> lvarietylist = baseDao.execute(hql);
		if(lvarietylist == null || lvarietylist.size() == 0){
			return false;
		}
		return true;
	}

	public void saveGroups(GroupsEntity groups) {
		// TODO Auto-generated method stub
		
	}

	public void delGroups(GroupsEntity groups, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public VarietyEntity getVarietyById(Integer verietyID) {
		
		return dictionaryDao.getVarietyById(verietyID);
	}

	public List<VarietyEntity> getVarietyListBySpecies(SpeciesEntity species) {
		
		return dictionaryDao.listVarietiesBySpecies(species.getId());
	}
	
}
