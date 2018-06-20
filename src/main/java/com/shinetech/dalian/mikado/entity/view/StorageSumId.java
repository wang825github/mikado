package com.shinetech.dalian.mikado.entity.view;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class StorageSumId implements Serializable{

	private static final long serialVersionUID = 1879250407558650979L;
	private Integer species_id;
	private Integer packingUnit_id;
	private String conmercial_name;
	private String name_en;
	private String name_cn;
	private String specifications;
	
	public Integer getspecies_id() {
		return species_id;
	}
	public void setspecies_id(Integer species_id) {
		this.species_id = species_id;
	}
	public Integer getPackingUnit_id() {
		return packingUnit_id;
	}
	public void setPackingUnit_id(Integer packingUnit_id) {
		this.packingUnit_id = packingUnit_id;
	}
	
	public String getConmercial_name() {
		return conmercial_name;
	}
	public void setConmercial_name(String conmercial_name) {
		this.conmercial_name = conmercial_name;
	}
	
	
	public String getName_en() {
		return name_en;
	}
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}
	public String getName_cn() {
		return name_cn;
	}
	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((species_id == null) ? 0 : species_id.hashCode());
		result = prime * result + ((conmercial_name == null) ? 0 : conmercial_name.hashCode());
		result = prime * result + ((packingUnit_id == null) ? 0 : packingUnit_id.hashCode());
		result = prime * result + ((name_en == null) ? 0 : name_en.hashCode());
		result = prime * result + ((name_cn == null) ? 0 : name_cn.hashCode());
		result = prime * result + ((specifications == null) ? 0 : specifications.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageSumId other = (StorageSumId) obj;
		if (species_id == null) {
			if (other.species_id != null){
				return false;
			}
		} else if (!species_id.equals(other.species_id)){
			return false;
		}
		
		if (conmercial_name == null) {
			if (other.conmercial_name != null){
				return false;
			}
		} else if (!conmercial_name.equals(other.conmercial_name)){
			return false;
		}
		
		if (packingUnit_id == null) {
			if (other.packingUnit_id != null){
				return false;
			}
		} else if (!packingUnit_id.equals(other.packingUnit_id)){
			return false;
		}
		if (name_en == null) {
			if (other.name_en != null){
				return false;
			}
		} else if (!name_en.equals(other.name_en)){
			return false;
		}
		if (name_cn == null) {
			if (other.name_cn != null){
				return false;
			}
		} else if (!name_cn.equals(other.name_cn)){
			return false;
		}
		if (specifications == null) {
			if (other.specifications != null){
				return false;
			}
		} else if (!specifications.equals(other.specifications)){
			return false;
		}
		
		return true;
	}
}
