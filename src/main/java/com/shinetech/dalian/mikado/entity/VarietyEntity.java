package com.shinetech.dalian.mikado.entity;

import java.util.Calendar;
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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
@Entity
@Table(name = "variety")
public class VarietyEntity {
	private Integer id;
	private String varietyCode;
	private String commercialName;
	private String varietyName;
	private Date beginDate;
	private SpeciesEntity species;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
 
	@Column(name = "commercial_name")
	public String getCommercialName() {
		return commercialName;
	}
	@Column(name = "variety_code")
	public String getVarietyCode() {
		return varietyCode;
	}

	public void setVarietyCode(String varietyCode) {
		this.varietyCode = varietyCode;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	@Column(name = "variety_name")
	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
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


 
	@Column(name = "begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

 
	/*@Transient
	public String getBeginDateShow() {
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate);
		beginDateShow  = String.valueOf(c.get(Calendar.YEAR))+"/"+String.valueOf(c.get(Calendar.MONTH))+"以前";
		return beginDateShow;
	}
	@Transient
	public void setBeginDateShow(String beginDateShow) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate);
		beginDateShow  = String.valueOf(c.get(Calendar.YEAR))+"/"+String.valueOf(c.get(Calendar.MONTH))+"以前";
		this.beginDateShow = beginDateShow;
	}*/
	
}
