package com.shinetech.dalian.mikado.entity.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;

public class DataManageVO {
	private Integer id;
	private String lotNumbers; // production lot number
	private String oddNumbers; // logistics odd number
	private Date receivingTime; // production receive time
	private Date deliveryTime; // production delivery time 
	private Date outStorageDay;
	private Integer status;// order status
	private Double deliveryAmount; // production quantity being delivered
	private Integer isSample;//是否为样品 0：否 ，1：是
	
	private StorageVO storage;
	private CustomerEntity customer; // customer information
	private List<ProductionsEntity> productions = new ArrayList<ProductionsEntity>(); // production information
	private LogisticCompanyEntity logisticCompany; // logistics company information
	public DataManageVO() {
		super();
	}
	
	
	public DataManageVO(DataManageEntity dataManage) {
		super();
		this.id = dataManage.getId();
		this.lotNumbers = dataManage.getLotNumbers();
		this.oddNumbers = dataManage.getOddNumbers();
		this.receivingTime = dataManage.getReceivingTime();
		this.deliveryTime = dataManage.getDeliveryTime();
		this.outStorageDay = dataManage.getOutStorageDay();
		this.status = dataManage.getStatus();
		this.deliveryAmount = dataManage.getDeliveryAmount();
		this.isSample = dataManage.getIsSample();
		this.customer = dataManage.getCustomer();
		this.productions = dataManage.getProductions();
		this.logisticCompany = dataManage.getLogisticCompany();
		this.storage = new StorageVO(dataManage.getStorage());
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLotNumbers() {
		return lotNumbers;
	}
	public void setLotNumbers(String lotNumbers) {
		this.lotNumbers = lotNumbers;
	}
	public String getOddNumbers() {
		return oddNumbers;
	}
	public void setOddNumbers(String oddNumbers) {
		this.oddNumbers = oddNumbers;
	}
	public Date getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(Date receivingTime) {
		this.receivingTime = receivingTime;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Date getOutStorageDay() {
		return outStorageDay;
	}
	public void setOutStorageDay(Date outStorageDay) {
		this.outStorageDay = outStorageDay;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public Integer getIsSample() {
		return isSample;
	}
	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}
	public StorageVO getStorage() {
		return storage;
	}
	public void setStorage(StorageVO storage) {
		this.storage = storage;
	}
	public CustomerEntity getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	public List<ProductionsEntity> getProductions() {
		return productions;
	}
	public void setProductions(List<ProductionsEntity> productions) {
		this.productions = productions;
	}
	public LogisticCompanyEntity getLogisticCompany() {
		return logisticCompany;
	}
	public void setLogisticCompany(LogisticCompanyEntity logisticCompany) {
		this.logisticCompany = logisticCompany;
	}
	
	
}
