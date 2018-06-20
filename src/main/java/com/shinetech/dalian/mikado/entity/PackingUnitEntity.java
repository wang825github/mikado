package com.shinetech.dalian.mikado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "packing_unit")
public class PackingUnitEntity {
	private Integer id;
	private String specifications;// 袋，罐。箱
	private String specificationCode;
	private String specificationName;
	private String unit;
	private String length;
	private String width;
	private String height;
	private String purpose;
	private Float weight;
	private Integer sample;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "specifications")
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
 

 
	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}
	@Column(name = "specifications_code")
	public String getSpecificationCode() {
		return specificationCode;
	}
	public void setSpecificationCode(String specificationCode) {
		this.specificationCode = specificationCode;
	}
	@Column(name = "specifications_name")
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "length")
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Column(name = "width")
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Column(name = "height")
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@Column(name = "purpose")
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Column(name = "weight")
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	@Column(name = "sample")
	public Integer getSample() {
		return sample;
	}
	public void setSample(Integer sample) {
		this.sample = sample;
	}
 

	
}
