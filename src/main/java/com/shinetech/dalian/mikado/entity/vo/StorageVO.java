package com.shinetech.dalian.mikado.entity.vo;

import java.util.Date;

import org.aspectj.weaver.reflect.IReflectionWorld;

import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;

public class StorageVO{
	private Integer id;
	private Double quantity;
	private Double surplusQuantity;
	private Date storageDay;
	private Date startDay;//包装开始时间
	private Date endDay;//包装完成时间
	private Integer isSample;//是否样品
	private SeedEntity seed;
	private PackageEntity packages;
	
	
	
	public StorageVO() {
		super();
	}

	public StorageVO(StorageEntity entity){
		this.setId(entity.getId());
		this.setQuantity(entity.getQuantity());
		this.setSurplusQuantity(entity.getSurplusQuantity());
		this.setStorageDay(entity.getStorageDay());
		this.setStartDay(entity.getStartDay());
		this.setEndDay(entity.getEndDay());
		this.setIsSample(entity.getIsSample());
//		
////		if(entity.getSeedlist() == null || entity.getSeedlist().size() <1){
//		if(entity.getSeedlist() == null || entity.getSeedlist().size() <1){
//			this.setSeed(null);
//		}else{
////			this.setSeed(entity.getSeedlist().get(0));
//			this.setSeed(entity.getSeedlist().get(0));
//		}
////		if(entity.getPackages() == null || entity.getPackages().size() <1){
//		if(entity.getPackages() == null || entity.getPackages().size() <1){
//			this.setPackages(null);
//		}else{
////			this.setPackages(entity.getPackages().get(0));
//			this.setPackages(entity.getPackages().iterator().next());
//		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getSurplusQuantity() {
		return surplusQuantity;
	}

	public void setSurplusQuantity(Double surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
	}

	public Date getStorageDay() {
		return storageDay;
	}

	public void setStorageDay(Date storageDay) {
		this.storageDay = storageDay;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public Integer getIsSample() {
		return isSample;
	}

	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}

	public SeedEntity getSeed() {
		return seed;
	}

	public void setSeed(SeedEntity seed) {
		this.seed = seed;
	}

	public PackageEntity getPackages() {
		return packages;
	}

	public void setPackages(PackageEntity packages) {
		this.packages = packages;
	}

	
	
}
