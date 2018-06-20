package com.shinetech.dalian.mikado.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.PublicDao;
import com.shinetech.dalian.mikado.dao.DictionaryDao;
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
@Service
@Repository
public class DictionaryDaoImpl  implements DictionaryDao {
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private PublicDao publicDao;
	/**
	 * Get species list by pagination function of server side
	 */
	@Override
	public List<SpeciesEntity> listSpeciesByParam(Map<String, Object> param) {
		String hql = " From SpeciesEntity order by cropCode asc , no asc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Get packing unit list by pagination function of server side
	 */
	@Override
	public List<PackingUnitEntity> listPackingUnitByParams(Map<String, Object> param) {
		String hql = " From PackingUnitEntity order by id desc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Insert a species into DB
	 */
	@Override
	public void saveSpecies(SpeciesEntity species) {
		
		baseDao.save(species);
		
	}
	
	/**
	 * Insert a packing unit into DB
	 */
	@Override
	public void savePackingUnit(PackingUnitEntity packingUnit) {
		
		baseDao.save(packingUnit);
		
	}
	
	/**
	 * Delete species from DB
	 */
	@Override
	public void deleteSpecies(SpeciesEntity species) {
		
		baseDao.delete(species);
		
	}
	
	/**
	 * Delete packing unit from DB
	 */
	@Override
	public void deletePackingUnit(PackingUnitEntity packingUnit) {
		
		baseDao.delete(packingUnit);
		
	}
	
	/**
	 * Get import names list by pagination function of server side
	 */
	@Override
	public List<ImportNameEntity> listImportNamesByParams(Map<String, Object> param) {
		
		String hql = " From ImportNameEntity order by id desc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Get importer list by pagination function of server side
	 */
	@Override
	public List<ImporterEntity> listImportersByParams(Map<String, Object> param) {
		String hql = " From ImporterEntity order by importerCode asc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Get supplier list by pagination function of server side
	 */
	@Override
	public List<SupplierEntity> listSuppliersByParams(Map<String, Object> param) {
		String hql = " From SupplierEntity order by supplierCode asc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Get sale manager list by pagination function of server side
	 */
	@Override
	public List<SaleManagerEntity> listSaleManagersByParams(Map<String, Object> param) {
		String hql = " From SaleManagerEntity order by SMCode  asc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Get logistic company list by pagination function of server side
	 */
	@Override
	public List<LogisticCompanyEntity> listLogisticCompaniesByParam(Map<String, Object> param) {
		String hql = " From LogisticCompanyEntity order by code asc";
		
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/* (non-Javadoc)
	 * Get VarietyEntity list by pagination function of server side
	 */
	@Override
	public List<VarietyEntity> listVarietyByParam(Map<String, Object> param) {
		List<VarietyEntity> vlist = new ArrayList<VarietyEntity>();
		String hql = " From VarietyEntity order by varietyCode asc";
		if(param == null){
			vlist =  baseDao.execute(hql);
		}else{
			vlist =  baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
		return vlist;
	}
	
	/**
	 * Insert import name into DB
	 */
	@Override
	public void saveImportName(ImportNameEntity importName) {
		
		baseDao.save(importName);
		
	}
	
	/**
	 * Insert an importer into DB
	 */
	@Override
	public void saveImporter(ImporterEntity importer) {
		
		baseDao.save(importer);
		
	}
	
	/**
	 * Insert a supplier into DB
	 */
	@Override
	public void saveSupplier(SupplierEntity supplier) {
		
		baseDao.save(supplier);
		
	}
	
	/**
	 * Insert a sale manager into DB
	 */
	@Override
	public void saveSaleManager(SaleManagerEntity saleManager) {
		
		baseDao.save(saleManager);
	}
	
	/**
	 * Insert a logistics company into DB
	 */
	@Override
	public void saveLogisticsCompany(LogisticCompanyEntity logisticsCompany) {
		
		baseDao.save(logisticsCompany);
	}
	
	/**
	 * Delete import name from DB
	 */
	@Override
	public void deleteImportName(ImportNameEntity importName) {
		
		baseDao.delete(importName);
	}
	
	/**
	 * Delete importer from DB
	 */
	@Override
	public void deleteImporter(ImporterEntity importer) {
		
		baseDao.delete(importer);
		
	}
	
	/**
	 * Delete supplier from DB
	 */
	@Override
	public void deleteSupplier(SupplierEntity supplier) {
		
		baseDao.delete(supplier);
		
	}
	
	/**
	 * Delete sale manager from DB
	 */
	@Override
	public void deleteSaleManager(SaleManagerEntity saleManager) {
		
		baseDao.delete(saleManager);
	}
	
	/**
	 * Delete logistic company from DB
	 */
	@Override
	public void deleteLogisticCompany(LogisticCompanyEntity logisticCompany) {
		
		baseDao.delete(logisticCompany);
		
	}
	
	/**
	 * Get all species from DB
	 */
	@Override
	public List<SpeciesEntity> listAllSpecies() {
		
		return baseDao.getAll(SpeciesEntity.class);
	}
	
	/**
	 * Get all import names from DB
	 */
	@Override
	public List<ImportNameEntity> listAllImportName() {
		return baseDao.getAll(ImportNameEntity.class);
	}
	
	/**
	 * Get all importers from DB
	 */
	@Override
	public List<ImporterEntity> listAllImporter() {
		return baseDao.getAll(ImporterEntity.class);
	}
	
	/**
	 * Get all suppliers from DB
	 */
	@Override
	public List<SupplierEntity> listAllSupplier() {
		return baseDao.getAll(SupplierEntity.class);
	}

	/**
	 * Get import name by id
	 */
	@Override
	public ImportNameEntity getImportNameById(Integer id) {
		
		return baseDao.get(ImportNameEntity.class, id);
	}
	
	/**
	 * Get importer by id
	 */
	@Override
	public ImporterEntity getImporterById(Integer id) {
		
		return baseDao.get(ImporterEntity.class, id);
	}
	
	/**
	 * Get supplier by id
	 */
	@Override
	public SupplierEntity getSupplierById(Integer id) {
		
		return baseDao.get(SupplierEntity.class, id);
	}
	
	/**
	 * Get all seeds from DB
	 */
	@Override
	public List<SeedEntity> listSeeds() {
		return baseDao.getAll(SeedEntity.class);
	}
	
	/**
	 * Get all packing units from DB
	 */
	@Override
	public List<PackingUnitEntity> listPackingUnits() {
		return baseDao.getAll(PackingUnitEntity.class);
	}
	
	/**
	 * Get packing unit by id
	 */
	@Override
	public PackingUnitEntity getPackingUnitById(Integer id) {
		
		return baseDao.get(PackingUnitEntity.class, id);
	}
	
	/**
	 * Get all logistics companies from DB
	 */
	@Override
	public List<LogisticCompanyEntity> listLogisticsCompanies() {
		String hql = " From LogisticCompanyEntity";
		return baseDao.execute(hql);
	}
	/**
	 * Get all customers from DB
	 */
	@Override
	public List<CustomerEntity> listCustomers() {
		return baseDao.getAll(CustomerEntity.class);
	}
	/**
	 * Get all sale managers from DB
	 */
	@Override
	public List<SaleManagerEntity> listSaleManagers() {
		return baseDao.getAll(SaleManagerEntity.class);
	}
	/**
	 * Get logistics company by id
	 */
	@Override
	public LogisticCompanyEntity getLogisticCompanyById(Integer id) {
		
		return baseDao.get(LogisticCompanyEntity.class, id);
	}
	/**
	 * Get sale manager by id
	 */
	@Override
	public SaleManagerEntity getSaleManagerByid(Integer id) {
		
		return baseDao.get(SaleManagerEntity.class, id);
	}
	/**
	 * Get importer name list by species id
	 */
	@Override
	public List<ImportNameEntity> listImportNameBySpeciesId(Integer speciesId) {
		String hql = " From ImportNameEntity where species_id = " + speciesId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get species by id
	 */
	@Override
	public SpeciesEntity getSpeciesById(Integer id) {
		
		return baseDao.get(SpeciesEntity.class, id);
	}
	
	/**
	 * Get productions list by packing unit id
	 */
	@Override
	public List<ProductionsEntity> listProductionsByPackingUnitId(Integer packingUnitId) {
		String hql = " From ProductionsEntity where packing_unit_id = " + packingUnitId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get areas list from DB
	 */
	@Override
	public List<AreaEntity> listAreas() {
		return baseDao.getAll(AreaEntity.class);
	}
	
	/**
	 * Get province list by area id
	 */
	@Override
	public List<ProvinceEntity> listProvinceByAreaId(Integer areaId) {
		String hql = " From ProvinceEntity where area_id = " + areaId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get city list by province id
	 */
	@Override
	public List<CityEntity> listCityByProvinceId(Integer provinceId) {
		String hql = " From CityEntity where province_id = " + provinceId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get sale manage list by area id
	 */
	@Override
	public List<SaleManagerEntity> listSaleManagerByAreaId(Integer areaId) {
		String hql = " From SaleManagerEntity where area_id = " + areaId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get all cities from DB
	 */
	@Override
	public List<CityEntity> listCities() {
		return baseDao.getAll(CityEntity.class);
	}
	
	/**
	 * Check if species name exists already in DB
	 * the function is being used when adding new species or update old species name
	 */
	@Override
	public boolean checkSpeciesNameExists(SpeciesEntity species) {
		String hql = null;
		if (species.getId() == null) {
				
			hql = " From SpeciesEntity where speciesNameCn = '"
					+ org.apache.commons.lang.StringUtils.trimToEmpty(species.getSpeciesNameCn())+ "' or  speciesNameEn = '"
					+org.apache.commons.lang.StringUtils.trimToEmpty(species.getSpeciesNameEn())+ "'";
		} else {
			hql = " From SpeciesEntity where (speciesNameCn = '"
					+ org.apache.commons.lang.StringUtils.trimToEmpty(species.getSpeciesNameCn()) + "' or  speciesNameEn = '"
					+ org.apache.commons.lang.StringUtils.trimToEmpty(species.getSpeciesNameEn())  + "') and id <> "
					+ species.getId();
		}
		List<SpeciesEntity> specieslist = baseDao.execute(hql);
		if (specieslist == null || specieslist.size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if packing unit specifications already exists or not in DB
	 * the function is being used when adding new packing unit or update old  packing unit specifications
	 */
	@Override
	public boolean checkPackingUnitExists(PackingUnitEntity packingUnit) {
		String hql = null;
		if(packingUnit.getId() == null){
			hql = " From PackingUnitEntity where specificationCode = '" + packingUnit.getSpecificationCode().trim() +"'";
		}else{
			hql = " From PackingUnitEntity where specificationCode = '" + packingUnit.getSpecificationCode().trim() +"' and id <>" + packingUnit.getId();
		}
		List<PackingUnitEntity> packingUnitlist = baseDao.execute(hql);
		if(packingUnitlist == null || packingUnitlist.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if import name exists already or not in DB
	 * the function if being user when adding import name or update old import name
	 */
	@Override
	public boolean checkImportNameExists(ImportNameEntity importName) {
		String hql = null;
		if(importName.getId() == null){
			hql = " From ImportNameEntity where name = '" + importName.getName().trim() +"'";
		}else{
			hql = " From ImportNameEntity where name = '" + importName.getName().trim() +"' and id <>" + importName.getId();
		}
		List<ImportNameEntity> importNamelit = baseDao.execute(hql);
		if(importNamelit == null || importNamelit.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if importer's name exists already or not in DB
	 * the function if being user when adding importer's name or update old import name
	 */
	@Override
	public boolean checkImporterExists(ImporterEntity importer) {
		String hql = null;
		if(importer.getId() == null){
			hql = " From ImporterEntity where name = '" + importer.getName().trim() + "'";
		}else{
			hql = " From ImporterEntity where name = '" + importer.getName().trim() + "' and id <>" + importer.getId();
		}
		List<ImporterEntity> importerlist = baseDao.execute(hql);
		if(importerlist == null || importerlist.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if supplier name exists already or not in DB
	 * the function if being user when adding supplier name or update old supplier name
	 */
	@Override
	public boolean checkSupplierExists(SupplierEntity supplier) {
		String hql = null;
		if(supplier.getId() == null){
			hql = " From SupplierEntity where name = '" + supplier.getName().trim() +"'";
		}else {
			hql = " From SupplierEntity where name = '" + supplier.getName().trim() +"' and id <>" + supplier.getId();
		}
		List<SupplierEntity> supplierlist = baseDao.execute(hql);
		if(supplierlist == null || supplierlist.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if sale manager name exists already or not in DB
	 * the function if being user when adding  sale manager name  or update old  sale manager name 
	 */
	@Override
	public boolean checkSaleManagerExists(SaleManagerEntity saleManager) {
		String hql = null;
		if(saleManager.getId() == null){
			hql = " From SaleManagerEntity where name = '" + saleManager.getName().trim() +"'";
		}else{
			hql = " From SaleManagerEntity where name = '" + saleManager.getName().trim() +"' and id <>" + saleManager.getId();
		}
		List<SaleManagerEntity> saleManagerList = baseDao.execute(hql);
		if(saleManagerList == null || saleManagerList.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if logistics company name exists already or not in DB
	 * the function if being user when adding logistics company name or update old logistics company name
	 */
	@Override
	public boolean checkLogisticsExists(LogisticCompanyEntity logisticsCompany) {
		String hql = null;
		if(logisticsCompany.getId() == null){
			hql = " From LogisticCompanyEntity where name = '" + logisticsCompany.getName().trim() +"'";
		}else{
			hql = " From LogisticCompanyEntity where name = '" + logisticsCompany.getName().trim() +"' and id <>" + logisticsCompany.getId();
		}
		List<LogisticCompanyEntity> logisticslist = baseDao.execute(hql);
		if(logisticslist == null || logisticslist.size() == 0){
			return false;
		}
		return true;
	}

	@Override
	public List<String> listConmercialNameFromVarity() {
		String hql = " select DISTINCT commercialName From VarietyEntity";
		return baseDao.execute(hql);
	}

	@Override
	public List<VarietyEntity> listVarieties() {
		
		return baseDao.getAll(VarietyEntity.class);
	}

	@Override
	public VarietyEntity getVarietyById(Integer id) {
		
		return baseDao.get(VarietyEntity.class, id);
	}

	@Override
	public List<VarietyEntity> listVarietiesBySpecies(int id) {
		String hql = "  From VarietyEntity where species.id = " + id;
		return baseDao.execute(hql);
	}


 
	
	
	
}
