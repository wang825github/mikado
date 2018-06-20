package com.shinetech.dalian.mikado.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.CustomerDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.DictionaryDao;
import com.shinetech.dalian.mikado.dao.PackagingDao;
import com.shinetech.dalian.mikado.dao.ProductionDao;
import com.shinetech.dalian.mikado.dao.SeedDao;
import com.shinetech.dalian.mikado.dao.StorageDao;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
//import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.MultipartFileChangeFileUtils;

/**
 * 
 * @author abc
 *
 */
@Service
public class PackagingService {
	
	@Autowired
	private PackagingDao packagingDao;
	@Autowired
	private StorageDao storageDao;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private SeedDao seedDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private MakeNumbersDetails makeNumbersDetails;
	@Autowired
	private TrackingWebService trackingWebService;
	@Autowired
	private ProductionDao productionDao;
	
	/**
	 * Get package by search condition
	 * server side pagination function is being user
	 * @param params
	 * @return map with package list and pagination parameters
	 */
	public Map<String, Object> getPackagingByGroup(Map<String, Object> params){
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<PackageEntity> rows = new ArrayList<PackageEntity>();
		String speciesNameCn = (String) params.get("speciesNameCn");
		String packingUnitId = (String) params.get("packingUnitId");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		
		Boolean speciesNameCnFlag = true,packingUnitIdFlag = true;
		if(speciesNameCn == null || "".equals(speciesNameCn.trim()) || "0".equals(speciesNameCn)){
			speciesNameCnFlag = false;
		}
		if(packingUnitId == null || "".equals(packingUnitId.trim()) || "0".equals(packingUnitId)){
			packingUnitIdFlag = false;
		}
		
		/*
		 * Get all storage from DB
		 */
		if (speciesNameCnFlag == false && packingUnitIdFlag == false) {
			total = packagingDao.listStorage(null).size();
			rows = packagingDao.listStorage(params);
		}
		/*
		 * Get storage list by packing unit
		 */
		if (speciesNameCnFlag == false && packingUnitIdFlag == true) {
			total = packagingDao.listStorageByPackingUnit(
					Integer.parseInt(packingUnitId), null, null).size();
			rows = packagingDao.listStorageByPackingUnit(
					Integer.parseInt(packingUnitId), start, maxResult);
		}
		/*
		 * Get storage list by commercial name
		 */
		if (speciesNameCnFlag == true && packingUnitIdFlag == false) {
			total = packagingDao.listStorageBySpeciesName(
					speciesNameCn, null, null).size();
			rows = packagingDao.listStorageBySpeciesName(
					speciesNameCn, start, maxResult);
		}
		/*
		 * Get storage list by commercial and packing unit
		 */
		if (speciesNameCnFlag == true && packingUnitIdFlag == true) {
			total = packagingDao.listStorageBySearchConditions(
					speciesNameCn, Integer.parseInt(packingUnitId),
					null, null).size();
			rows = packagingDao.listStorageBySearchConditions(
					speciesNameCn, Integer.parseInt(packingUnitId),
					start, maxResult);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * Insert productions into DB
	 * @param storage
	 * @throws NoSuchAlgorithmException
	 */
	public void addPackagingsByPackage(PackageEntity packages) throws NoSuchAlgorithmException{
		Double planAmount = packages.getPlanAmount();
//		VarietyEntity variety = dictionaryDao.getVarietyById(packages.getVariety().getId());
		SpeciesEntity species = dictionaryDao.getSpeciesById(packages.getSpecies().getId());
		PackingUnitEntity packingUnit = dictionaryDao.getPackingUnitById(packages.getPackingUnit().getId());
		
		String iDCode = "";
		String qRCode = "";
		
		if(packages.getCurrentAmount() == null){
			packages.setCurrentAmount(0.0);
		}
//		packages.setSurplusAmount(packages.getCurrentAmount());
		packages.setSurplusAmount(packages.getPlanAmount());
		packagingDao.savePackages(packages);
		
		List<PackagingEntity> entityList=new ArrayList<PackagingEntity>();
		String qrcodePrefix=packagingDao.getQRCodePrefix();
		for(int i = 0;i < planAmount;i++){
			
			iDCode = makeNumbersDetails.generateUnitIdentificationCode(species);
			qRCode = qrcodePrefix+ iDCode;
			
			PackagingEntity packaging = new PackagingEntity();
			
			packaging.setLotNumber(packages.getLotNumber());
			packaging.setPlanAmount(1.0);
			packaging.setCurrentAmount(0.0);
			packaging.setSurplusAmount(0.0);
			packaging.setqRCode(qRCode);
			packaging.setIDCode(iDCode);
			packaging.setStorageDay(packages.getStorageDay());
			packaging.setStatus(0);
//			packaging.setVariety(variety);
			packaging.setSpecies(species);
			packaging.setPackingUnit(packingUnit);
			packaging.setPackages(packages);
			
//			packagingDao.savePackaging(packaging);
			entityList.add(packaging);
		}
		baseDao.save(entityList);
		
	}
	
	/**
	 * Update productions information
	 * @param storage
	 */
	public void updatePackagingsByPackages(PackageEntity packages){
		
		//若入库时间有变化，则更新所有产品的入库时间
		//新增或编辑产品的入库时间可能为空，但更新实际数量时入库时间一定不为空
		//新增或编辑产品入库时间不为空，且和更新实际数量时入库时间不相等时
//		PackageEntity packageEntity = packagingDao.getPackagesById(packages.getId());
//		if(packageEntity.getStorageDay() == null  || !packages.getStorageDay().equals(packageEntity.getStorageDay())){
//			packagingDao.setStorageDayOfPackaging(packageEntity,packages.getStorageDay());
//		}
		
//		packages.setSurplusAmount(packages.getCurrentAmount());
//		PackingUnitEntity packingUnit = dictionaryDao.getPackingUnitById(packages.getPackingUnit().getId());
		PackageEntity packageEntity = packagingDao.getPackagesById(packages.getId());
		List<PackagingEntity> packagingList = packagingDao.listPackagingsByPackage(packageEntity);
		//若修改了packingunit,则同时修改 packaging表的packing_unit_id
		if(!packages.getPackingUnit().getId().equals(packagingList.get(0).getPackingUnit().getId())){
			packagingDao.updatePackingUnitIdOfPackaging(packageEntity.getId(),packages.getPackingUnit().getId());
		}
		
		
		packages.setSurplusAmount(packages.getPlanAmount());
		packages.setStorageDay(packages.getStorageDay());
		packagingDao.savePackages(packages);
		
	}
	
	public void deletePackagingsByPackageId(Integer packageId){
		packagingDao.deletePackagingsByPackageId(packageId);
		packagingDao.deletePackagesById(packageId);
	}
	
//	public List<String> getPackageType(){
//		return packagingDao.listPackageType();
//	}
	
	/**
	 * Export QRCode information of productions into txt file
	 * @param storage
	 * @return
	 * @throws IOException
	 */
	public StringBuffer exportTxtData(PackageEntity packages) throws IOException{ 
		
		List<String> QRCodeList = packagingDao.listPackagingQRCodeByPackage(packages);
        
        StringBuffer buff = new StringBuffer();  
        String enter = "\r\n";  
		
		try {
			
			for(String qrcode:QRCodeList){
				buff.append(qrcode);
				buff.append(enter);
			}
			return buff;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
//	/**
//	 * Delivery productions and generate orders
//	 * @param params
//	 * @param uploadFile
//	 * @return
//	 * @throws IOException
//	 */
//	public Map<String, Object> deliveryProductions(Map<String, Object> params,MultipartFile uploadFile) throws IOException {
//		String result = "出库完成。";
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		StorageEntity storage = storageDao.getStorageById((Integer)params.get("storageId"));
//		
//		if((Double)params.get("deliveryAmount") > storage.getSurplusAmount()){
//			result = "出库数量大于剩余数量，出库失败！";
//			returnMap.put("result", result);
//			returnMap.put("dataManage", null);
//			return returnMap;
//		}
//		
//		//计算文件中的 产品的数量（抛去重复的，需要和出库数量相等
//		File upFile = MultipartFileChangeFileUtils.multipartToFile(uploadFile);
//		
//		InputStreamReader read = new InputStreamReader(new FileInputStream(upFile));
//		BufferedReader bufferedReader = new BufferedReader(read);
//		String lineTxt = null;
//		ArrayList<String> ayyarsListText = new ArrayList<String>();
//		while ((lineTxt = bufferedReader.readLine()) != null) {
//			if(lineTxt == null || "".equals(lineTxt.trim())){
//				continue;
//			}
//			ayyarsListText.add(lineTxt);
//		}
//		read.close();
//		bufferedReader.close();
//		
//		ArrayList<String> resultList = new ArrayList<String>();
//		
//		// 去除文件中的重复数据
//		for (String item : ayyarsListText) {
//			if (!resultList.contains(item)) {
//				resultList.add(item);
//			}
//		}
//		
//		if( (Double)params.get("deliveryAmount") != resultList.size()){
//			result = "文件中产品数和出库数量不相等！";
//			returnMap.put("result", result);
//			returnMap.put("dataManage", null);
//			return returnMap;
//		}
//		//检查文件中的产品在产品表中对应的产品存在，若有不存在的，则不做出库操作
//		//检查文件中的产品 都是 入库状态（没有已 出库 的）
//		//获取所有产品表中符合条件产品（seedId，packingUnitId，storageDay，状态为入库）
//		List<ProductionsEntity> productionList = packagingDao.listProductionsByStorageAndStatus(storage,"入库");
//		List<String> qRCodeList = new ArrayList<String>();
//		for(ProductionsEntity entity:productionList){
//			qRCodeList.add(entity.getQRCode());
//		}
//		for(String item:resultList){
//			if(!qRCodeList.contains(item)){
//				result = "有产品不在未出库产品表中·！";
//				returnMap.put("result", result);
//				returnMap.put("dataManage", null);
//				return returnMap;
//			}
//		}
//		
//		storage.setSurplusAmount(storage.getSurplusAmount() - (Double)params.get("deliveryAmount"));
//		storageDao.editStorage(storage);
//		
//		//根据文件中 识别码获取产品列表并修改产品状态
//		List<ProductionsEntity> productions = new ArrayList<ProductionsEntity>();
//		for(ProductionsEntity entity:productionList){
//			if(resultList.contains(entity.getQRCode())){
//				productions.add(entity);
//				entity.setStatus("出库");
//				packagingDao.editProductions(entity);
//			}
//		}
//		
//		// 获取物流信息，客户信息和销售信息
//		LogisticCompanyEntity logisticCompany = dictionaryDao
//				.getLogisticCompanyById((Integer)params.get("logisticId"));
//		CustomerEntity customer = customerDao.getCustomerById((Integer)params.get("customerId"));
//		ImporterEntity importer = dictionaryDao.getImporterById((Integer)params.get("importerId"));
//		SupplierEntity supplier = dictionaryDao.getSupplierById((Integer)params.get("supplierId"));
//
//		DataManageEntity dataManage = new DataManageEntity();
//		
//		dataManage.setLogisticCompany(logisticCompany);
//		dataManage.setCustomer(customer);
//		dataManage.setReceivingTime(null);
//		dataManage.setDeliveryTime((Date)params.get("deliveryTime"));
//		dataManage.setStatus("发货");
//		dataManage.setLotNumbers((String)params.get("lotNumber"));
//		dataManage.setQuantity((Double)params.get("deliveryAmount"));
//		dataManage.setStorage(storage);
//		dataManage.setProductions(productions);
//		dataManage.setOriginalLot((String)params.get("original_lot"));
//		dataManage.setOriginal((String)params.get("original"));
//		dataManage.setPhytoNo((String)params.get("phyto_no"));
//		dataManage.setImportPermitNo((String)params.get("import_permit_no"));
//		dataManage.setGermination((String)params.get("germination"));
//		dataManage.setPurchaseDay((Date)params.get("purchase_day"));
//		dataManage.setTestTime((Date)params.get("test_time"));
//		dataManage.setImporter(importer);
//		dataManage.setSupplier(supplier);
//		dataManageDao.saveDataManage(dataManage);
////		trackingWebService.SaveTrackingWeb(dataManage);
//		
//		returnMap.put("result", result);
//		returnMap.put("dataManage", dataManage);
//		return returnMap;
//		
//	}
	
	/**
	 * Check if storage is associated by other tables
	 * @param storageId
	 * @return true if being associated by other tables,else return false
	 */
	public boolean checkPackagesBeAssociated(Integer packageId) {
		boolean packageFlag = productionDao.listPackagingsByPackageId(packageId);
		if(packageFlag == true){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Edit productions information
	 * @param storage
	 * @throws NoSuchAlgorithmException
	 */
	public void editPackagingsByPackages(PackageEntity packages) throws NoSuchAlgorithmException {
		PackageEntity packageEntity = packagingDao.getPackagesById(packages.getId());
		
		Double planAmount = packages.getPlanAmount();
//		SeedEntity seed = seedDao.getSeedById(storageEntity.getSeed().getId());
//		VarietyEntity variety = dictionaryDao.getVarietyById(packages.getVariety().getId());
		SpeciesEntity species = dictionaryDao.getSpeciesById(packages.getSpecies().getId());
		PackingUnitEntity packingUnit = dictionaryDao.getPackingUnitById(packages.getPackingUnit().getId());
		
		String iDCode = "";
		String qRCode = "";
		
		//若 编辑包装时 作物有变化，则同时更新packages和packaging表记录品种的信息
		if(!packages.getSpecies().getId().equals(packageEntity.getSpecies().getId())){
			packageEntity.setSpecies(species);
			
			packagingDao.updateSpeciesByPackageIdAndSpeciesId(packages.getId(),packages.getSpecies().getId());
		}
		
		//若入库时间有变化，则更新所有产品的入库时间
		//1.编辑前入库时间为空，编辑后入库时间不为空
		//2.编辑前入库时间不为空，编辑后入库时间和编辑前入库时间不相等
		if(packages.getStorageDay() != null && (packageEntity.getStorageDay() == null || !packages.getStorageDay().equals(packageEntity.getStorageDay()))){
			List<PackagingEntity> packagingList = packagingDao.listPackagingsByPackage(packageEntity);
			for(PackagingEntity entity:packagingList){
				entity.setStorageDay(packages.getStorageDay());
				packagingDao.editPackagings(entity);
			}
		}
		
		//若编辑的计划数量大于数据库中的计划数量，则新增产品，使两个计划数量相等
		if(planAmount > packageEntity.getPlanAmount()){
			for(int i = 0;i< planAmount - packageEntity.getPlanAmount();i++){
				iDCode = makeNumbersDetails.generateUnitIdentificationCode(species);
				qRCode = packagingDao.getQRCodePrefix() + iDCode;
				
				PackagingEntity packaging = new PackagingEntity();
				
				packaging.setLotNumber(packages.getLotNumber());
				packaging.setPlanAmount(1.0);
				packaging.setCurrentAmount(0.0);
				packaging.setSurplusAmount(0.0);
				packaging.setqRCode(qRCode);
				packaging.setIDCode(iDCode);
				if(packages.getStorageDay() == null){
					packaging.setStorageDay(null);
				}else{
					packaging.setStorageDay(packages.getStorageDay());
				}
				packaging.setStatus(0);
//				packaging.setVariety(variety);
				packaging.setSpecies(species);
				packaging.setPackingUnit(packingUnit);
				packaging.setPackages(packages);
//				production.setSeed(seed);
				packagingDao.savePackaging(packaging);
			}
		}
		
		if(packages.getPlanAmount() != null){
			packageEntity.setPlanAmount(packages.getPlanAmount());
		}
		if(packages.getCurrentAmount() != null){
			packageEntity.setCurrentAmount(packages.getCurrentAmount());
			packageEntity.setSurplusAmount(packages.getCurrentAmount());
		}
		packageEntity.setPackingUnit(packingUnit);
		if(packages.getStorageDay() == null){
			packageEntity.setStorageDay(null);
		}else{
			packageEntity.setStorageDay(packages.getStorageDay());
		}
		
		packagingDao.savePackages(packageEntity);
		
	}

	
	/**
	 * Get seed list from storage table
	 * @return SeedEntity list
	 */
	public List<SeedEntity> getSeedsFromStorage() {
		
		return packagingDao.listSeedsFromStorage();
	}

	/**
	 * Get packing unit list from storage table
	 * @return PackingUnitEntity list
	 */
	public List<PackingUnitEntity> getPackingUnitsFromStorage() {
		return packagingDao.listPackingUnitsFromStorage();
	}


	/**
	 * Get production inventory data from DB by search conditions
	 * server side pagination function is being used
	 * @param params
	 * @return
	 */
	public Map<String, Object> selectStorageSummary(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<StorageSumView> rows = new ArrayList<StorageSumView>();
		String speciesId = (String) params.get("speciesId");
		String conmercial_name = (String) params.get("conmercial_name");
		String packingUnitId = (String) params.get("packingUnitId");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		Boolean conmercial_nameFlag = true,packingUnitIdFlag = true,speciesIdFlag = true;
		if(conmercial_name == null || "".equals(conmercial_name.trim()) || "0".equals(conmercial_name)){
			conmercial_nameFlag = false;
		}
		if(packingUnitId == null || "".equals(packingUnitId.trim()) || "0".equals(packingUnitId)){
			packingUnitIdFlag = false;
		}
		if(speciesId == null || "".equals(speciesId.trim()) || "0".equals(speciesId)){
			speciesIdFlag = false;
		}
		
		/*
		 * Get all production inventory data
		 */
		if (conmercial_nameFlag == false && packingUnitIdFlag == false && speciesIdFlag == false) {
			total = packagingDao.listStorageSummary(null).size();
			rows = packagingDao.listStorageSummary(params);
		}
		/*
		 * Get production inventory by species id
		 */
		if(conmercial_nameFlag == false && packingUnitIdFlag == false && speciesIdFlag == true){
			total = packagingDao.listStorageSumBySpeciesId(Integer.parseInt(speciesId),null,null).size();
			rows = packagingDao.listStorageSumBySpeciesId(Integer.parseInt(speciesId), start, maxResult);
		}
		
		/*
		 * Get production inventory by packing unit id
		 */
		if (conmercial_nameFlag == false && packingUnitIdFlag == true && speciesIdFlag == false) {
			total = packagingDao.listStorageSumByPackingUnitId(Integer.parseInt(packingUnitId), null, null).size();
			rows = packagingDao.listStorageSumByPackingUnitId(Integer.parseInt(packingUnitId), start, maxResult);
		}
		/*
		 * Get production inventory by packing unit id and species id
		 */
		if (conmercial_nameFlag == false && packingUnitIdFlag == true && speciesIdFlag == true){
			total = packagingDao.listStorageSumByPackingUnitAndSpecies(Integer.parseInt(packingUnitId),Integer.parseInt(speciesId),null,null).size();
			rows = packagingDao.listStorageSumByPackingUnitAndSpecies(Integer.parseInt(packingUnitId), Integer.parseInt(speciesId), start, maxResult);
		}
		/*
		 * Get production inventory by commercial name
		 */
		if (conmercial_nameFlag == true && packingUnitIdFlag == false && speciesIdFlag == false) {
			total = packagingDao.listStorageSumByConmercialName(conmercial_name, null, null).size();
			rows = packagingDao.listStorageSumByConmercialName(conmercial_name, start, maxResult);
		}
		
		
		/*
		 * Get production inventory by commercial name and species id
		 */
		if(conmercial_nameFlag == true && packingUnitIdFlag == false && speciesIdFlag == true){
			total = packagingDao.listStorageSumByConmercialNameAndSpecies(conmercial_name,Integer.parseInt(speciesId),null,null).size();
			rows = packagingDao.listStorageSumByConmercialNameAndSpecies(conmercial_name,Integer.parseInt(speciesId), start, maxResult);
		}
		
		/*
		 * Get production inventory by commercial name and packing unit id
		 */
		if(conmercial_nameFlag == true && packingUnitIdFlag == true && speciesIdFlag == false){
			total = packagingDao.listStorageSumByConmercialNameAndPackingUnit(conmercial_name,Integer.parseInt(packingUnitId),null,null).size();
			rows = packagingDao.listStorageSumByConmercialNameAndPackingUnit(conmercial_name,Integer.parseInt(packingUnitId),start,maxResult);
		}
		
		/*
		 * Get production inventory by commercial name,packing unit id and species id
		 */
		if (conmercial_nameFlag == true && packingUnitIdFlag == true && speciesIdFlag == true) {
			total = packagingDao.listStorageSumBySearchConditions(conmercial_name, Integer.parseInt(packingUnitId),Integer.parseInt(speciesId),null, null).size();
			rows = packagingDao.listStorageSumBySearchConditions(conmercial_name, Integer.parseInt(packingUnitId),Integer.parseInt(speciesId),start, maxResult);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}


	public void saveTrackingWeb(DataManageEntity dataManage) {
		
		trackingWebService.saveTrackingWeb(dataManage);
	}
	
	public String getLotNumber(String code){
		 PackageEntity packageEntity =packagingDao.getLastPackagingLotNumber(code);
		 if(packageEntity==null){
			 return code+"0001";
		 }else{
			 String lastLotNumber=packageEntity.getLotNumber();
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

	public StorageEntity getStorageById(Integer id) {
		
		return packagingDao.getStorageByStorageId(id);
	}

	public PackageEntity getPackagesById(Integer id) {
		
		return packagingDao.getPackagesById(id);
	}

	public List<PackingUnitEntity> getPackingUnitsFromPackages() {
		
		return packagingDao.listPackingUnitsFromPackages();
	}

	public List<PackageEntity> getAllPackages() {
		
		return packagingDao.listPackages();
	}
	public List<PackageEntity> getPackagesBySpecies(Integer speciesID) {
		
		return packagingDao.getPackagesBySpecies(speciesID);
	}

	public Integer getDeliveryPackagingByPackageId(Integer packagesId) {
		
		return packagingDao.getPackagingCountByStatusAndPackages(packagesId, 2);
		
	}
	
}
