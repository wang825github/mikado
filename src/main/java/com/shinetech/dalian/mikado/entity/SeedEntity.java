package com.shinetech.dalian.mikado.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seed")
public class SeedEntity {
	private Integer id;
	private String importNumber;// import number of the seed
	private String lotNumber;
	private Double weight;
	private Double surplusWeight;
	private Date purchaseDay;
	private Date storageDay;
	private Date testTime;
	private String originalLot;
	private String original;
	private String germination;
	private String importPermitNo;
	private String phytoNo;
	private Double thousandGrainWeight;//千粒重
	private Integer storageId;
	
	private String importName;
//	private ImportNameEntity importName; // import name of the seed
//	private VarietyEntity variety;
	private SpeciesEntity species;
	private ImporterEntity importer;
	private SupplierEntity supplier;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


//	@ManyToOne(targetEntity = ImportNameEntity.class, cascade = {
//			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
//	@JoinColumn(name = "import_name_id")
//	public ImportNameEntity getImportName() {
//		return importName;
//	}
//
//	public void setImportName(ImportNameEntity importName) {
//		this.importName = importName;
//	}


	@Column(name = "import_number")
	public String getImportNumber() {
		return importNumber;
	}
	
	public void setImportNumber(String importNumber) {
		this.importNumber = importNumber;
	}
	
	@Column(name="lot_number")
	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	@Column(name="weight")
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Column(name="surplus_weight")
	public Double getSurplusWeight() {

		return surplusWeight;
	}

	public void setSurplusWeight(Double surplusWeight) {
		this.surplusWeight = surplusWeight;
	}
	
	@Column(name="purchase_day")
	public Date getPurchaseDay() {
		return purchaseDay;
	}

	public void setPurchaseDay(Date purchaseDay) {
		this.purchaseDay = purchaseDay;
	}
	
	@Column(name="storage_day")
	public Date getStorageDay() {
		return storageDay;
	}

	public void setStorageDay(Date storageDay) {
		this.storageDay = storageDay;
	}
	
	@Column(name="test_time")
	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	
	@Column(name="original_lot")
	public String getOriginalLot() {
		return originalLot;
	}

	public void setOriginalLot(String originalLot) {
		this.originalLot = originalLot;
	}
	
	@Column(name="original")
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
	
	@Column(name="germination")
	public String getGermination() {
		return germination;
	}

	public void setGermination(String germination) {
		this.germination = germination;
	}
	
	@Column(name="import_permit_no")
	public String getImportPermitNo() {
		return importPermitNo;
	}

	public void setImportPermitNo(String importPermitNo) {
		this.importPermitNo = importPermitNo;
	}
	
	@Column(name="phyto_no")
	public String getPhytoNo() {
		return phytoNo;
	}

	public void setPhytoNo(String phytoNo) {
		this.phytoNo = phytoNo;
	}
	
//	@ManyToOne(targetEntity = VarietyEntity.class, cascade = {
//		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
//	@JoinColumn(name = "variety_id")
//	public VarietyEntity getVariety() {
//		return variety;
//	}
//
//	public void setVariety(VarietyEntity variety) {
//		this.variety = variety;
//	}

	@ManyToOne(targetEntity = ImporterEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "importer_id")
	public ImporterEntity getImporter() {
		return importer;
	}

	public void setImporter(ImporterEntity importer) {
		this.importer = importer;
	}

	@ManyToOne(targetEntity = SupplierEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id")
	public SupplierEntity getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierEntity supplier) {
		this.supplier = supplier;
	}
	
	@Column(name = "thousand_grain_weight")
	public Double getThousandGrainWeight() {
		if(thousandGrainWeight == null )
			thousandGrainWeight = 0.00;
		return thousandGrainWeight;
	}

	public void setThousandGrainWeight(Double thousandGrainWeight) {
		this.thousandGrainWeight = thousandGrainWeight;
	}
	
	@Column(name = "storage_id")
	public Integer getStorageId() {
		return storageId;
	}

	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
	}
	
	@Column(name = "import_name")
	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}
	
	@ManyToOne(targetEntity = SpeciesEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	public SpeciesEntity getSpecies() {
		return species;
	}

	public void setSpecies(SpeciesEntity species) {
		this.species = species;
	}

}
