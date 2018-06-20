package com.shinetech.dalian.mikado.entity.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storage_view")
public class StorageSumView implements Serializable{

	private static final long serialVersionUID = -5314481933575912627L;
	
	private StorageSumId storageId;
	private Double planAmount;
	private Double currentAmounts;
	private Double surplusAmount;
	
	@Id
	public StorageSumId getStorageId() {
		return storageId;
	}
	public void setStorageId(StorageSumId storageId) {
		this.storageId = storageId;
	}
	@Column(name = "plan_amount")
	public Double getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}
	@Column(name = "current_amount")
	public Double getCurrentAmounts() {
		return currentAmounts;
	}
	public void setCurrentAmounts(Double currentAmounts) {
		if(currentAmounts == null){
			this.currentAmounts = 0.0;
		}
		this.currentAmounts = currentAmounts;
	}
	@Column(name = "surplus_amount")
	public Double getSurplusAmount() {
		return surplusAmount;
	}
	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	
}
