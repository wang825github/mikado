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
@Table(name = "storage")
public class StorageEntity {
	private Integer id;
	private Double quantity;
	private Double surplusQuantity;
	private Date storageDay;
	private Date startDay;//包装开始时间
	private Date endDay;//包装完成时间
	private Integer isSample;//是否样品
	
	private String seedLotNumber;
	private String packageLotNumber;
	private String productionLotNumber;
	
	private PackageEntity packages;
	private SeedEntity seed;
	private VarietyEntity variety;
	
	private String seedids;
	private String packagesids;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "storage_day")
	public Date getStorageDay() {
		return storageDay;
	}
	public void setStorageDay(Date storageDay) {
		this.storageDay = storageDay;
	}
	
	
	@Column(name="quantity")
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	
	@Column(name="surplus_quantity")
	public Double getSurplusQuantity() {
		return surplusQuantity;
	}
	public void setSurplusQuantity(Double surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
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
	
	@Column(name = "seed_lot_number")
	public String getSeedLotNumber() {
		return seedLotNumber;
	}
	public void setSeedLotNumber(String seedLotNumber) {
		this.seedLotNumber = seedLotNumber;
	}
	
	@Column(name = "package_lot_number")
	public String getPackageLotNumber() {
		return packageLotNumber;
	}
	public void setPackageLotNumber(String packageLotNumber) {
		this.packageLotNumber = packageLotNumber;
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
	
	@ManyToOne(targetEntity = SeedEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name="seed_id")
	public SeedEntity getSeed() {
		return seed;
	}
	public void setSeed(SeedEntity seed) {
		this.seed = seed;
	}
	
	@Column(name = "seedids")
	public String getSeedids() {
		return seedids;
	}
	public void setSeedids(String seedids) {
		this.seedids = seedids;
	}
	
	@Column(name = "packagesids")
	public String getPackagesids() {
		return packagesids;
	}
	public void setPackagesids(String packagesids) {
		this.packagesids = packagesids;
	}
	
	@Column(name = "production_lot_number")
	public String getProductionLotNumber() {
		return productionLotNumber;
	}
	public void setProductionLotNumber(String productionLotNumber) {
		this.productionLotNumber = productionLotNumber;
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
