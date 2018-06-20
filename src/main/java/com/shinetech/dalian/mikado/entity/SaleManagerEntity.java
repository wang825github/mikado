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
@Table(name = "sale_manager")
public class SaleManagerEntity {
	private Integer id;
	private String name;//sale manager name
	private String SMCode;//sale manager name
	private AreaEntity area;//area the sale manager belongs to
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name",nullable = false,length = 255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(targetEntity = AreaEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id")
	public AreaEntity getArea() {
		return area;
	}
	public void setArea(AreaEntity area) {
		this.area = area;
	}
	@Column(name = "sm_code",nullable = false,length = 255)
	public String getSMCode() {
		return SMCode;
	}
	public void setSMCode(String sMCode) {
		SMCode = sMCode;
	}
	
}
