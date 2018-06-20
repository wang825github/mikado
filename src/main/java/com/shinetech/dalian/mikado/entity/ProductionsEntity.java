package com.shinetech.dalian.mikado.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="productions")
public class ProductionsEntity {
	private Integer id;
	private String status;//production status入库 ,出库
	private Integer isSample;//是否为样品 0：否 ，1：是
	
	private SeedEntity seed;
	private PackagingEntity packaging;
	private StorageEntity storage;//storage being associated
	private Integer dataManageId;
	
	private Date startDay;//包装开始时间
	private Date endDay;//包装完成时间
	
	private VarietyEntity variety;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	@Column(name = "data_manage_id")
	public Integer getDataManageId() {
		return dataManageId;
	}
	public void setDataManageId(Integer dataManageId) {
		this.dataManageId = dataManageId;
	}
	
	@ManyToOne(targetEntity = SeedEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "seed_id")
	public SeedEntity getSeed() {
		return seed;
	}
	public void setSeed(SeedEntity seed) {
		this.seed = seed;
	}
	
	@ManyToOne(targetEntity = PackagingEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "packaging_id")
	public PackagingEntity getPackaging() {
		return packaging;
	}
	public void setPackaging(PackagingEntity packaging) {
		this.packaging = packaging;
	}
	
	@Column(name = "start_day")
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	
	@Column(name = "end_day")
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	
	@Column(name = "is_sample")
	public Integer getIsSample() {
		return isSample;
	}
	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}
	
	@ManyToOne(targetEntity = VarietyEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "variety_id")
	public VarietyEntity getVariety() {
		return variety;
	}
	public void setVariety(VarietyEntity variety) {
		this.variety = variety;
	}
	
}
