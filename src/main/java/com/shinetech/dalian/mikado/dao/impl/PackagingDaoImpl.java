package com.shinetech.dalian.mikado.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.PackagingDao;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
//import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;

@Service
@Repository
public class PackagingDaoImpl implements PackagingDao {
	
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
	public void savePackaging(PackagingEntity packaging) {
		
		baseDao.save(packaging);
		
	}
	
	/**
	 * Update productions by storage
	 */
	@Override
	public void updateProductionsByStorage(StorageEntity storage) {
		
		baseDao.save(storage);
	}
	
	@Override
	public void updatePackagingList(String ids) {
		String hql = "update PackagingEntity packageing set packageing.status=1 where packageing.id in("+ids+") ";
		baseDao.executeUpdate(hql);
		
	}
	
	@Override
	public void updatePackagesSurplusAmount(PackageEntity packageEntity) {
		String sql = "update packages set surplus_amount=(select count(*) from packaging where packages_id="+packageEntity.getId()+" and status=0) where id="+packageEntity.getId();
		 baseDao.executeSqlUpdate(sql);
		
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
	public List<PackageEntity> listStorage(Map<String, Object> params) {
		
		String hql = " From PackageEntity order by id desc";
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
	public List<String> listPackagingQRCodeByPackage(
			PackageEntity packages) {
		String hql = " select qRCode From PackagingEntity where packages.id = " + packages.getId(); 
		
		return baseDao.execute(hql);
	}
	
	/**
	 * Get productions by storage
	 */
	@Override
	public List<PackagingEntity> listPackagingsByPackage(PackageEntity packages) {
		
		String hql = " From PackagingEntity where packages.id = "+ packages.getId();
		
		return baseDao.execute(hql);
	}
	
	
	/**
	 * set storage day of packaging records by the packages id
	 */
	@Override
	public void setStorageDayOfPackaging(PackageEntity packageEntity,
			Date storageDay) {
		String sql = "update packaging set storage_day = " + storageDay + " where packages_id = " + packageEntity.getId();
		baseDao.executeSqlUpdate(sql);
	}

	/**
	 * Get productions by storage and status
	 */
	@Override
	public List<PackagingEntity> listPackagingsByPackagesAndStatus(
			PackageEntity packages, Integer status) {

		String hql = " From PackagingEntity where packages.id = "
				+ packages.getId() + " and status = " + status + "";

		return baseDao.execute(hql);
	}
	
	/**
	 * Edit production information
	 */
//	@Override
//	public void editProductions(ProductionsEntity productions) {
//		
//		baseDao.save(productions);
//		
//	}
	
	
	
	/**
	 * Get production list by seed id
	 */
	@Override
	public List<ProductionsEntity> listProductionsBySeedId(Integer seedId) {
		String hql = " From ProductionsEntity where seed.id = " + seedId;
		return baseDao.execute(hql);
	}
	
	
	/**
	 * Get packages records by IDs
	 */
	@Override
	public List<PackageEntity> listPackagesByIDs(String packagesIds) {
		String hql = "  From PackageEntity where id in(" + packagesIds + ")";
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
	public void deletePackagingsByPackageId(Integer packageId) {
		String hql = "Delete From PackagingEntity where packages.id = " + packageId;
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
	public List<PackageEntity> listStorageByPackingUnit(Integer packingUnitId,
			Integer start, Integer maxResult) {
		String hql = " From PackageEntity where packingUnit.id = " + packingUnitId +" order by id desc";
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
	public List<PackageEntity> listStorageBySpeciesName(
			String speciesNameCn, Integer start, Integer maxResult) {
		String hql = " From PackageEntity where species.speciesNameCn like '%" + speciesNameCn +"%' order by id desc";
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
	public List<PackageEntity> listStorageBySearchConditions(
			String speciesNameCn, Integer packingUnitId, Integer start,
			Integer maxResult) {
		String hql = " From PackageEntity where species.speciesNameCn like '%"
				+ speciesNameCn + "%' and packingUnit.id = " + packingUnitId
				+ " order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

	@Override
	public void savePackages(PackageEntity packages) {
		
		baseDao.save(packages);
	}

	@Override
	public PackageEntity getPackagesById(Integer id) {
		
		return baseDao.get(PackageEntity.class, id);
	}

	@Override
	public void editPackagings(PackagingEntity packaging) {
		baseDao.save(packaging);
		
	}


	@Override
	public void deletePackagesById(Integer packageId) {
		baseDao.delete(PackageEntity.class, packageId);
		
	}

	@Override
	public List<PackingUnitEntity> listPackingUnitsFromPackages() {
		String sql = "select * from packing_unit where id in (select packing_unit_id from packages)";
		return baseDao.executeSQL(sql, PackingUnitEntity.class);
	}

	@Override
	public List<PackageEntity> listPackages() {
		
		return baseDao.getAll(PackageEntity.class);
	}

	@Override
	public PackagingEntity getPackagingByQRCode(String qrCode) {
		String hql = " From PackagingEntity where qRCode = '" + qrCode +"'";
		return baseDao.executeGetFirst(hql);
	}

	@Override
	public List<PackagingEntity> getPackagingByQRCodeList(List<String> qrCodeList) {
		String qrCode="'0'";
		for(String code:qrCodeList){
			qrCode=qrCode+",'"+code+"'";
		}
		String hql = " From PackagingEntity where qRCode in (" + qrCode +")";
		return baseDao.execute(hql);
	}
	
	@Override
	public List<PackagingEntity> getPackagingForSample(String ids,int quantity) {
		String hql = " From PackagingEntity where status=0 and packages.id in(" + ids +")";
		return baseDao.executeByLimit(hql, 0, quantity);
	}
	
	
	@Override
	public boolean checkQRCodeInPackaging(String qrCode) {
		String hql = "From PackagingEntity where qRCode = '" + qrCode + "'";
		List<PackagingEntity> packagingList = baseDao.execute(hql);
		if(packagingList != null && packagingList.size() >0){
			return true;
		}
		return false;
	}

	@Override
	public PackageEntity getLastPackagingLotNumber(String code) {
		String hql = " From PackageEntity where lotNumber like '" + code +"%' ORDER BY lotNumber desc";
		List<PackageEntity> packages= baseDao.execute(hql);
		if(packages!=null&&!packages.isEmpty()){
			return packages.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Integer getPackagingCountByStatusAndPackages(Integer packagesId, Integer status) {
		String sql = "Select count(*) from PackagingEntity where packages_id = " + packagesId + " and status = " + status;
		return ((Number)baseDao.openSession().createQuery(sql).uniqueResult()).intValue();
		
	}

	@Override
	public List<PackagingEntity> listPackagingByPackagesIdAndAmountAndStatus(
			Integer packagesId, Integer packagingAmount,Integer status) {
//		String hql = "Select top " + packagingAmount + " * From PackagingEntity where packages.id = " + packagesId + " and status = " + status;
		String hql = " From PackagingEntity where packages.id = " + packagesId + " and status = " + status;
		return baseDao.executeByLimit(hql, 0, packagingAmount);
	}
	
	@Override
	public List<PackageEntity> getPackagesBySpecies(Integer speciesID) {
		String hql = " from PackageEntity where species.id = " + speciesID ;
		return baseDao.execute(hql);
		
	}

	@Override
	/**
	 * 设置样品出库包装物状态
	 * 设置packaging表记录的状态，修改前状态为：originalStatus，修改后状态为：updateStatus，修改记录数量为：amount
	 */
	public void setPackagingStatus(String packageIds, Integer updateStatus,
			Integer originalStatus, Double amount) {
		// String sql = " update packaging set status = " + updateStatus +
		// " where packages_id in (" + packageIds + ") and status = " +
		// originalStatus;
		String sql = " update packaging set status = " + updateStatus
				+ " where id in (select top " + amount.intValue()
				+ " id from packaging where packages_id in (" + packageIds
				+ ") and status = " + originalStatus + ")";
		baseDao.executeSqlUpdate(sql);

	}

	/**
	 * 设置非样品包装物出库状态
	 */
	@Override
	public void setPackagingStatusOfNonSample(ArrayList<String> resultList,
			Integer status, Integer data_manage_id,List<ProductionsEntity> productionsEntities) {
		StringBuffer productionIds= new StringBuffer();

		if(CollectionUtils.isNotEmpty(productionsEntities)){
			for(int i =0;i<productionsEntities.size();i++){
				productionIds.append("'"+productionsEntities.get(i).getId()+"'"+",");
				if(i % 1900 == 0 ){
					saveProductionsEntities(productionIds,data_manage_id);
					productionIds = new StringBuffer();
					continue;
				}
				if(productionsEntities.size() - i<1900){
					productionIds = new StringBuffer();
					String sqlPara = productionsEntities.stream().skip(i-1).map(o -> "'"+String.valueOf(o.getId())+"'"+",").reduce("",String::concat);
					saveProductionsEntities(productionIds.append(sqlPara),data_manage_id);
					break;
				}
			}
		}
		StringBuffer qrCode= new StringBuffer();
		if(CollectionUtils.isNotEmpty(resultList)){
			for(int i =0;i<resultList.size();i++){
				qrCode.append("'"+resultList.get(i)+"'"+",");
				if(i % 2000 == 0 ){
					savePackagingStatusOfNonSample(qrCode,status);
					qrCode = new StringBuffer();
					continue;
				}
				System.out.println("continue: ");
				if(resultList.size() - i<2000){
					qrCode = new StringBuffer();
					String sqlPara;
					if(i == 0 ){
						sqlPara = resultList.stream().map(o -> o = "'"+o+"'"+",").reduce("",String::concat);
					}else {
						sqlPara = resultList.stream().skip(i-1).map(o -> o = "'"+o+"'"+",").reduce("",String::concat);
					}

					savePackagingStatusOfNonSample(qrCode.append(sqlPara),status);
					break;
				}
			}
		}


	}
	public void saveProductionsEntities(StringBuffer productionId,int dataManageId){
		productionId.deleteCharAt(productionId.length()-1);
		String sql = "update productions set data_manage_id = " + dataManageId  + " where id in (" + productionId.toString() +")";
		baseDao.executeSqlUpdate(sql);
	}
	public void savePackagingStatusOfNonSample(StringBuffer qrCode,Integer status){
		qrCode.deleteCharAt(qrCode.length()-1);
		String sql = "update packaging set status = " + status  + " where QR_code in (" + qrCode.toString() +")";
		baseDao.executeSqlUpdate(sql);
	}
	@Override
	public void updateSpeciesByPackageIdAndSpeciesId(Integer packageId, Integer speciesId) {
		String sql = " update packaging set species_id = " + speciesId + " where packages_id = " + packageId;
		baseDao.executeSqlUpdate(sql);
	}

	@Override
	public void updatePackingUnitIdOfPackaging(Integer packagesId,
			Integer packingUnitId) {
		String sql = " update packaging set packing_unit_id = " + packingUnitId + " where packages_id = " + packagesId;
		baseDao.executeSqlUpdate(sql);
		
	}
	
	
}
