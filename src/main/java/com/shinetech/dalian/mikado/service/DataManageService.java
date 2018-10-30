package com.shinetech.dalian.mikado.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.shinetech.dalian.mikado.dao.CustomerDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.DictionaryDao;
import com.shinetech.dalian.mikado.dao.PackagingDao;
import com.shinetech.dalian.mikado.dao.ProductionDao;
import com.shinetech.dalian.mikado.dao.SeedDao;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.entity.vo.DataManageDto;
import com.shinetech.dalian.mikado.entity.vo.DataManageVO;
import com.shinetech.dalian.mikado.util.FileLogUtils;

/**
 * 
 * @author abc
 *
 */
@Service
public class DataManageService {
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private PackagingDao packagingDao;
	@Autowired
	private SeedDao seedDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private ProductionDao productionDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private FileLogUtils fileLogUtils;
	/**
	 * Get orders from DB by search conditions
	 * The search conditions contain conmercialName,cityId and salesManagerId
	 * If the search conditions are all empty,then get all orders from DB
	 * @param params
	 * @return map which contains the searched orders by search conditions and pagination parameters
	 */
//	@Cacheable(value = "dataManageCache",cacheNames="dataManageCache")
	public Map<String, Object> getDataManageByPagination(Map<String, Object> params){
		Map<String, Object> result = new HashMap<String, Object>();
		List<DataManageEntity> rows = new ArrayList<DataManageEntity>();
//		List<DataManageVO> row2 = new ArrayList<DataManageVO>();
		String conmercialName = (String) params.get("conmercialName");
		String cityId = (String) params.get("cityId");
		String salesManagerId = (String) params.get("salesManagerId");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		Boolean salesManagerIdFlag = true,conmercialNameFlag = true,cityIdFlag = true;
		
		if("0".equals(salesManagerId) || salesManagerId == null || "".equals(salesManagerId.trim())){
			salesManagerIdFlag = false;
		}
		if("0".equals(cityId) || cityId == null || "".equals(cityId)){
			cityIdFlag = false;
		}
		if(conmercialName == null || "".equals(conmercialName.trim())){
			conmercialNameFlag = false;
		}
		
		/*
		 * Get all orders from DB
		 */
		if( salesManagerIdFlag == false && conmercialNameFlag == false && cityIdFlag == false){
			total = dataManageDao.listDataManage(null).size();
			rows = dataManageDao.listDataManage(params);
		}
		/*
		 * Get orders by city id
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == false && cityIdFlag == true){
			total = dataManageDao.listDataManageByCityId(Integer.parseInt(cityId),null,null).size();
			rows = dataManageDao.listDataManageByCityId(Integer.parseInt(cityId),start,maxResult);
		}
		/*
		 * Get orders by commercial name
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == true && cityIdFlag == false){
			total = dataManageDao.listDataManageByConmercialName(conmercialName,null,null).size();
			rows = dataManageDao.listDataManageByConmercialName(conmercialName,start,maxResult);
		}
		/*
		 * Get orders by commercial name and city id
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == true && cityIdFlag == true){
			total = dataManageDao.listDataManageByConmercialNameAndCityId(conmercialName,Integer.parseInt(cityId),null,null).size();
			rows = dataManageDao.listDataManageByConmercialNameAndCityId(conmercialName,Integer.parseInt(cityId),start,maxResult);
		}
		/*
		 * Get orders by sale id
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == false && cityIdFlag == false){
			total = dataManageDao.listDataManageBySalesId(Integer.parseInt(salesManagerId),null,null).size();
			rows = dataManageDao.listDataManageBySalesId(Integer.parseInt(salesManagerId),start,maxResult);
		}
		/*
		 * Get orders by sale id and city id
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == false && cityIdFlag == true){
			total = dataManageDao.listDataManageBySalesIdAndCityId(Integer.parseInt(salesManagerId),Integer.parseInt(cityId),null,null).size();
			rows = dataManageDao.listDataManageBySalesIdAndCityId(Integer.parseInt(salesManagerId),Integer.parseInt(cityId),start,maxResult);
		}
		/*
		 * Get orders by sale id and commercial name
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == true && cityIdFlag == false){
			total = dataManageDao.listDataManageBySalesIdAndConmercialName(Integer.parseInt(salesManagerId),conmercialName,null,null).size();
			rows = dataManageDao.listDataManageBySalesIdAndConmercialName(Integer.parseInt(salesManagerId),conmercialName,start,maxResult);
		}
		/*
		 * Get orders by sale id,commercial name and city id
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == true && cityIdFlag == true){
			total = dataManageDao.listDataManageBySearches(Integer.parseInt(salesManagerId),conmercialName,Integer.parseInt(cityId),null,null).size();
			rows = dataManageDao.listDataManageBySearches(Integer.parseInt(salesManagerId),conmercialName,Integer.parseInt(cityId),start,maxResult);
		}
		
//		for(DataManageEntity entity:rows){
//			row2.add(new DataManageVO(entity));
//		}
//		BeanUtils.copyProperties(dest, orig);
//		rows.stream().map(o ->{BeanUtils.copyProperties(o, new DataManageDto())}).
		List<DataManageDto> rowsDto = rows.stream()
			.map(o ->{
					DataManageDto dto = new DataManageDto();
					try {
						BeanUtils.copyProperties(dto, o ) ;
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					return dto;
				})
				.collect(Collectors.toList());
	
		result.put("total", total);
		result.put("rows", rowsDto);
		
		return result;
	}
	
	
	public DataManageEntity getDataManageById(Integer id){
		return dataManageDao.getDataManageById(id);
	}
	
	/**
	 * Update a orders status,contains '发货' and '已签收'
	 * When update the order status,then also update the status about all the productions in the order
	 * @param dataManage
	 */
//	@CacheEvict(allEntries=true,value = "dataManageCache", cacheNames="dataManageCache")
	public void editStatus(DataManageEntity dataManage) {
		DataManageEntity dataEntity = dataManageDao.getDataManageById(dataManage.getId());
		List<ProductionsEntity> productionsList = dataEntity.getProductions();
		String status = "";
		if("0".equals(dataManage.getStatus())){
			status = "出库";
		}
		
		if("1".equals(dataManage.getStatus())){
			status = "已签收";
		}
		
		/*
		 * Update productions status of the order
		 */
		for(ProductionsEntity entity:productionsList){
			entity.setStatus(status);
			productionDao.editProductions(entity);
		}
		
		dataEntity.setStatus(dataManage.getStatus());
		dataEntity.setReceivingTime(dataManage.getReceivingTime());
		dataEntity.setOddNumbers(dataManage.getOddNumbers());
		dataManageDao.saveDataManage(dataEntity);
		
	}
	
	/**
	 * Export excel function
	 * The export content is based on search conditions:conmercialName,cityId,saleId
	 * The exportType decides which type of report will be exported
	 * @param conmercialName
	 * @param cityId
	 * @param saleId
	 * @param exportType
	 * @return excel workbook
	 */
	public Workbook exportExcel(String conmercialName,Integer cityId,Integer saleId,Integer exportType){
		List<DataManageEntity> dataManageList  = new ArrayList<DataManageEntity>();
		
		//Firstly,get the data from data_manage table which is qualified
		Boolean conmercialNameFlag = true,cityIdFlag = true,salesManagerIdFlag = true;
		if(saleId == 0 || saleId == null){
			salesManagerIdFlag = false;
		}
		if(cityId == 0 || cityId == null){
			cityIdFlag = false;
		}
		if(conmercialName == null || "".equals(conmercialName.trim())){
			conmercialNameFlag = false;
		}
		
		/*
		 * Get all data from DB
		 */
		if( salesManagerIdFlag == false && conmercialNameFlag == false && cityIdFlag == false){
			dataManageList = dataManageDao.listDataManage(null);
		}
		/*
		 * Get all data by city id
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == false && cityIdFlag == true){
			dataManageList = dataManageDao.listDataManageByCityId(cityId, null, null);
		}
		/*
		 * Get all data by commercial name
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == true && cityIdFlag == false){
			dataManageList = dataManageDao.listDataManageByConmercialName(conmercialName, null, null);
		}
		/*
		 * Get all data by commercial name and city id
		 */
		if(salesManagerIdFlag == false && conmercialNameFlag == true && cityIdFlag == true){
			dataManageList = dataManageDao.listDataManageByConmercialNameAndCityId(conmercialName, cityId, null, null);
		}
		/*
		 * Get all data by sales id
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == false && cityIdFlag == false){
			dataManageList = dataManageDao.listDataManageBySalesId(saleId,null,null);
		}
		/*
		 * Get all data by sales id and city id
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == false && cityIdFlag == true){
			dataManageList = dataManageDao.listDataManageBySalesIdAndCityId(saleId, cityId, null, null);
		}
		/*
		 * Get all data by sales id and commercial name
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == true && cityIdFlag == false){
			dataManageList = dataManageDao.listDataManageBySalesIdAndConmercialName(saleId, conmercialName, null, null);
		}
		/*
		 * Get all data by sales id,city id and commercial name
		 */
		if(salesManagerIdFlag == true && conmercialNameFlag == true && cityIdFlag == true){
			dataManageList = dataManageDao.listDataManageBySearches(saleId,conmercialName,cityId,null,null);
		}
		
		//return the excel contained about the searched data
		return exportExcel(dataManageList,exportType);
	}
	
	/**
	 * 
	 * @param dataManageList
	 * @param exportType
	 * @return
	 */
	public Workbook exportExcel(List<DataManageEntity> dataManageList,Integer exportType){
		Workbook wb = new XSSFWorkbook();
		Sheet processingLot,saleInfo,logistics;
		
		/*
		 * Set excel style,about font,color,border,alignment of header and content
		 */
		CellStyle csheader = wb.createCellStyle();
    	CellStyle csvalue = wb.createCellStyle();
    	
    	Font f = wb.createFont();
    	Font f2 = wb.createFont();
    	
    	f.setFontHeightInPoints((short)10);
    	f.setColor(IndexedColors.BLACK.getIndex());
    	f.setBoldweight(Font.BOLDWEIGHT_BOLD);
    	
    	f2.setFontHeightInPoints((short) 10);
    	f2.setColor(IndexedColors.BLACK.getIndex());
    	
    	csheader.setFont(f);
    	csheader.setBorderLeft(CellStyle.BORDER_THIN);
    	csheader.setBorderRight(CellStyle.BORDER_THIN);
    	csheader.setBorderTop(CellStyle.BORDER_THIN);
    	csheader.setBorderBottom(CellStyle.BORDER_THIN);
    	csheader.setAlignment(CellStyle.ALIGN_CENTER);
    	
    	csvalue.setFont(f2);
    	csvalue.setBorderLeft(CellStyle.BORDER_THIN);
    	csvalue.setBorderRight(CellStyle.BORDER_THIN);
    	csvalue.setBorderTop(CellStyle.BORDER_THIN);
    	csvalue.setBorderBottom(CellStyle.BORDER_THIN);
    	csvalue.setAlignment(CellStyle.VERTICAL_CENTER);
    	csvalue.setAlignment(CellStyle.ALIGN_CENTER);
    	
		
    	/*
    	 * Export type:Processing Lot
    	 */
		if(exportType == 1){
			processingLot = (Sheet)wb.createSheet("Processing Lot");
			for(int i=0;i<16;i++){
				processingLot.setColumnWidth((short) i, (short) (35.7*150));
	    	}
			
			Row processingLotRow = (Row)processingLot.createRow(0);
			Cell cell  = processingLotRow.createCell(0);
			cell.setCellValue("Production Lot"); //产品批次
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(1);
			cell.setCellValue("Package Lot");//包装批次
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(2);
			cell.setCellValue("Seed Lot");//种子批次
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(3);
			cell.setCellValue("Delivery Lot");//出库批次
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(4);
			cell.setCellValue("Species");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(5);
			cell.setCellValue("Import Name");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(6);
			cell.setCellValue("Original Lot");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(7);
			cell.setCellValue("Original");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(8);
			cell.setCellValue("Purchase Day");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(9);
			cell.setCellValue("Phyto No.");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(10);
			cell.setCellValue("Import Permit No.");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(11);
			cell.setCellValue("Packing Day");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(12);
			cell.setCellValue("Germination");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(13);
			cell.setCellValue("Test Time");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(14);
			cell.setCellValue("Importer");
			cell.setCellStyle(csheader);
			cell  = processingLotRow.createCell(15);
			cell.setCellValue("Supplier");
			cell.setCellStyle(csheader);
			
			int i = 1;
			for (DataManageEntity entity : dataManageList) {

				String ids = entity.getStorage().getSeedids();
				String packagesIds = entity.getStorage().getPackagesids();
				List<PackageEntity> packageSet = packagingDao
						.listPackagesByIDs(packagesIds);
				List<SeedEntity> seedSet = seedDao.listSeedsByIDs(ids);

				for (SeedEntity seed : seedSet) {
					for (PackageEntity packagess : packageSet) {

						processingLotRow = (Row) processingLot.createRow(i);
						i++;
						processingLotRow.createCell(0).setCellValue(entity.getStorage().getProductionLotNumber());
						processingLotRow.createCell(1).setCellValue(packagess.getLotNumber());
						processingLotRow.createCell(2).setCellValue(seed.getLotNumber());
						processingLotRow.createCell(3).setCellValue(entity.getLotNumbers());

						processingLotRow.createCell(4).setCellValue(seed.getSpecies().getSpeciesNameCn()+ " - "+ seed.getSpecies().getSpeciesNameEn());
						processingLotRow.createCell(5).setCellValue(seed.getImportName());
						processingLotRow.createCell(6).setCellValue(seed.getOriginalLot());
						processingLotRow.createCell(7).setCellValue(seed.getOriginal());
						if (seed.getPurchaseDay() == null) {
							processingLotRow.createCell(8).setCellValue("");
						} else {
							processingLotRow.createCell(8).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(seed.getPurchaseDay()));
						}
						processingLotRow.createCell(9).setCellValue(seed.getPhytoNo());
						processingLotRow.createCell(10).setCellValue(seed.getImportPermitNo());
						if (entity.getDeliveryTime() == null) {
							processingLotRow.createCell(11).setCellValue("");
						} else {
							processingLotRow.createCell(11).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getDeliveryTime()));
						}
						processingLotRow.createCell(12).setCellValue(seed.getGermination());
						if (seed.getTestTime() == null) {
							processingLotRow.createCell(13).setCellValue("");
						} else {
							processingLotRow.createCell(13).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(seed.getTestTime()));
						}
						if (seed.getImporter() == null) {

							processingLotRow.createCell(14).setCellValue("");
						} else {
							processingLotRow.createCell(14).setCellValue(seed.getImporter().getName());

						}
						if (seed.getSupplier() == null) {
							processingLotRow.createCell(15).setCellValue("");
						} else {
							processingLotRow.createCell(15).setCellValue(seed.getSupplier().getName());
						}
						processingLotRow.getCell(0).setCellStyle(csvalue);
						processingLotRow.getCell(1).setCellStyle(csvalue);
						processingLotRow.getCell(2).setCellStyle(csvalue);
						processingLotRow.getCell(3).setCellStyle(csvalue);
						processingLotRow.getCell(4).setCellStyle(csvalue);
						processingLotRow.getCell(5).setCellStyle(csvalue);
						processingLotRow.getCell(6).setCellStyle(csvalue);
						processingLotRow.getCell(7).setCellStyle(csvalue);
						processingLotRow.getCell(8).setCellStyle(csvalue);
						processingLotRow.getCell(9).setCellStyle(csvalue);
						processingLotRow.getCell(10).setCellStyle(csvalue);
						processingLotRow.getCell(11).setCellStyle(csvalue);
						processingLotRow.getCell(12).setCellStyle(csvalue);
						processingLotRow.getCell(13).setCellStyle(csvalue);
						processingLotRow.getCell(14).setCellStyle(csvalue);
						processingLotRow.getCell(15).setCellStyle(csvalue);
					}
				}
				
			}
		}
		/*
		 * Sale Information
		 */
		if(exportType == 2){
			saleInfo = (Sheet)wb.createSheet("Sale Information");
			for(int i=0;i<13;i++){
				saleInfo.setColumnWidth((short) i, (short) (35.7*150));
	    	}
			
			Row saleInfoRow = (Row)saleInfo.createRow(0);
			Cell cell  = saleInfoRow.createCell(0);
			cell.setCellValue("Production Lot");
			cell.setCellStyle(csheader);
			
			//=========
			cell  = saleInfoRow.createCell(1);
			cell.setCellValue("Package Lot");//包装批次
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(2);
			cell.setCellValue("Seed Lot");//种子批次
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(3);
			cell.setCellValue("Delivery Lot");//出库批次
			cell.setCellStyle(csheader);
			
			//=======
			
			cell  = saleInfoRow.createCell(4);
			cell.setCellValue("Sale Manager");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(5);
			cell.setCellValue("Province");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(6);
			cell.setCellValue("Customer");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(7);
			cell.setCellValue("Species");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(8);
			cell.setCellValue("Import Name");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(9);
			cell.setCellValue("Commercial Name ");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(10);
			cell.setCellValue("Packing Unit");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(11);
			cell.setCellValue("Quantity");
			cell.setCellStyle(csheader);
			cell  = saleInfoRow.createCell(12);
			cell.setCellValue("Order Day");
			cell.setCellStyle(csheader);
			
//			processingLot.addMergedRegion(new CellRangeAddress(1, (short)2, 0, (short)0));
			
			int i = 1;
			for(DataManageEntity entity:dataManageList){
				String ids=entity.getStorage().getSeedids();
				String packagesIds = entity.getStorage().getPackagesids();
				
				List<SeedEntity> seedSet =seedDao.listSeedsByIDs(ids);
				List<PackageEntity> packageSet = packagingDao.listPackagesByIDs(packagesIds);
				
				PackingUnitEntity packingUnit = entity.getStorage().getPackages().getPackingUnit();
				
				if(seedSet.size()>1){
					saleInfo.addMergedRegion(new CellRangeAddress(i, i+seedSet.size()-1, 11,11));
				}
				
				for(SeedEntity seed:seedSet){
					if(packageSet.size() > 1){
						saleInfo.addMergedRegion(new CellRangeAddress(i, i+packageSet.size()-1, 11,11));
					}
					for (PackageEntity packagess : packageSet) {
					
						saleInfoRow = (Row)saleInfo.createRow(i);
						i++;
						saleInfoRow.createCell(0).setCellValue(entity.getStorage().getProductionLotNumber());
						saleInfoRow.createCell(1).setCellValue(packagess.getLotNumber());
						saleInfoRow.createCell(2).setCellValue(seed.getLotNumber());
						saleInfoRow.createCell(3).setCellValue(entity.getLotNumbers());
					
						if(entity.getCustomer() == null || entity.getCustomer().getSaleManager() == null){
							saleInfoRow.createCell(4).setCellValue("");
						}else{
							saleInfoRow.createCell(4).setCellValue(entity.getCustomer().getSaleManager().getName());
						}
						saleInfoRow.createCell(5).setCellValue(entity.getCustomer().getCity().getProvince().getNameCn());
						saleInfoRow.createCell(6).setCellValue(entity.getCustomer().getCompany());
						saleInfoRow.createCell(7).setCellValue(seed.getSpecies().getSpeciesNameCn()+ " - " + seed.getSpecies().getSpeciesNameEn());
						saleInfoRow.createCell(8).setCellValue(seed.getImportName());
						saleInfoRow.createCell(9).setCellValue(entity.getStorage().getVariety().getCommercialName());
						saleInfoRow.createCell(10).setCellValue(packingUnit.getSpecifications());
						saleInfoRow.createCell(11).setCellValue(entity.getDeliveryAmount()); //need merge
						if(entity.getReceivingTime() == null){
							saleInfoRow.createCell(12).setCellValue("");
						}else{
							saleInfoRow.createCell(12).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getReceivingTime()));
						}
						
						
						
						saleInfoRow.getCell(0).setCellStyle(csvalue);
						saleInfoRow.getCell(1).setCellStyle(csvalue);
						saleInfoRow.getCell(2).setCellStyle(csvalue);
						saleInfoRow.getCell(3).setCellStyle(csvalue);
						saleInfoRow.getCell(4).setCellStyle(csvalue);
						saleInfoRow.getCell(5).setCellStyle(csvalue);
						saleInfoRow.getCell(6).setCellStyle(csvalue);
						saleInfoRow.getCell(7).setCellStyle(csvalue);
						saleInfoRow.getCell(8).setCellStyle(csvalue);
						saleInfoRow.getCell(9).setCellStyle(csvalue);
						saleInfoRow.getCell(10).setCellStyle(csvalue);
						saleInfoRow.getCell(11).setCellStyle(csvalue);
						saleInfoRow.getCell(12).setCellStyle(csvalue);
					}
				}
			}
		}
		/*
		 * Logistics Information
		 */
		if(exportType ==3){
			logistics =(Sheet) wb.createSheet("Logistics Information");
			for(int i=0;i<13;i++){
				logistics.setColumnWidth((short) i, (short) (35.7*150));
	    	}
			
			Row logisticsRow = (Row)logistics.createRow(0);
			Cell cell  = logisticsRow.createCell(0);
			cell.setCellValue("Production Lot");
			cell.setCellStyle(csheader);
			
			//=========
			cell  = logisticsRow.createCell(1);
			cell.setCellValue("Package Lot");//包装批次
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(2);
			cell.setCellValue("Seed Lot");//种子批次
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(3);
			cell.setCellValue("Delivery Lot");//出库批次
			cell.setCellStyle(csheader);
			
			//=======
			
			cell  = logisticsRow.createCell(4);
			cell.setCellValue("Species");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(5);
			cell.setCellValue("Variety");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(6);
			cell.setCellValue("Packing Unit");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(7);
			cell.setCellValue("Quantity");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(8);
			cell.setCellValue("Logistics Company");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(9);
			cell.setCellValue("Odd Numbers ");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(10);
			cell.setCellValue("Delivery Time");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(11);
			cell.setCellValue("Destination ");
			cell.setCellStyle(csheader);
			cell  = logisticsRow.createCell(12);
			cell.setCellValue("Receiving Time");
			cell.setCellStyle(csheader);
			
			int i = 1;
			for(DataManageEntity entity:dataManageList){
				String ids=entity.getStorage().getSeedids();
				String packagesIds = entity.getStorage().getPackagesids();
				
				List<SeedEntity> seedSet =seedDao.listSeedsByIDs(ids);
				List<PackageEntity> packageSet = packagingDao.listPackagesByIDs(packagesIds);
				PackingUnitEntity packingUnit = entity.getStorage().getPackages().getPackingUnit();
				
				if(seedSet.size()>1){
					logistics.addMergedRegion(new CellRangeAddress(i, i+seedSet.size()-1, 7,7));
				}
				
				for(SeedEntity seed:seedSet){
					
					if(packageSet.size() > 1){
						logistics.addMergedRegion(new CellRangeAddress(i, i+packageSet.size()-1, 7,7));
					}
					
					for (PackageEntity packagess : packageSet) {
					
						logisticsRow = (Row)logistics.createRow(i);
						i++;
						logisticsRow.createCell(0).setCellValue(entity.getStorage().getProductionLotNumber());
						logisticsRow.createCell(1).setCellValue(packagess.getLotNumber());
						logisticsRow.createCell(2).setCellValue(seed.getLotNumber());
						logisticsRow.createCell(3).setCellValue(entity.getLotNumbers());
						logisticsRow.createCell(4).setCellValue(seed.getSpecies().getSpeciesNameCn() + " - " + seed.getSpecies().getSpeciesNameEn());
						logisticsRow.createCell(5).setCellValue(entity.getStorage().getVariety().getCommercialName());
						logisticsRow.createCell(6).setCellValue(packingUnit.getSpecifications());
						logisticsRow.createCell(7).setCellValue(entity.getDeliveryAmount());
						logisticsRow.createCell(8).setCellValue(entity.getLogisticCompany().getName());
						logisticsRow.createCell(9).setCellValue(entity.getOddNumbers());
						if(entity.getDeliveryTime() == null){
							logisticsRow.createCell(10).setCellValue("");
						}else{
							logisticsRow.createCell(10).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getDeliveryTime()));
						}
						logisticsRow.createCell(11).setCellValue(entity.getCustomer().getCity().getProvince().getNameCn());
						if(entity.getReceivingTime() == null){
							logisticsRow.createCell(12).setCellValue("");
						}else{
							logisticsRow.createCell(12).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getReceivingTime()));
						}
						logisticsRow.getCell(0).setCellStyle(csvalue);
						logisticsRow.getCell(1).setCellStyle(csvalue);
						logisticsRow.getCell(2).setCellStyle(csvalue);
						logisticsRow.getCell(3).setCellStyle(csvalue);
						logisticsRow.getCell(4).setCellStyle(csvalue);
						logisticsRow.getCell(5).setCellStyle(csvalue);
						logisticsRow.getCell(6).setCellStyle(csvalue);
						logisticsRow.getCell(7).setCellStyle(csvalue);
						logisticsRow.getCell(8).setCellStyle(csvalue);
						logisticsRow.getCell(9).setCellStyle(csvalue);
						logisticsRow.getCell(10).setCellStyle(csvalue);
						logisticsRow.getCell(11).setCellStyle(csvalue);
						logisticsRow.getCell(12).setCellStyle(csvalue);
					}
				}
			}
		}
		
		return wb;
		
	}
