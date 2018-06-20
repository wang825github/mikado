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
@Table(name="packages")
public class PackageEntity {
	private Integer id;
	private Double planAmount;//plan amount being delivered 计划数量
	private Double currentAmount;//current amount being delivered 实际数量
	private Double surplusAmount;//surplus amount being delivered 剩余数量
	private Date storageDay;
	private String lotNumber;
	private Integer storageId;
	
//	private VarietyEntity variety;
	private SpeciesEntity species;
	private PackingUnitEntity packingUnit;//packing unit being associated
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	@Column(name="storage_day")
	public Date getStorageDay() {
		return storageDay;
	}
	public void setStorageDay(Date storageDay) {
		this.storageDay = storageDay;
	}
	
	@Column(name="lot_number")
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
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
	
	@Column(name="storage_id")
	public Integer getStorageId() {
		return storageId;
	}
	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
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
