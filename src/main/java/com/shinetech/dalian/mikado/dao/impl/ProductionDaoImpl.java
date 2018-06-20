package com.shinetech.dalian.mikado.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.HibernateDao;
import com.shinetech.dalian.mikado.dao.ProductionDao;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;

@Service
@Repository
public class ProductionDaoImpl extends HibernateDao implements ProductionDao  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private BaseDao baseDao;

//	@Override
//	public PackagingEntity getPackagingById(Integer id) {
//		
//		return baseDao.get(PackagingEntity.class, id);
//	}

//	@Override
//	public List<PackagingEntity> getPackagings(Map<String, Object> params) {
//		String hql = " From PackagingEntity ";
//		if(params == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, (int)params.get("start"), (int)params.get("maxResult"));
//		}
//	}
	
	
	/**
	 * Get productions from DB by pagination function
	 */
	@Override
	public List<StorageSumView> listStorageSummary(Map<String, Object> params) {
		String hql = " From StorageSumView";
		if(params == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)params.get("start"), (int)params.get("maxResult"));
		}
	}

//	@Override
//	public List<PackagingEntity> getPackagingByBuyDate(String buyDate,
//			Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where buyDate = '" + buyDate +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingByAmount(String amount,
//			Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where amount = '" + amount +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingByAmountAndBuyDate(String amount,
//			String buyDate, Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where amount = '" + amount +"' and buyDate = '" + buyDate + "'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingByType(String type, Integer start,
//			Integer maxResult) {
//		String hql = " From PackagingEntity where type = '" + type +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingByTypeAndBuyDate(String type,
//			String buyDate, Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where type = '" + type +"' and buyDate = '" + buyDate +"'";
//		if(start == null && buyDate == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingByTypeAndAmount(String type,
//			String amount, Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where type = '" + type +"' and amount = '" + amount +"'" ;
//		if(start == null && amount == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<PackagingEntity> getPackagingBySearch(String type,
//			String amount, String buyDate, Integer start, Integer maxResult) {
//		String hql = " From PackagingEntity where type = '" + type +" and amount = '" + amount +" and buyDate = '" + buyDate +"'";
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Insert production into DB
	 */
	@Override
	public void saveProductions(ProductionsEntity productions) {
		
		baseDao.save(productions);
		
	}
	
	/**
	 * Update productions by storage
	 */
	@Override
	public void updateProductionsByStorage(StorageEntity storage) {
		
		baseDao.save(storage);
	}
	
	

//	@Override
//	public void deletProductionsByStorageInfo(Integer seedId,Integer packingUnitId,Date storageDay) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String storageString = formatter.format(storageDay);
//		
//		String hql = "Delete From ProductionsEntity where seed_id = " + seedId 
//				+ " and packing_unit_id = " + packingUnitId
//				+ " and storage_day = '" + storageString + "'";
//		baseDao.executeUpdate(hql);
//				
//	}

