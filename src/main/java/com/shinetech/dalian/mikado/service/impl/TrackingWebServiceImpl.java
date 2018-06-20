package com.shinetech.dalian.mikado.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.shinetech.dalian.mikado.Trancking.entity.SeedDetail;
import com.shinetech.dalian.mikado.basedao.BaseTrackingWebDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.TrackingWebDao;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.TrackingWebService;
import com.shinetech.dalian.mikado.util.IdentificationCodeUtils;
@Service
@Transactional
@Repository
public class TrackingWebServiceImpl implements TrackingWebService{
	
	@Autowired
	BaseTrackingWebDao baseTrackingWebDao;
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private TrackingWebDao trackingWebDao;
	@Autowired
	private LogManageService logService;
	/*  
	 * Synchronize data to  TrackingWebSite On site
	 * @author Justin
	 * @see com.shinetech.dalian.mikado.service.TrackingWebService#SaveTrackingWeb()
	 */
	@Override
	@Transactional 
	public int saveTrackingWeb(DataManageEntity dm) {
		// All data taken from the database
	 
		List<SeedDetail> sdListFromTrankingWeb= trackingWebDao.getSeedDetailByProcessingLot(dm.getLotNumbers());
		List<SeedDetail> sdList =  new ArrayList<SeedDetail>(); 
		List<ProductionsEntity> productions =  dm.getProductions();
		for (int i = 0; i < productions.size(); i++) {
			//If the unit identification number is empty, then end the loop
			//Proceed to the next cycle
			if(StringUtils.isBlank(productions.get(i).getPackaging().getIDCode())) continue;
			int res =  IdentificationCodeUtils.findSameidentificationCode(sdListFromTrankingWeb, productions.get(i).getPackaging().getIDCode());
			SeedDetail sd = new SeedDetail();;
			if(res ==0){
				//If the search is successful, it indicates that there is the unit identification number in the database. Just update it
				sd =  IdentificationCodeUtils.getSameidentificationCode(sdListFromTrankingWeb, productions.get(i).getPackaging().getIDCode());
			}else{
				 sd = new SeedDetail();
			} 
			if(productions.get(i).getSeed() != null){
				sd.setVarietiesName(productions.get(i).getVariety().getCommercialName());//Cultivar name
				if(productions.get(i).getVariety()!=null)
				sd.setCropSpecies(productions.get(i).getVariety().getSpecies().getSpeciesNameCn());//Crop species
			}
			sd.setIdentificationCode(productions.get(i).getPackaging().getIDCode());//Unit identification code
			if(dm.getProductions().get(i).getSeed().getImporter() != null){
				sd.setProductionOperator( dm.getProductions().get(i).getSeed().getImporter().getName()); 
			}
			sd.setProcessingLot(productions.get(i).getSeed().getLotNumber());
			if(dm.getLogisticCompany() !=null){
				sd.setLogisticscompany(dm.getLogisticCompany().getName()); 
			}
			sd.setStartPackingDate(productions.get(i).getStartDay());
			sd.setCompletePackingDate(productions.get(i).getEndDay());
			
			sd.setDiliverytime(dm.getDeliveryTime());
			if(productions.get(i).getStorage()!=null)
			sd.setWarehouseOutDate(productions.get(i).getStorage().getStorageDay());//出库时间
			//If the delivery address of the logistics information is not available, then use this address
		/*	if(dm.getLogisticCompany()  != null){
				if(StringUtils.isNotBlank(dm.getLogisticCompany().getDestination())){
					sd.setDestination(dm.getLogisticCompany().getDestination()); 
				} 
			} */
			//If the delivery address of the logistics information is null, then use the customer's default address
			if(dm.getCustomer()!=null&&StringUtils.isBlank(sd.getDestination())){
				sd.setDestination(dm.getCustomer().getCity().getNameCn()); 
			}
//			identificationCodeFromLocal.add(sd.getIdentificationCode());
			sdList.add(sd);
		}
		try {
			baseTrackingWebDao.saveAll(sdList);
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@Override
	public int saveTrackingWebByLotNumbers(String lotNumbers) {
		DataManageEntity dm = dataManageDao.getDataManageByLotNumbers(lotNumbers);
		return saveTrackingWeb(dm);
	}

	
	
}
