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

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "packaging")
public class PackagingEntity {
	
	private Integer id;
	private String lotNumber;
	private Double planAmount;//plan amount being delivered
	private Double currentAmount;//current amount being delivered
	private Double surplusAmount;//surplus amount being delivered
	private String qRCode;
	private String IDCode;
	private Date storageDay;
	private Integer status;//0:创建 1：入库 2：出库
	
//	private VarietyEntity variety;
	private SpeciesEntity species;
	private PackingUnitEntity packingUnit;//packing unit being associated
	private PackageEntity packages;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "lot_number")
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	@Column(name="plan_amount")
	public Double getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}
	
	@Column(name="current_amount")
	public Double getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	@Column(name="surplus_amount")
	public Double getSurplusAmount() {
		return surplusAmount;
	}
	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	
	@Column(name = "QR_code")
	public String getqRCode() {
		return qRCode;
	}
	public void setqRCode(String qRCode) {
		this.qRCode = qRCode;
	}
	
	@Column(name="ID_code")
	public String getIDCode() {
		return IDCode;
	}
	public void setIDCode(String IDCode) {
		this.IDCode = IDCode;
	}
	
	@Column(name="storage_day")
	public Date getStorageDay() {
		return storageDay;
	}
	public void setStorageDay(Date storageDay) {
		this.storageDay = storageDay;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
//	@ManyToOne(targetEntity = VarietyEntity.class, cascade = {
//		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
//	@JoinColumn(name="variety_id")
//	public VarietyEntity getVariety() {
//		return variety;
//	}
//	public void setVariety(VarietyEntity variety) {
//		this.variety = variety;
//	}
	
	@ManyToOne(targetEntity = PackingUnitEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name="packing_unit_id")
	public PackingUnitEntity getPackingUnit() {
		return packingUnit;
	}
	public void setPackingUnit(PackingUnitEntity packingUnit) {
		this.packingUnit = packingUnit;
	}
	
	@ManyToOne(targetEntity = PackageEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name="packages_id")
	public PackageEntity getPackages() {
		return packages;
	}
	public void setPackages(PackageEntity packages) {
		this.packages = packages;
	}
	
	@ManyToOne(targetEntity = SpeciesEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name="species_id")
	public SpeciesEntity getSpecies() {
		return species;
	}
	public void setSpecies(SpeciesEntity species) {
		this.species = species;
	}
	
}
