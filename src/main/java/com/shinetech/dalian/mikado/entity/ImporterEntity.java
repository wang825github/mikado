package com.shinetech.dalian.mikado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "importer")
public class ImporterEntity {
	private Integer id;
	private String name;//import name
	private String simpleName;//Abbreviation
	private String importerCode;//Abbreviation
	private String zip;//Abbreviation
	private String address;//Abbreviation
	private String tel;//Abbreviation
	private String fax;//Abbreviation
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name",nullable = false,length = 255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "simple_name",nullable = false,length = 255)
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	@Column(name="zip")
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="tel")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name="fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name="importer_code")
	public String getImporterCode() {
		return importerCode;
	}
	public void setImporterCode(String importerCode) {
		this.importerCode = importerCode;
	}
	
}
