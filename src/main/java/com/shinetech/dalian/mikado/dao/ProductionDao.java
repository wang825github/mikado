package com.shinetech.dalian.mikado.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;

public interface ProductionDao  {
void saveProductions(ProductionsEntity productions);
	
	void updateProductionsByStorage(StorageEntity storage);
	
	void editProductions(ProductionsEntity productions);
	
//	void deletProductionsByStorageInfo(Integer seedId,Integer packingUnitId,Date storageDay);
	
	void saveStorageEntity(StorageEntity entity);
	
	void deleteProductionsByStorageId(Integer storageId);
	
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
	List<String> listProductionQRCodeByStorage(StorageEntity storage);
	
	/**
	 * Get productions by storage
	 * @param storage
	 * @return productions list
	 */
	List<ProductionsEntity> listProductionsByStorage(StorageEntity storage);
	
	/**
	 * Get productions by storage and production status
	 * @param storage
	 * @param status
	 * @return productions list
	 */
	List<ProductionsEntity> listProductionsByStorageAndStatus(StorageEntity storage,String status);
	
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
	List<StorageEntity> listStorage(Map<String, Object> params);
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
	List<StorageEntity> listStorageByPackingUnit(Integer packingUnitId,Integer start,Integer maxResult);
	
	/**
	 * Get storage by commercial name
	 * When return to front end,display storage by pagination function
	 * @param conmercial_name
	 * @param start
	 * @param maxResult
	 * @return storage list
	 */
	List<StorageEntity> listStorageByConmercialName(String conmercial_name,Integer start,Integer maxResult);
	
	/**
	 * Get storage by commercial name and packing unit id
	 * When return to front end,display storage by pagination function
	 * @param conmercial_name
	 * @param packingUnitId
	 * @param start
	 * @param maxResult
	 * @return storage list
	 */
	List<StorageEntity> listStorageBySearchConditions(String conmercial_name,Integer packingUnitId,Integer start,Integer maxResult);
	
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

	boolean listPackagingsByPackageId(Integer packageId);

	StorageEntity getLastProductionLotNumber(String code);

	void updateProductionStatus(Integer storageId, Double deliveryAmount);

	void updateProductionStatusByQRCode(ArrayList<String> QRCodeList);

	List<ProductionsEntity> listProductionsByStorageAndStatusForDelivery(StorageEntity storage, String status);

	
}
