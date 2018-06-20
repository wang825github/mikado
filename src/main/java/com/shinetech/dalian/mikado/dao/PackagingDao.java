package com.shinetech.dalian.mikado.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;




















import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
//import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;

/**
 * 
 * @author abc
 *
 */
public interface PackagingDao {
	
	void savePackaging(PackagingEntity packaging);
	
	void updateProductionsByStorage(StorageEntity storage);
	
	void editPackagings(PackagingEntity packaging);
	
//	void deletProductionsByStorageInfo(Integer seedId,Integer packingUnitId,Date storageDay);
	
	void saveStorageEntity(StorageEntity entity);
	
	void deletePackagingsByPackageId(Integer packageId);
	
	/**
	 * Get QRCode prefix from DB
	 * it is used to generate QRCode when storage productions
	 * @return QRCode string
	 */
	String getQRCodePrefix();
	
	StorageEntity getStorageByStorageId(Integer id);
	
//	List<String> listPackageType();
	
	/**
	 * Get productions QRCodes by storage
	 * @param storage
	 * @return productions QRCodes string list
	 */
	List<String> listPackagingQRCodeByPackage(PackageEntity packages);
	
	/**
	 * Get productions by storage
	 * @param storage
	 * @return productions list
	 */
	List<PackagingEntity> listPackagingsByPackage(PackageEntity packages);
	
	/**
	 * Get productions by storage and production status
	 * @param storage
	 * @param status
	 * @return productions list
	 */
	List<PackagingEntity> listPackagingsByPackagesAndStatus(PackageEntity packages,Integer status);
	
	/**
	 * Get productions by seed id
	 * @param seedId
	 * @return productions list
	 */
	List<ProductionsEntity> listProductionsBySeedId(Integer seedId);
	
	/**
	 * Get seeds from storage 
	 * @return seed list
	 */
	List<SeedEntity> listSeedsFromStorage();
	
	/**
	 * Get packing units from storage
	 * @return packing unit list
	 */
	List<PackingUnitEntity> listPackingUnitsFromStorage();
	
	/**
	 * Get storage from DB
	 * When return to front end,display storage by pagination function
	 * @param params
	 * @return storage list
	 */
	List<PackageEntity> listStorage(Map<String, Object> params);
	/**
	 * Get storage summary
	 * When return to front end,display storage by pagination function
	 * @param speciesId
	 * @param conmercial_name
	 * @param packingUnitId
	 * @return
	 */
//	List<StorageEntity> listStorageSummary(Integer speciesId,String conmercial_name, Integer packingUnitId);
	
	/**
	 * Get storage  by packing unit
	 * When return to front end,display storage by pagination function
	 * @param packingUnitId
	 * @param start
	 * @param maxResult
	 * @return storage list
	 */
	List<PackageEntity> listStorageByPackingUnit(Integer packingUnitId,Integer start,Integer maxResult);
	
	/**
	 * Get storage by commercial name
	 * When return to front end,display storage by pagination function
	 * @param conmercial_name
	 * @param start
	 * @param maxResult
	 * @return storage list
	 */
	List<PackageEntity> listStorageBySpeciesName(String speciesNameCn,Integer start,Integer maxResult);
	
	/**
	 * Get storage by commercial name and packing unit id
	 * When return to front end,display storage by pagination function
	 * @param conmercial_name
	 * @param packingUnitId
	 * @param start
	 * @param maxResult
	 * @return storage list
	 */
	List<PackageEntity> listStorageBySearchConditions(String speciesNameCn,Integer packingUnitId,Integer start,Integer maxResult);
	
