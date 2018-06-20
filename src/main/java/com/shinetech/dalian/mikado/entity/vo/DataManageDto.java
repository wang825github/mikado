package com.shinetech.dalian.mikado.entity.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;

import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;

public class DataManageDto {
	private Integer id;
	private String lotNumbers; // production lot number
	private String oddNumbers; // logistics odd number
	private Date receivingTime; // production receive time
	private Date deliveryTime; // production delivery time 
	private Date outStorageDay;
	private Integer status;// order status
	private Double deliveryAmount; // production quantity being delivered
	private Integer isSample;//是否为样品 0：否 ，1：是
	
	private StorageEntity storage;
	private CustomerEntity customer; // customer information
	
	private LogisticCompanyEntity logisticCompany; // logistics company information
	public DataManageDto() {
		super();
	}
	
 

	public StorageEntity getStorage() {
		return storage;
	}



	public void setStorage(StorageEntity storage) {
		this.storage = storage;
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
 
	public CustomerEntity getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	 
	public LogisticCompanyEntity getLogisticCompany() {
		return logisticCompany;
	}
	public void setLogisticCompany(LogisticCompanyEntity logisticCompany) {
		this.logisticCompany = logisticCompany;
	}
	
	
}