//	@CacheEvict(allEntries=true,value = "dataManageCache",cacheNames="dataManageCache")
	public void editDataManage(DataManageEntity dataManage) {
		DataManageEntity dataManageEntity = dataManageDao.getDataManageById(dataManage.getId());
		CustomerEntity customer = customerDao.getCustomerById(dataManage.getCustomer().getId());
		LogisticCompanyEntity logisticCompany = dictionaryDao.getLogisticCompanyById(dataManage.getLogisticCompany().getId());
		
		dataManageEntity.setCustomer(customer);
		dataManageEntity.setLogisticCompany(logisticCompany);
		
		dataManageDao.editDataManage(dataManageEntity);
	}
	
	public VarietyEntity getVariety(Integer id){
		VarietyEntity variety = dictionaryDao.getVarietyById(id);
		return variety;
	}
	
	public String checkLotNumber(String code) {
		return "";
	}
	public String getLotNumber(String code){
		StopWatch watch=new  StopWatch();
		fileLogUtils.writeLog("-DataManageService.getLotNumber开始-"," ");
		watch.start("dataManageDao.getLastOrderLotNumber(code) :"+code);
//		DataManageEntity dataManageEntity =dataManageDao.getLastOrderLotNumber(code);
		DataManageEntity dataManageEntity =dataManageDao.getLastOrderLotNumberBySql(code);
		
		watch.stop();
		String res = watch.prettyPrint();
		fileLogUtils.writeLog(res," ");
		fileLogUtils.writeLog("-DataManageService.getLotNumber结束-"," ");
		 if(dataManageEntity==null){
			 return code+"0001";
		 }else{
			 String lastLotNumber=dataManageEntity.getLotNumbers();
			 String index=lastLotNumber.substring(5);
			 Integer intIndex=Integer.parseInt(index);
			 intIndex++;
			 index=intIndex.toString();
			 int length=index.length();
			 for(int i=0;i<4-length;i++){
				 index="0"+index;
			 }
			 return code+index;
		 }
	}
}
