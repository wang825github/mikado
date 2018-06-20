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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="species")
public class SpeciesEntity {
	private Integer id;
	private String nameEn;//species name
	private String nameCn;//Abbreviation
//	private List<ImportNameEntity> importNames = new ArrayList<ImportNameEntity>();
	private String cropCode;
	private String no;
	private String speciesNameEn;
	private String speciesNameCn;
	private String speciesNameCn2;
	private GroupsEntity group;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name_en")
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@Column(name="name_cn")
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	@Column(name="no")
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(name="species_name_en")
	public String getSpeciesNameEn() {
		return speciesNameEn;
	}
	public void setSpeciesNameEn(String speciesNameEn) {
		this.speciesNameEn = speciesNameEn;
	}
	@Column(name="species_name_cn")
	public String getSpeciesNameCn() {
		return speciesNameCn;
	}
	public void setSpeciesNameCn(String speciesNameCn) {
		this.speciesNameCn = speciesNameCn;
	}
	@Column(name="species_name_cn2")
	public String getSpeciesNameCn2() {
		return speciesNameCn2;
	}
	public void setSpeciesNameCn2(String speciesNameCn2) {
		this.speciesNameCn2 = speciesNameCn2;
	}
	
	@OneToOne(targetEntity = GroupsEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	public GroupsEntity getGroup() {
		return group;
	}
	public void setGroup(GroupsEntity group) {
		this.group = group;
	}
	@Column(name="crop_code")
	public String getCropCode() {
		return cropCode;
	}
	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}
	 
	
}
