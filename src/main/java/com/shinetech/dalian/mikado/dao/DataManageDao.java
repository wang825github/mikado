package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.DataManageEntity;

/**
 * 
 * @author abc
 *
 */
public interface DataManageDao {
	void saveDataManage(DataManageEntity dataManage);
	
	void editDataManage(DataManageEntity dataManage);
	
	/**
	 * Check if customer is associated or not in data_manage table
	 * @param id
	 * @return true if customer is associated,false if not
	 */
	boolean checkCustomerInDataManage(Integer id);
	
	/**
	 * Get data manage by id
	 * @param id
	 * @return DataManageEntity
	 */
	DataManageEntity getDataManageById(Integer id);
	
	/**
	 * Get data manage by logNumber
	 * One data manage record has only one lotNumber
	 * @param lotNumbers
	 * @return
	 */
	DataManageEntity getDataManageByLotNumbers(String lotNumbers);
	
//	List<DataManageEntity> listDataManageByDeliveryTime(String startTime,String endTime,Integer start,Integer maxResult);
	
//	List<DataManageEntity> listDataManageByCustomerId(Integer customerId,Integer start,Integer maxResult);
	
//	List<DataManageEntity> listDataManageByCustomerIdAndDeliverytime(Integer customerId,String startTime,String endTime,Integer start,Integer maxResult);
	
	
//	List<DataManageEntity> listDataManageBySalesIdAndDeliverytime(Integer salesId,String startTime,String endTime,Integer start,Integer maxResult);
	
//	List<DataManageEntity> listDataManageBySalesIdAndCustomerId(Integer salesId,Integer customerId,Integer start,Integer maxResult);
	
	/**
	 * Get data manage from DB
	 * The pagination function is being used
	 * @param params
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManage(Map<String, Object> params);
	
	/**
	 * Get data manage by sale id from DB
	 * The pagination function is being used
	 * @param salesId
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<DataManageEntity> listDataManageBySalesId(Integer salesId,Integer start,Integer maxResult);
	
	/**
	 * Get data manage by sale id,commercial name and city id
	 * The pagination function is being used
	 * @param salesId
	 * @param conmercialName
	 * @param cityId
	 * @param start
	 * @param maxResult
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageBySearches(Integer salesId,String conmercialName,Integer cityId,Integer start,Integer maxResult);
	/**
	 * Get data manage by strings of lotNumbers
	 * The pagination function is being used
	 * @param years
	 * @param spName
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByCityId(Integer cityId,Integer start,Integer maxResult);
	/**
	 * Get data manage by strings of lotNumbers
	 * The pagination function is being used
	 * @param years
	 * @param spName
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByConmercialName(String conmercialName,Integer start,Integer maxResult);
	/**
	 * Get data manage by strings of lotNumbers
	 * The pagination function is being used
	 * @param years
	 * @param spName
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByConmercialNameAndCityId(String conmercialName, Integer cityId,Integer start,Integer maxResult);
	/**
	 * Get data manage by conmercialName,cityId,start,maxResult
	 * The pagination function is being used
	 * @param years
	 * @param spName
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageBySalesIdAndCityId(Integer saleId,Integer cityId,Integer start,Integer maxResult);
	/**
	 * Get data manage by sales id and commercial name
	 * The pagination function is being used
	 * @param saleId
	 * @param conmercialName
	 * @param start
	 * @param maxResult
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageBySalesIdAndConmercialName(Integer saleId, String conmercialName,Integer start,Integer maxResult);
	
	/**
	 * Get data manage by strings of lotNumbers
	 * @param years
	 * @param spName
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageBySpecies(String years, String spName );
	
	/**
	 * Get data manage by logistics company id
	 * @param logisticsId
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByLogisticId(Integer logisticsId);
	
	/**
	 * Get data manage by storage id
	 * @param storageId
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByStorageId(Integer storageId);
	
	/**
	 * Get data manage by importer id
	 * @param importerId
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageByImporterId(Integer importerId);
	
	/**
	 * Get data manage by supplier id
	 * @param supplierId
	 * @return DataManageEntity list
	 */
	List<DataManageEntity> listDataManageBySupplierId(Integer supplierId);

	DataManageEntity getLastOrderLotNumber(String code);

}
