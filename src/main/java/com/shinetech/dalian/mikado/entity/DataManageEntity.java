package com.shinetech.dalian.mikado.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "data_manage")
public class DataManageEntity {
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
	private List<ProductionsEntity> productions = new ArrayList<ProductionsEntity>(); // production information
	private LogisticCompanyEntity logisticCompany; // logistics company information

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "data_manage_id")
	public List<ProductionsEntity> getProductions() {
		return productions;
	}

	public void setProductions(List<ProductionsEntity> productions) {
		this.productions = productions;
	}

	@ManyToOne(targetEntity = LogisticCompanyEntity.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "logistics_id")
	public LogisticCompanyEntity getLogisticCompany() {
		return logisticCompany;
	}

	public void setLogisticCompany(LogisticCompanyEntity logisticCompany) {
		this.logisticCompany = logisticCompany;
	}

	@ManyToOne(targetEntity = CustomerEntity.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	@Column(name = "odd_numbers")
	public String getOddNumbers() {
		return oddNumbers;
	}

	public void setOddNumbers(String oddNumbers) {
		this.oddNumbers = oddNumbers;
	}

	@Column(name = "receiving_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getReceivingTime() {
		return receivingTime;
	}

	public void setReceivingTime(Date receivingTime) {
		this.receivingTime = receivingTime;
	}

	@Column(name = "delivery_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "lot_numbers")
	public String getLotNumbers() {
		return lotNumbers;
	}

	public void setLotNumbers(String lotNumbers) {
		this.lotNumbers = lotNumbers;
	}


	@ManyToOne(targetEntity = StorageEntity.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "storage_id")
	public StorageEntity getStorage() {
		return storage;
	}

	public void setStorage(StorageEntity storage) {
		this.storage = storage;
	}

	@Column(name="delivery_amount")
	public Double getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	
	@Column(name = "is_sample")
	public Integer getIsSample() {
		return isSample;
	}

	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}
	
	@Column(name = "out_storage_day")
	public Date getOutStorageDay() {
		return outStorageDay;
	}

	public void setOutStorageDay(Date outStorageDay) {
		this.outStorageDay = outStorageDay;
	}

}