	/**
	 * Get storage sum by packing unit id
	 * When return to front end,display storage summary by pagination function
	 * @param packingUnitId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumByPackingUnitId(Integer packingUnitId,Integer start,Integer maxResult);
	
	/**
	 * Get storage summary by commercial name
	 * When return to front end,display storage summary by pagination function
	 * @param conmercial_name
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumByConmercialName(String conmercial_name,Integer start,Integer maxResult);
	
	/**
	 * Get storage summary by commercial name and packing unit id and species id
	 * When return to front end,display storage summary by pagination function
	 * @param conmercial_name
	 * @param packingUnitId
	 * @param speciesId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumBySearchConditions(String conmercial_name,Integer packingUnitId,Integer speciesId,Integer start,Integer maxResult);
	
	/**
	 * Get all storage summary 
	 * When return to front end,display storage summary by pagination function
	 * @param params
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSummary(Map<String, Object> params);
	
	/**
	 * Get all storage summary by species id
	 * When return to front end,display storage summary by pagination function
	 * @param speciesId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumBySpeciesId(Integer speciesId, Integer start,Integer maxResult);
	
	/**
	 * Get storage summary by packing unit id and species id
	 * When return to front end,display storage summary by pagination function
	 * @param packingUnitId
	 * @param speciesId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumByPackingUnitAndSpecies(Integer packingUnitId,Integer speciesId,Integer start,Integer maxResult);
	
	/**
	 * Get storage summary by commercial name and species id
	 * When return to front end,display storage summary by pagination function
	 * @param conmercial_name
	 * @param speciesId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumByConmercialNameAndSpecies(String conmercial_name, Integer speciesId,Integer start,Integer maxResult);
	
	/**
	 * Get storage summary by commercial name and packing unit id
	 * When return to front end,display storage summary by pagination function
	 * @param conmercial_name
	 * @param packingUnitId
	 * @param start
	 * @param maxResult
	 * @return storage sum view list
	 */
	List<StorageSumView> listStorageSumByConmercialNameAndPackingUnit(String conmercial_name, Integer packingUnitId,Integer start,Integer maxResult);

	void savePackages(PackageEntity packages);

	PackageEntity getPackagesById(Integer id);


	void deletePackagesById(Integer packageId);

	List<PackingUnitEntity> listPackingUnitsFromPackages();

	List<PackageEntity> listPackages();

	PackagingEntity getPackagingByQRCode(String qrCode);
	
	/**
	 * 检查qrcode是否属于packaging表记录，属于则返回true,否则返回false
	 * @param qrCode
	 * @return
	 */
	boolean checkQRCodeInPackaging(String qrCode);
	
	PackageEntity getLastPackagingLotNumber(String code);

	Integer getPackagingCountByStatusAndPackages(Integer packagesId, Integer status);

	List<PackagingEntity> listPackagingByPackagesIdAndAmountAndStatus(Integer packagesId, Integer packagingAmount,Integer status);

	List<PackageEntity> getPackagesBySpecies(Integer speciesID);

	List<PackagingEntity> getPackagingByQRCodeList(List<String> qrCodeList);

	void updatePackagingList(String ids);

	void updatePackagesSurplusAmount(PackageEntity packageEntity);

	List<PackagingEntity> getPackagingForSample(String ids, int quantity);

	void setStorageDayOfPackaging(PackageEntity packageEntity, Date storageDay);

	List<PackageEntity> listPackagesByIDs(String packagesIds);

	void setPackagingStatus(String packageIds, Integer updateStatus,Integer originalStatus,Double amount);

	void setPackagingStatusOfNonSample(ArrayList<String> resultList, Integer status);

	void updateSpeciesByPackageIdAndSpeciesId(Integer packageId, Integer speciesId);

	void updatePackingUnitIdOfPackaging(Integer packagesId, Integer packingUnitId);
	
//	List<PackagingEntity> listPackagings(Map<String, Object> params);
//	
//	List<PackagingEntity> getPackagingByBuyDate(String buyDate,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingByAmount(String amount,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingByAmountAndBuyDate(String amount,String buyDate,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingByType(String type,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingByTypeAndBuyDate(String type,String buyDate,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingByTypeAndAmount(String type,String amount,Integer start,Integer maxResult);
//	
//	List<PackagingEntity> getPackagingBySearch(String type,String amount,String buyDate,Integer start,Integer maxResult);
	
//	List<StorageEntity> listStorageBySeedIdFromProductions(Integer seedId);
}
