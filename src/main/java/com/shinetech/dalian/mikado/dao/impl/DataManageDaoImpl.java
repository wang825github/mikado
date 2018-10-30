package com.shinetech.dalian.mikado.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.HibernateDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.util.FileLogUtils;
/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class DataManageDaoImpl extends HibernateDao implements DataManageDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = 150185096075725240L;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private FileLogUtils fileLogUtils;
	/**
	 * Insert a data manage into DB
	 */
	@Override
	public void saveDataManage(DataManageEntity dataManage) {
		baseDao.save(dataManage);
	}
	
	/**
	 * Get data manage list from DB by pagination function
	 */
	@Override
	public List<DataManageEntity> listDataManage(Map<String, Object> params) {
		String hql = " Select new DataManageEntity(d.id, d.lotNumbers, d.oddNumbers, d.receivingTime, d.deliveryTime , d.outStorageDay, "
				+ " d.status , d.deliveryAmount,  d.isSample, d.storage, d.customer, d.logisticCompany)  From DataManageEntity  d  LEFT JOIN d.storage "
				+ "LEFT JOIN d.customer  LEFT JOIN d.logisticCompany order by d.id desc";
		if(params == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)params.get("start"), (int)params.get("maxResult"));
		}
	}

//	@Override
//	public List<DataManageEntity> getDataManageByDeliveryTime(
//			String startTime,String endTime, Integer start, Integer maxResult) {
//		String hql = "";
//		//null,null
//		if(startTime == null && endTime == null){
//			hql = " From DataManageEntity";
//		}
//		//null,not null
//		if(startTime == null && endTime != null){
//			hql = " From DataManageEntity where deliveryTime <= '" + endTime + "'";
//		}
//		//not null,null
//		if(startTime != null && endTime == null){
//			hql = " From DataManageEntity where deliveryTime >= '" + startTime + "'";
//		}
//		//not null,not null
//		if(startTime != null && endTime != null){
//			hql = " From DataManageEntity where deliveryTime >= '" + startTime + "' and deliveryTime <= '" + endTime +"'";
//		}
//		
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<DataManageEntity> getDataManageByCustomerId(Integer customerId,
//			Integer start, Integer maxResult) {
//		String hql = " From DataManageEntity where customer_id = " + customerId;
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<DataManageEntity> getDataManageByCustomerIdAndDeliverytime(
//			Integer customerId, String startTime,String endTime, Integer start,
//			Integer maxResult) {
//		String hql = "";
//		//null,null
//		if(startTime == null && endTime == null){
//			hql = " From DataManageEntity where customer_id = " + customerId;
//		}
//		//null,not null
//		if(startTime == null && endTime != null){
//			hql = " From DataManageEntity where customer_id = "+ customerId + " and deliveryTime <= '" + endTime + "'";
//		}
//		//not null,null
//		if(startTime != null && endTime == null){
//			hql = " From DataManageEntity where customer_id = "+ customerId + " and deliveryTime >= '" + startTime + "'";
//		}
//		//not null,not null
//		if(startTime != null && endTime != null){
//			hql = " From DataManageEntity where customer_id = "+ customerId + " and deliveryTime >= '" + startTime + "' and deliveryTime <= '" + endTime +"'";
//		}
//		
//		
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Get data manage list by sales id by pagination function
	 */
	@Override
	public List<DataManageEntity> listDataManageBySalesId(Integer salesId,
			Integer start, Integer maxResult) {
		String hql = " From DataManageEntity where customer.saleManager.id = " + salesId;
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}

//	@Override
//	public List<DataManageEntity> getDataManageBySalesIdAndDeliverytime(
//			Integer salesId, String startTime,String endTime, Integer start,
//			Integer maxResult) {
//		
//		String hql = "";
//		//null,null
//		if(startTime == null && endTime == null){
//			hql = " From DataManageEntity where customer.saleManager.id = " + salesId;
//		}
//		//null,not null
//		if(startTime == null && endTime != null){
//			hql = " From DataManageEntity where customer.saleManager.id = " + salesId + " and deliveryTime <= '" + endTime + "'";
//		}
//		//not null,null
//		if(startTime != null && endTime == null){
//			hql = " From DataManageEntity where customer.saleManager.id = " + salesId + " and deliveryTime >= '" + startTime + "'";
//		}
//		//not null,not null
//		if(startTime != null && endTime != null){
//			hql = " From DataManageEntity where customer.saleManager.id = " + salesId + " and deliveryTime >= '" + startTime + "' and deliveryTime <= '" + endTime +"'";
//		}
//		
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}

//	@Override
//	public List<DataManageEntity> getDataManageBySalesIdAndCustomerId(
//			Integer salesId, Integer customerId, Integer start,
//			Integer maxResult) {
//		String hql = " From DataManageEntity where customer.saleManager.id = " + salesId + " and customer_id = " + customerId;
//		if(start == null && maxResult == null){
//			return baseDao.execute(hql);
//		}else{
//			return baseDao.executeByLimit(hql, start, maxResult);
//		}
//	}
	
	/**
	 * Get data manage list by sale id,commercial name and city id by pagination function
	 *  d left join fetch   d.productions 
	 */
	@Override
	public List<DataManageEntity> listDataManageBySearches(Integer salesId,
			String conmercialName, Integer cityId, Integer start,
			Integer maxResult) {
		String hql = " From DataManageEntity  where customer.saleManager.id ="
				+ salesId + " and storage.variety.commercialName like '%"
				+ conmercialName + "%' and customer.city.id = " + cityId
				+ " order by id desc";

		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get a data manage information by id
	 */
	@Override
	public DataManageEntity getDataManageById(Integer id) {
		
		return baseDao.get(DataManageEntity.class, id);
	}
	
	/**
	 * Edit a data manage information
	 */
	@Override
	public void editDataManage(DataManageEntity dataManage) {
		baseDao.save(dataManage);
		
	}
	
	/**
	 * Get data manage list by lot numbers
	 */
	@Override
	public List<DataManageEntity> listDataManageBySpecies(String years, String spName) {
		 String hql = "FROM DataManageEntity DM WHERE  DM.lotNumbers  like "+"'"+years+spName+"%'";
		return baseDao.execute(hql);
	}
	
	/**
	 * Check if customer is associated by data manage table
	 */
	@Override
	public boolean checkCustomerInDataManage(Integer customerId) {
		String hql = " From DataManageEntity where customer_id = " + customerId;
		if(baseDao.execute(hql) != null && baseDao.execute(hql).size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Get data manage list by logistics company id
	 */
	@Override
	public List<DataManageEntity> listDataManageByLogisticId(Integer logisticsId) {
		String hql = " From DataManageEntity where logistics_id = " + logisticsId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get data manage list by storage id
	 */
	@Override
	public List<DataManageEntity> listDataManageByStorageId(Integer storageId) {
		String hql = " From DataManageEntity where storage_id = " + storageId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get data manage list by importer id
	 */
	@Override
	public List<DataManageEntity> listDataManageByImporterId(Integer importerId) {
		String hql = "  From DataManageEntity where importer_id = " + importerId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get data manage list by supplier id
	 */
	@Override
	public List<DataManageEntity> listDataManageBySupplierId(Integer supplierId) {
		String hql = "  From DataManageEntity where supplier_id = " + supplierId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get data manage list by city id
	 */
	@Override
	public List<DataManageEntity> listDataManageByCityId(Integer cityId, Integer start,
			Integer maxResult) {
		String hql = " From DataManageEntity where customer.city.id = " + cityId + " order by id desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get data manage list by commercial name
	 */
	@Override
	public List<DataManageEntity> listDataManageByConmercialName(
			String conmercialName, Integer start, Integer maxResult) {
		String hql = " From DataManageEntity where storage.variety.commercialName like '%"
				+ conmercialName.trim() + "%' order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get data manage by commercial name and city id
	 */
	@Override
	public List<DataManageEntity> listDataManageByConmercialNameAndCityId(
			String conmercialName, Integer cityId, Integer start,
			Integer maxResult) {
		String hql = " From DataManageEntity where storage.variety.commercialName like '%"
				+ conmercialName.trim()
				+ "%' and customer.city.id = "
				+ cityId
				+ " order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get data manage list by sale id and city id
	 */
	@Override
	public List<DataManageEntity> listDataManageBySalesIdAndCityId(
			Integer saleId, Integer cityId, Integer start, Integer maxResult) {
		String hql = " From DataManageEntity where customer.saleManager.id = "
				+ saleId + " and customer.city.id = " + cityId
				+ " order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get data manage list by sales id and commercial name
	 */
	@Override
	public List<DataManageEntity> listDataManageBySalesIdAndConmercialName(
			Integer saleId, String conmercialName, Integer start,
			Integer maxResult) {
		String hql = " From DataManageEntity where customer.saleManager.id ="
				+ saleId + " and storage.variety.commercialName like '%"
				+ conmercialName.trim() + "%' order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get data manage list by log numbers
	 */
	@Override
	public DataManageEntity getDataManageByLotNumbers(String lotNumbers) {
		String hql = "FROM DataManageEntity dm WHERE dm.lotNumbers = '"+lotNumbers+"'";
		return baseDao.executeGetFirst(hql);
	}
	//select datamanage0_.id as id1_3_, datamanage0_.customer_id as custome10_3_, datamanage0_.delivery_amount as delivery2_3_, datamanage0_.delivery_time as delivery3_3_, datamanage0_.is_sample as is_sampl4_3_, datamanage0_.logistics_id as logisti11_3_, datamanage0_.lot_numbers as lot_numb5_3_, datamanage0_.odd_numbers as odd_numb6_3_, datamanage0_.out_storage_day as out_stor7_3_, datamanage0_.receiving_time as receivin8_3_, datamanage0_.status as status9_3_, datamanage0_.storage_id as storage12_3_ from data_manage datamanage0_ where datamanage0_.lot_numbers like '2018S%' order by datamanage0_.lot_numbers desc
	public DataManageEntity getLastOrderLotNumberBySql(String code) {
		Session session = super.getSession();
//		String sql = " select * from data_manage where lot_numbers like :code % order by lot_numbers desc";
		String sql =  " select lot_numbers from  data_manage where lot_numbers like '" + code +"%' ORDER BY lot_numbers desc";
		Query query = session.createNativeQuery(sql);
//		String hql = " From DataManageEntity where lotNumbers like '" + code +"%' ORDER BY lotNumbers desc";
//		List<DataManageEntity> orders= baseDao.execute(hql);
		List<String> orders = query.getResultList();

		if (CollectionUtils.isNotEmpty(orders)) {
			DataManageEntity dm = new DataManageEntity();
			dm.setLotNumbers(orders.get(0));
			return dm;
		} else
			return null;
	}
	
	@Override
	public DataManageEntity getLastOrderLotNumber(String code) {
		System.out.println("-DataManageDaoImpl.getLastOrderLotNumber开始-");
		fileLogUtils.writeLog("-DataManageDaoImpl.getLastOrderLotNumber开始-"," ");
		StopWatch watch=new  StopWatch();
		watch.start("DataManageDaoImpl.getLastOrderLotNumber(code)");
		String hql = " From DataManageEntity where lotNumbers like '" + code +"%' ORDER BY lotNumbers desc";
		List<DataManageEntity> orders= baseDao.execute(hql);
		watch.stop();
	    fileLogUtils.writeLog( watch.prettyPrint()," ");
	    if(CollectionUtils.isNotEmpty(orders))
	    fileLogUtils.writeLog( "List<DataManageEntity> size: "+orders.size()," ");
		fileLogUtils.writeLog("-DataManageDaoImpl.getLastOrderLotNumber 结束-"," ");
		System.out.println("-DataManageDaoImpl.getLastOrderLotNumber结束-");
		if(orders!=null&&!orders.isEmpty()){
			return orders.get(0);
		}else{
			return null;
		}
	}
}
