package com.shinetech.dalian.mikado.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.Trancking.entity.SeedDetail;
import com.shinetech.dalian.mikado.basedao.BaseTrackingWebDao;
import com.shinetech.dalian.mikado.dao.TrackingWebDao;
@Service
@Repository
@Transactional
public class TrackingWebDaoIpml implements TrackingWebDao{
	@Autowired
	BaseTrackingWebDao baseTrackingWebDao;
	@Override
	public List<SeedDetail> getSeedDetailByProcessingLot(String processingLot) {
		String hql ="FROM SeedDetail SD WHERE SD.processingLot =  '"+processingLot+"'";
		return baseTrackingWebDao.execute(hql);
	}

	@Override
	public SeedDetail getSeedDetailByIdentificationCode(
			String identificationCode) {
		String hql ="FROM SeedDetail SD.identificationCode = '"+identificationCode+"'";
		return baseTrackingWebDao.executeGetFirst(hql);
	}

	@Override
	public int delTrackingWeb(String identificationCode) {
		String hql ="Delete  SeedDetail SD WHERE SD.identificationCode = '"+identificationCode+"'";
		baseTrackingWebDao.executeUpdate(hql);
		return 0;
	}

 

}
