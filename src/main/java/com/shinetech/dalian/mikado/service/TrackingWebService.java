package com.shinetech.dalian.mikado.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
@Service
public interface TrackingWebService {
	
	public int saveTrackingWeb(DataManageEntity de);
	public int saveTrackingWebByLotNumbers(String lotNumbers);
	

}
