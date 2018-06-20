package com.shinetech.dalian.mikado.dao;

import java.util.List;

import com.shinetech.dalian.mikado.Trancking.entity.SeedDetail;
import com.shinetech.dalian.mikado.entity.DataManageEntity;

public interface TrackingWebDao {

	List<SeedDetail> getSeedDetailByProcessingLot(String processingLot);
	
	SeedDetail getSeedDetailByIdentificationCode(String identificationCode);
	
	int delTrackingWeb(String identificationCode);
}
