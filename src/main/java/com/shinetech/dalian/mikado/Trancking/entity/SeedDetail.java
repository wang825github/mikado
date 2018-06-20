package com.shinetech.dalian.mikado.Trancking.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Synchronize to the entity classes on Trancking Web
 * @author 82578
 *
 */
@Entity
@Table(name="seed_detail")
public class SeedDetail {
	private Integer id;
	private String varietiesName;//
	private String identificationCode;
	private String productionOperator;//生产经营者或进口商名称 DataManageEntity -- ImporterEntity--name
	private String cropSpecies ;
	private String processingLot ;//加工批次
	private String logisticscompany ;	//物流公司
	private Date diliverytime;//发货时间
	private Date startPackingDate;//
	private Date completePackingDate;
	private Date warehouseOutDate;
	private String destination ;//目的地
	private String conmercialName ;// 
	
	@Transient
	private String showDiliverytime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "varieties_name")
	public String getVarietiesName() {
		return varietiesName;
	}
	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}
	@Column(name = "identification_code")
	public String getIdentificationCode() {
		return identificationCode;
	}
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	@Column(name = "production_operator")
	public String getProductionOperator() {
		return productionOperator;
	}
	public void setProductionOperator(String productionOperator) {
		this.productionOperator = productionOperator;
	}
	@Column(name = "processing_lot")
	public String getProcessingLot() {
		return processingLot;
	}
	public void setProcessingLot(String processingLot) {
		this.processingLot = processingLot;
	}
	 
	@Column(name = "logistics_company")
	public String getLogisticscompany() {
		return logisticscompany;
	}
	public void setLogisticscompany(String logisticscompany) {
		this.logisticscompany = logisticscompany;
	}
	@Column(name = "dilivery_time")
	public Date getDiliverytime() {
		return diliverytime;
	}
	public void setDiliverytime(Date diliverytime) {
		this.diliverytime = diliverytime;
	}
	@Column(name = "destination")
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Column(name = "crop_species")
	public String getCropSpecies() {
		return cropSpecies;
	}
	public void setCropSpecies(String cropSpecies) {
		this.cropSpecies = cropSpecies;
	}
 
	@Transient
	public String getShowDiliverytime() {
		String show = new SimpleDateFormat("yyyy年MM月dd日").format(diliverytime);
		return show;
	}
	public void setShowDiliverytime(String showDiliverytime) {
		this.showDiliverytime = showDiliverytime;
	}
	@Column(name = "start_packing_date")
	public Date getStartPackingDate() {
		return startPackingDate;
	}
	public void setStartPackingDate(Date startPackingDate) {
		this.startPackingDate = startPackingDate;
	}
	@Column(name = "complete_packing_date")
	public Date getCompletePackingDate() {
		return completePackingDate;
	}
	public void setCompletePackingDate(Date completePackingDate) {
		this.completePackingDate = completePackingDate;
	}
	@Column(name = "warehouse_out_date")
	public Date getWarehouseOutDate() {
		return warehouseOutDate;
	}
	public void setWarehouseOutDate(Date warehouseOutDate) {
		this.warehouseOutDate = warehouseOutDate;
	}
	@Column(name = "conmercial_name")
	public String getConmercialName() {
		return conmercialName;
	}
	public void setConmercialName(String conmercialName) {
		this.conmercialName = conmercialName;
	}
 
	
	
}
