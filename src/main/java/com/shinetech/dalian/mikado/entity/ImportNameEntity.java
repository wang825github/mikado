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
@Table(name = "import_name")
public class ImportNameEntity {
	private Integer id;
	private String name;//import name
	private SpeciesEntity species;//species being associated
	
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
	
	@ManyToOne(targetEntity = SpeciesEntity.class,cascade={CascadeType.MERGE,
		CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	public SpeciesEntity getSpecies() {
		return species;
	}
	public void setSpecies(SpeciesEntity species) {
		this.species = species;
	}
	
}
