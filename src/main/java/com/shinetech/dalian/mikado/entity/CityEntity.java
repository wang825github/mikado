package com.shinetech.dalian.mikado.entity;

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
@Table(name = "city")
public class CityEntity {
	private Integer id;
	private String nameCn; //city Chinese name
	private String nameEn; // city Abbreviation
	private ProvinceEntity province;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "name_cn",nullable = false,length = 255)
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	
	@Column(name = "name_en",nullable = false,length = 255)
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@ManyToOne(targetEntity = ProvinceEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "province_id")
	public ProvinceEntity getProvince() {
		return province;
	}
	public void setProvince(ProvinceEntity province) {
		this.province = province;
	}
	
}