//	@Override
//	public List<String> listPackageType() {
//		
//		return baseDao.getFieldByTableName("packaging", "type");
//	}
	
	/**
	 * Get production list by pagination function
	 */
	@Override
	public List<StorageEntity> listStorage(Map<String, Object> params) {
		
		String hql = " From StorageEntity order by id desc";
		if(params == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)params.get("start"), (int)params.get("maxResult"));
		}
	}
	
	/**
	 * Get production inventory list by packing unit id by pagination function
	 */
	@Override
	public List<StorageSumView> listStorageSumByPackingUnitId(
			Integer packingUnitId, Integer start, Integer maxResult) {
		String hql = " From StorageSumView where packingUnit_id = " + packingUnitId;
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory list by commercial name by pagination function
	 */
	@Override
	public List<StorageSumView> listStorageSumByConmercialName(String conmercial_name,
			Integer start, Integer maxResult) {
		String hql = " From StorageSumView where conmercial_name like '%" + conmercial_name +"%'";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory list by commercial name and packing unit id and species id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageSumView> listStorageSumBySearchConditions(
			String conmercial_name, Integer packingUnitId, Integer speciesId,
			Integer start, Integer maxResult) {
		String hql = " From StorageSumView where conmercial_name like '%"
				+ conmercial_name + "%' and packingUnit_id = " + packingUnitId
				+ " and species_id = " + speciesId;
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production QRcodes by storage
	 */
	@Override
	public List<String> listProductionQRCodeByStorage(
			StorageEntity storage) {
		String hql = " select QRCode From ProductionsEntity where storage.id = " + storage.getId(); 
		
		return baseDao.execute(hql);
	}
	
	/**
	 * Get productions by storage
	 */
	@Override
	public List<ProductionsEntity> listProductionsByStorage(StorageEntity storage) {
		
		String hql = " From ProductionsEntity where storage.id = "+ storage.getId();
		
		return baseDao.execute(hql);
	}
	
	/**
	 * Get productions by storage and status
	 */
	@Override
	public List<ProductionsEntity> listProductionsByStorageAndStatus(
			StorageEntity storage, String status) {

		String hql = " From ProductionsEntity where storage.id = "
				+ storage.getId() + " and status = '" + status + "'";

		return baseDao.execute(hql);
	}
	@Override
	public List<ProductionsEntity> listProductionsByStorageAndStatusForDelivery(
			StorageEntity storage, String status) {

		String hql = " From ProductionsEntity where storage.id = "
				+ storage.getId() + " and status = '" + status + "'";

		return baseDao.execute(hql);
	}
	/**
	 * Edit production information
	 */
	@Override
	public void editProductions(ProductionsEntity productions) {
		
		baseDao.save(productions);
		
	}
	
	/**
	 * Get production list by seed id
	 */
	@Override
	public List<ProductionsEntity> listProductionsBySeedId(Integer seedId) {
		String hql = " From ProductionsEntity where seed_id = " + seedId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get QRCode prefix 
	 */
	@Override
	public String getQRCodePrefix() {
		String hql = "select qrcode From QRCodePrefixEntity";
		return baseDao.executeGetFirst(hql);
	}
	
	/**
	 * Get storage by storage id
	 */
	@Override
	public StorageEntity getStorageByStorageId(Integer id) {
		
		return baseDao.get(StorageEntity.class, id);
	}

//	@Override
//	public List<StorageEntity> getStorageBySeedIdFromProductions(Integer seedId) {
//		String sql = "select * from storage where id in (SELECT storage_id from productions where seed_id = "+ seedId + ")";
//		
//		return baseDao.executeSQL(sql, StorageEntity.class);
//	}
	
	/**
	 * Insert storage into DB
	 */
	@Override
	public void saveStorageEntity(StorageEntity entity) {
		
		baseDao.save(entity);
	}
	
	/**
	 * Delete productions from DB by storage id
	 */
	@Override
	public void deleteProductionsByStorageId(Integer storageId) {
		String hql = "Delete From ProductionsEntity where storage.id = " + storageId;
		baseDao.executeUpdate(hql);
	}
	
	/**
	 * Get seed list from storage table
	 */
	@Override
	public List<SeedEntity> listSeedsFromStorage() {
		String sql = "select * from seed where id in (select seed_id from storage)";
		return baseDao.executeSQL(sql, SeedEntity.class);
	}
	
	/**
	 * Get packing unit list from storage table
	 */
	@Override
	public List<PackingUnitEntity> listPackingUnitsFromStorage() {
		String sql = "select * from packing_unit where id in (select packing_unit_id from storage)";
		return baseDao.executeSQL(sql, PackingUnitEntity.class);
	}

//	@Override
//	public List<StorageEntity> listStorageSummary(Integer speciesId,
//			String conmercial_name, Integer packingUnitId) {
//		String hql = " From StorageEntity where seed.species.id = " + speciesId
//				+ " and seed.conmercialName = '" + conmercial_name.trim()
//				+ "' and packingUnit.id = " + packingUnitId;
//
//		return baseDao.execute(hql);
//	}
	
	/**
	 * Get production inventory by species id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageSumView> listStorageSumBySpeciesId(Integer speciesId,
			Integer start, Integer maxResult) {
		String hql = " From StorageSumView where species_id = " + speciesId;
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory by packing unit id and species id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageSumView> listStorageSumByPackingUnitAndSpecies(
			Integer packingUnitId, Integer speciesId, Integer start,
			Integer maxResult) {
		String hql = " From StorageSumView where packingUnit_id = "
				+ packingUnitId + " and species_id = " + speciesId;
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory by commercial name and species id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageSumView> listStorageSumByConmercialNameAndSpecies(
			String conmercial_name, Integer speciesId, Integer start,
			Integer maxResult) {
		String hql = " From StorageSumView where conmercial_name like '%"
				+ conmercial_name + "%' and species_id = " + speciesId;
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory by commercial name and packing unit id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageSumView> listStorageSumByConmercialNameAndPackingUnit(
			String conmercial_name, Integer packingUnitId, Integer start,
			Integer maxResult) {
		String hql = " From StorageSumView where conmercial_name like '%"
				+ conmercial_name + "%' and packingUnit_id =" + packingUnitId;
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get production inventory by packing unit id
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageEntity> listStorageByPackingUnit(Integer packingUnitId,
			Integer start, Integer maxResult) {
		String hql = " From StorageEntity where packages.packingUnit.id = " + packingUnitId +" order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get storage by commercial name
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageEntity> listStorageByConmercialName(
			String conmercial_name, Integer start, Integer maxResult) {
		String hql = " From StorageEntity where seed.variety.commercialName like '%" + conmercial_name +"%' order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get storage by packing unit id and commercial name
	 * server side pagination function is being used
	 */
	@Override
	public List<StorageEntity> listStorageBySearchConditions(
			String conmercial_name, Integer packingUnitId, Integer start,
			Integer maxResult) {
		String hql = " From StorageEntity where seed.variety.commercialName like '%"
				+ conmercial_name + "%' and packages.packingUnit.id = " + packingUnitId
				+ " order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

	@Override
	public boolean listPackagingsByPackageId(Integer packageId) {
		String sql = " select * from  productions where packaging_id in ( select id from  packaging where packages_id = " + packageId + ")";
		List<ProductionsEntity> productionsList = baseDao.executeSQL(sql, ProductionsEntity.class);
		if(productionsList != null && productionsList.size() != 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public StorageEntity getLastProductionLotNumber(String code) {
		String hql = " From StorageEntity where productionLotNumber like '" + code +"%' ORDER BY productionLotNumber desc";
		List<StorageEntity> storages= baseDao.execute(hql);
		if(storages!=null&&!storages.isEmpty()){
			return storages.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateProductionStatus(Integer storageId, Double deliveryAmount) {
		String sql = " update productions set status = '出库' where id in (select top " + deliveryAmount.intValue() + " id from productions where storage_id = " + storageId + ")"; 
		baseDao.executeSqlUpdate(sql);
	}

	@Override
	public void updateProductionStatusByQRCode(ArrayList<String> QRCodeList) {
	 
		String hqlPackagingEntity = "FROM PackagingEntity p WHERE p.qRCode IN(:QRCodeList)";
		String hql = "UPDATE ProductionsEntity p  SET status = '出库' WHERE p.packaging IN(:packingList)";
		
		Query<PackagingEntity> queryList  = super.getSession().createQuery(hqlPackagingEntity);
		queryList.setParameterList("QRCodeList",QRCodeList);
		List<PackagingEntity> packingList = queryList.list();
		 
		Query query = super.getSession().createQuery(hql);
		query.setParameterList("packingList", packingList);
		query.executeUpdate();

	}
	
	
}
