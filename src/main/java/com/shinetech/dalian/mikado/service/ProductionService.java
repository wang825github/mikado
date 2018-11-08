package com.shinetech.dalian.mikado.service;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
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
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.entity.view.StorageSumView;
import com.shinetech.dalian.mikado.entity.vo.StorageVO;
import com.shinetech.dalian.mikado.util.FileLogUtils;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.MultipartFileChangeFileUtils;

@Service
public class ProductionService {
	static Log  logger = LogFactory.getLog(ProductionService.class);
	@Autowired
	private PackagingDao packagingDao;
	@Autowired
	private StorageDao storageDao;
	@Autowired
	private SeedDao seedDao;
	@Autowired
	private BaseDao baseDao;
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
	@Autowired
	private FileLogUtils fileLogUtils;
//	public PackagingEntity getPackagingById(Integer id){
//		return packagingDao.getPackagingById(id);
//	}
	
	/**
	 * Get storage by search condition
	 * server side pagination function is being user
	 * @param params
	 * @return map with storage list and pagination parameters
	 */
	public Map<String, Object> getStorageByGroup(Map<String, Object> params){
		Map<String, Object> result = new HashMap<String, Object>();
		List<StorageEntity> rows = new ArrayList<StorageEntity>();
//		List<StorageVO> rows2 = new ArrayList<StorageVO>();
		String conmercialName = (String) params.get("conmercialName");
		String packingUnitId = (String) params.get("packingUnitId");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		Boolean conmercialNameFlag = true,packingUnitIdFlag = true;
		if(conmercialName == null || "".equals(conmercialName.trim()) || "0".equals(conmercialName)){
			conmercialNameFlag = false;
		}
		if(packingUnitId == null || "".equals(packingUnitId.trim()) || "0".equals(packingUnitId)){
			packingUnitIdFlag = false;
		}
		
		/*
		 * Get all storage from DB
		 */
		if (conmercialNameFlag == false && packingUnitIdFlag == false) {
			total = productionDao.listStorage(null).size();
			rows = productionDao.listStorage(params);
		}
		/*
		 * Get storage list by packing unit
		 */
		if (conmercialNameFlag == false && packingUnitIdFlag == true) {
			total = productionDao.listStorageByPackingUnit(
					Integer.parseInt(packingUnitId), null, null).size();
			rows = productionDao.listStorageByPackingUnit(
					Integer.parseInt(packingUnitId), start, maxResult);
		}
		/*
		 * Get storage list by commercial name
		 */
		if (conmercialNameFlag == true && packingUnitIdFlag == false) {
			total = productionDao.listStorageByConmercialName(
					conmercialName, null, null).size();
			rows = productionDao.listStorageByConmercialName(
					conmercialName, start, maxResult);
		}
		/*
		 * Get storage list by commercial and packing unit
		 */
		if (conmercialNameFlag == true && packingUnitIdFlag == true) {
			total = productionDao.listStorageBySearchConditions(
					conmercialName, Integer.parseInt(packingUnitId),
					null, null).size();
			rows = productionDao.listStorageBySearchConditions(
					conmercialName, Integer.parseInt(packingUnitId),
					start, maxResult);
		}
		
//		for(StorageEntity entity:rows){
//			rows2.add(new StorageVO(entity));
//		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	public List<SeedEntity> getSeedList(List<Integer> seedIdList){
		List<SeedEntity> lists = new ArrayList<SeedEntity>();
		for(Integer id:seedIdList){
			lists.add(seedDao.getSeedById(id));
		}
		return lists;
	}
	
	public List<PackageEntity> getPackagesList(List<Integer> packagesIdList){
		List<PackageEntity> lists = new ArrayList<PackageEntity>();
		for(Integer id:packagesIdList){
			lists.add(packagingDao.getPackagesById(id));
		}
		return lists;
	}
	
	public List<PackagingEntity> getPackagingByPackagesIdAndAmountAndStatus(List<Integer> seedIdList,List<Integer> packageIdlist,List<Integer> packagingAmountList,Integer status){
		List<PackagingEntity> lists = new ArrayList<PackagingEntity>();
		if(seedIdList.size() == 1){
			for(int i = 0;i<packageIdlist.size();i++){
				lists.addAll(packagingDao.listPackagingByPackagesIdAndAmountAndStatus(packageIdlist.get(i),packagingAmountList.get(i),status));
			}
		}
		if(seedIdList.size() > 1){
			lists = packagingDao.listPackagingByPackagesIdAndAmountAndStatus(packageIdlist.get(0), packagingAmountList.get(0), status);
		}
		return lists;
	}
	
	public void countSeedWeightPackagesAmount(List<SeedEntity> seedList,List<PackageEntity> packagesList,List<Integer> packagingAmountList){
		if(seedList.size() == 1){
			for(int i = 0;i<packagesList.size();i++){
				//包装
				int packagingAmount = packagingAmountList.get(i); 
				packagesList.get(i).setSurplusAmount(packagesList.get(i).getSurplusAmount() - packagingAmount);
				packagingDao.savePackages(packagesList.get(i));
				
				//种子
				double packingUnitWeight = packagesList.get(i).getPackingUnit().getWeight();
				String packingUnit = packagesList.get(i).getPackingUnit().getUnit();
				double seedWeight = 0D;
				
				if("g".equalsIgnoreCase(packingUnit)){
					seedWeight = (packingUnitWeight * packagingAmount)/1000;
				}
				
				if("KG".equalsIgnoreCase(packingUnit)){
					seedWeight = packingUnitWeight * packagingAmount;
				}
				
				if("s".equalsIgnoreCase(packingUnit)){
					seedWeight = ((packingUnitWeight * packagingAmount) * (seedList.get(0).getThousandGrainWeight()/1000))/1000;
				}
				if("ks".equalsIgnoreCase(packingUnit)){
					seedWeight = ((packingUnitWeight * packagingAmount) * seedList.get(0).getThousandGrainWeight())/1000;
				}
				seedList.get(0).setSurplusWeight(seedList.get(0).getSurplusWeight() - seedWeight);
				seedDao.saveSeed(seedList.get(0));
			}
		}
		
		if(seedList.size() > 1){
			//包装
			int packagingAmount = packagingAmountList.get(0);
			packagesList.get(0).setSurplusAmount(packagesList.get(0).getSurplusAmount() - packagingAmount);
			packagingDao.savePackages(packagesList.get(0));
			
			//种子
			double packingUnitWeight = packagesList.get(0).getPackingUnit().getWeight();
			String packingUnit = packagesList.get(0).getPackingUnit().getUnit();
			double seedWeight = 0D;
			for(int i = 0;i < seedList.size();i++){
				if("g".equalsIgnoreCase(packingUnit)){
					seedWeight = (packingUnitWeight * packagingAmount)/1000;
				}
				if("KG".equalsIgnoreCase(packingUnit)){
					seedWeight = packingUnitWeight * packagingAmount;
				}
				
				if("s".equalsIgnoreCase(packingUnit)){
					seedWeight = ((packingUnitWeight * packagingAmount) * (seedList.get(i).getThousandGrainWeight()/1000))/1000;
				}
				if("ks".equalsIgnoreCase(packingUnit)){
					seedWeight = ((packingUnitWeight * packagingAmount) * seedList.get(i).getThousandGrainWeight())/1000;
				}
				seedList.get(i).setSurplusWeight(seedList.get(i).getSurplusWeight() - seedWeight);
				seedDao.saveSeed(seedList.get(i));
			}
		}
		
	}
	
//	@SuppressWarnings("null")
	public Map<String, Object> checkSampleStorageAllowed(List<Integer> seedIdList,List<Integer> packagesIdList,List<Integer> packagingAmountList,Double quantity){
		String result = "可以入库";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 包装物要入库总数
		int packagingNum = 0;
		for (Integer num : packagingAmountList) {
			packagingNum = packagingNum + num;
		}
		
		//1.包装物规格数量和入库数量是否相等，不相等返回错误信息，入库失败
		if(seedIdList.size() == 1){
			if(quantity != packagingNum){
				result = "包装物总数和入库数量不相等！";
				resultMap.put("result", result);
				resultMap.put("allowFlag", false);
				return resultMap;
//				return result;
			}
		}
		if(seedIdList.size() > 1){
			if(!(quantity == packagingNum && quantity == seedIdList.size() && packagingNum == seedIdList.size())){
				result = "种子数量，包装物总数和入库数量不相等！";
				resultMap.put("result", result);
				resultMap.put("allowFlag", false);
				return resultMap;
			}
		}
		//2.从包装物packaging表中取出状态为 创建：0 的入库数量的包装物
		int countOFStatusZero = 0;//状态为0的包装物的总数
		if(seedIdList.size() == 1){
			int j = 0;//数据库中状态为0，且小于包装数量的包装数
			for(int i = 0;i<packagesIdList.size();i++){
				countOFStatusZero = packagingDao.getPackagingCountByStatusAndPackages(packagesIdList.get(i),0);
				if(packagingAmountList.get(i) > countOFStatusZero){
					j++;
				}
			}
			if(j > 0){
				result = "有" + j + "个包装物库存不足，入库失败！";
//				return result;
				resultMap.put("result", result);
				resultMap.put("allowFlag", false);
				return resultMap;
			}
		}
		if(seedIdList.size() > 1){
			countOFStatusZero = packagingDao.getPackagingCountByStatusAndPackages(packagesIdList.get(0),0);
			if(packagingAmountList.get(0) > countOFStatusZero){
				result = "库存数量不足！";
//				return result;
				resultMap.put("result", result);
				resultMap.put("allowFlag", false);
				return resultMap;
			}
		}
		
		resultMap.put("result", result);
		resultMap.put("allowFlag", true);
		return resultMap;
		
	}
	
	// 将入库文件去掉 空行，以及去掉重复行
	public List<String> removeEmptyAndDuplicateOfFile(File upFile) throws IOException {
		// 1.将入库文件去掉 空行，以及去掉重复行
		// 去掉空行数据，所有数据放到ayyarsListText
		InputStreamReader read = new InputStreamReader(new FileInputStream(
				upFile));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		ArrayList<String> ayyarsListText = new ArrayList<String>();
		while ((lineTxt = bufferedReader.readLine()) != null) {
			if (lineTxt == null || "".equals(lineTxt.trim())) {
				continue;
			}
			ayyarsListText.add(lineTxt);
		}
		read.close();
		bufferedReader.close();

		ArrayList<String> resultList = new ArrayList<String>();

		// 去除文件中的重复数据，放到 resultList
		for (String item : ayyarsListText) {
			if (!resultList.contains(item)) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	/**
	 * Insert productions into DB
	 * 是样品，入库文件肯定为null
	 * 1.检查包装物规格数量和入库数量是否相等，不相等返回错误信息，入库失败
	 * 2.从包装物packaging表中取出状态为 创建：0 的入库数量的包装物
	 * 3.包装物和种子封装为产品
	 * 4.入库：产品是否样品为：1，种子剩余重量减去包装物规格重量*入库数量，包装物剩余数量减去入库数量
	 * 生成产品信息和storage表记录
	 * 5.种子减去相应重量，包装物减去相应数量
	 * 
	 * 不是样品,一定有入库文件
	 * 0.检查包装规格表packages当前数量和剩余数量是否大于 要入库的数量：quantity,若小于则入库失败
	 * 1.将入库文件去掉 空行，以及去掉重复行
	 * 2.入库文件中的记录都在packaging表中有相应的记录，否则给出提示，小于10条返回具体信息，大于10条返回错误记录
     * 3.入库文件中不能有入库和出库状态的记录，所有记录状态只能是 创建：0,否则给出提示，小于10条返回具体信息，大于10条返回出错记录过多
	 * 4.入库文件中的记录全部和包装物规格相匹配，即文件中的每一行对应的packaging表的记录，packages外键属于 包装物规格的id值，否则返回具体信息，小于10条返回具体信息，大于10条返回出错记录过多
	 * 5.入库文件中的数据，和包装物规格的数量，入库数量需要分别相等
     * 6.入库操作,入库完成，seed表减去相应的重量，packages表减去相应的数量
	 * 生成storage表和productions表，并设置 是否样品为 否：0
	 * 
	 * @param
	 * @throws NoSuchAlgorithmException
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String addProductionsByStorage(Map<String, Object> storageParam) throws NoSuchAlgorithmException, IOException{
		String result = "入库完成。";
		
		MultipartFile uploadFile = (MultipartFile) storageParam.get("uploadFile");
		List<Integer> seedIdList =  (List<Integer>) storageParam.get("seedIdList");
		List<Integer> packagesIdList =  (List<Integer>) storageParam.get("packagesIdList");
		
//		List<Integer> packagingAmountList =  (List<Integer>) storageParam.get("packagingAmountList");
		int isSample = (Integer) storageParam.get("isSample");
//		Integer varietyId = (Integer)storageParam.get("varietyId");
		VarietyEntity varietyEntity = dictionaryDao.getVarietyById((Integer)storageParam.get("varietyId"));
		Double quantity = (Double) storageParam.get("quantity");
		Date storageDay = (Date) storageParam.get("storageDay");
		Date startDay = (Date) storageParam.get("startDay");
		Date endDay = (Date) storageParam.get("endDay");
		String productionLotNumber=(String)storageParam.get("productionLotNumber");
		
		List<SeedEntity> seedLists = getSeedList(seedIdList);
		List<PackageEntity> packageLists = getPackagesList(packagesIdList);
		/**数据校验
		 * 	如果当前包装物规格是s 或者是ks,那么当前的种子必须要有 千粒重
		 *  @Justin.wang
		 *  ---------------------------------------------------------------------
		 *  **/
		if(CollectionUtils.isNotEmpty(seedLists) && CollectionUtils.isNotEmpty(packageLists)){
			String getUnit = packageLists.stream().findFirst().get().getPackingUnit().getUnit();
			Double currentAmounts = packageLists.stream().findFirst().get().getCurrentAmount();
			if(currentAmounts == 0)
				return "当前包装的实际数量不能为 0! ";
			//查看当前包装物 是不是s 或者是 ks
			if(StringUtils.equalsIgnoreCase(getUnit, "s")  || StringUtils.equalsIgnoreCase(getUnit, "ks")){
				if(seedLists.stream().findFirst().get().getThousandGrainWeight() == 0 ||seedLists.stream().findFirst().get().getThousandGrainWeight() == null)
				return "当前种子的千粒重不能为 0! ";
				
			}   
		}
		/**
		 *  @Justin.wang
		 *  ---------------------------------------------------------------------
		 **/
		String seedids="0";
		String packageids="0";
		String packageingids="0";
		
		
		for(int id:seedIdList){
			seedids=seedids+","+id;
		}
		for(int id:packagesIdList){
			packageids=packageids+","+id;
		}
		
		List<PackagingEntity> packageingList=new ArrayList<PackagingEntity>();
		if(isSample==0){
			File upFile = MultipartFileChangeFileUtils.multipartToFile(uploadFile);
			List<String> resultList = removeEmptyAndDuplicateOfFile(upFile);
			System.out.println("File Size :"+resultList.size());
			
//			resultList
			
			for(int i=0;i<=resultList.size();i=i+1000){
				int j=i+1000;
				if(j>resultList.size()){
					j=resultList.size();
				}
				List newlist = resultList.subList(i,j);
				packageingList.addAll(packagingDao.getPackagingByQRCodeList(newlist));
			}
			System.out.println("packageingList : +"+packageingList.size());
			
		}else{
			packageingList=packagingDao.getPackagingForSample(packageids, quantity.intValue());
		
		}
		
		//验证
		if(packageingList.size()>quantity){
			result = "上传文件中Code数量大于入库数量，入库失败!，多了"+(packageingList.size()-quantity)+"个";
			return result;
		}else if(packageingList.size()<quantity){
			result = "包装库存不足，入库失败! 缺少"+(quantity-packageingList.size())+"个";
			return result;
		}
		
		Map checkResult=check(packageingList,packageids);
		String resultStr=(String) checkResult.get("Error");
		if(resultStr.equals("true")){
			result = (String) checkResult.get("notInPackags");
			result = result+System.getProperty("line.separator")+(String) checkResult.get("statusError");
			
			return result;
		}
		
		fileLogUtils.writeLog("------开始入库------","seedIdList : "+seedIdList +" packagesIdList: "+packagesIdList.size());
		StorageEntity storage = new StorageEntity();
		storage.setSeedids(seedids);
		storage.setPackagesids(packageids);
		
		storage.setSeed(seedLists.get(0));
		storage.setPackages(packageLists.get(0));
		storage.setProductionLotNumber(productionLotNumber);
		storage.setQuantity(quantity);
		storage.setSurplusQuantity(quantity);
		storage.setStorageDay(storageDay);
		storage.setStartDay(startDay);
		storage.setEndDay(endDay);
		storage.setIsSample(isSample);
		storage.setVariety(varietyEntity);
		try {
			storageDao.saveStorage(storage);
			fileLogUtils.writeLog("1.入库成功","storageDao.saveStorage(storage) : "+new Gson().toJson(storage.getId()));
		} catch (Exception e) {
			fileLogUtils.writeLog("1.入库失败","storageDao.saveStorage(storage) : "+new Gson().toJson(storage.getId()));
			e.printStackTrace();
		}
		
		List<ProductionsEntity> entityList = new ArrayList<ProductionsEntity>();
		for(PackagingEntity packagingEntity:packageingList){
			packageingids=packageingids+","+packagingEntity.getId();
			
			Float weight=packagingEntity.getPackingUnit().getWeight();
			String packageUnit=packagingEntity.getPackingUnit().getUnit();
			
			SeedEntity seed=null;
			
			double weightUnit=0d;
			if("g".equalsIgnoreCase(packageUnit)){
				weightUnit=weight/1000f;
			}else if("kg".equalsIgnoreCase(packageUnit)){
				weightUnit=weight;
			}
			//quantity
//			for(SeedEntity seedEntity:seedLists){
			for(int i=0;i<seedLists.size();i++){
				SeedEntity seedEntity=seedLists.get(i);
				if(seedEntity.getSurplusWeight() <= 0 && (i < (seedLists.size()-1))){
					continue;
				}else{
					
					if("s".equalsIgnoreCase(packageUnit)){
						weightUnit=seedEntity.getThousandGrainWeight()/1000f;
						weightUnit=weightUnit*packagingEntity.getPackingUnit().getWeight()/1000f;
					}else if("ks".equalsIgnoreCase(packageUnit)){
						weightUnit=seedEntity.getThousandGrainWeight();
						weightUnit=weightUnit*packagingEntity.getPackingUnit().getWeight()/1000f;
					}
					
					seedEntity.setSurplusWeight(seedEntity.getSurplusWeight()-weightUnit);
					seed=seedEntity;
					break;
				}
			}
			
			if(seed==null){
				seed=seedLists.get(0);
			}
			try {
				seedDao.saveSeed(seed);
				fileLogUtils.writeLog("2.入库成功","seedDao.saveSeed(seed)  : "+seed.getId());
			} catch (Exception e) {
				fileLogUtils.writeLog("2.入库失败","seedDao.saveSeed(seed)  : "+seed.getId());
				e.printStackTrace();
			}
				
			ProductionsEntity productions = new ProductionsEntity();
			productions.setStatus("入库");
			productions.setStorage(storage);
			productions.setSeed(seed);
			productions.setPackaging(packagingEntity);
			productions.setStartDay(startDay);
			productions.setEndDay(endDay);
			productions.setIsSample(isSample);
			productions.setVariety(varietyEntity);
			entityList.add(productions);
			//productionDao.saveProductions(productions);
		}
		try {
			baseDao.save(entityList);
			fileLogUtils.writeLog("3.入库成功","baseDao.save(entityList) ："+new Gson().toJson(entityList.size()));
		} catch (Exception e1) {
			fileLogUtils.writeLog("3.入库失败","baseDao.save(entityList) ："+new Gson().toJson(entityList.size()));
			e1.printStackTrace();
		}
		
		try {
			packagingDao.updatePackagingList(packageingids);
			fileLogUtils.writeLog("4.入库成功","packagingDao.updatePackagingList(packageingids) ："+new Gson().toJson(packageingids));
		} catch (Exception e1) {
			fileLogUtils.writeLog("4.入库失败","packagingDao.updatePackagingList(packageingids) ："+new Gson().toJson(packageingids));
			e1.printStackTrace();
		}
		
		for(PackageEntity packages:packageLists){
			try {
				packagingDao.updatePackagesSurplusAmount(packages);
				fileLogUtils.writeLog("5.入库成功","packagingDao.updatePackagesSurplusAmount(packages);"+new Gson().toJson(packages));
			} catch (Exception e) {
				fileLogUtils.writeLog("5.入库失败","packagingDao.updatePackagesSurplusAmount(packages);"+new Gson().toJson(packages));
				e.printStackTrace();
			}
		}
		//packagingDao
		//resultList
		
		fileLogUtils.writeLog("------入库完成------"," 入库成功");
		result = "入库完成。";
		return result;
	}
	
	
	
	private Map<String,String> check(List<PackagingEntity> packageingList,String ids){
		Map<String,String> checkResult=new HashMap();
		String notInPaackags="下列产品不在选中的包装列表中："+System.getProperty("line.separator");
		String statusError="下列产品已经入库："+System.getProperty("line.separator");
		int i=0;
		for(PackagingEntity packaging:packageingList){
			if(!ids.contains(packaging.getPackages().getId().toString())){
				//not in selected pagkages
				notInPaackags=notInPaackags+packaging.getIDCode()+System.getProperty("line.separator");
				i++;
			}
			if(packaging.getStatus()!=0){
				statusError=statusError+packaging.getIDCode()+System.getProperty("line.separator");
				i++;
				//status error
			}
		}
		
		if(i>0){
			checkResult.put("Error", "true");
		}else{
			checkResult.put("Error", "false");
		}
		
		checkResult.put("notInPackags", notInPaackags);
		checkResult.put("statusError", statusError);
		
		return checkResult;
	}
	/**
	 * 删除产品
	 * 出库之前删除产品需要还原 产品和包装状态，及 包装物数量
	 * @param storageId
	 */
	public void deleteProductionsByStorageId(Integer storageId){
		productionDao.deleteProductionsByStorageId(storageId);
		storageDao.deleteStorageById(storageId);
		/*
		 * 1.在storage表中获取 该记录关联的所有packages表的id
		 * 2.
		 */
	}
	
	
	/**
	 * Export QRCode information of productions into txt file
	 * @param storage
	 * @return
	 * @throws IOException
	 */
	public StringBuffer exportTxtData(StorageEntity storage) throws IOException{ 
		
		List<String> QRCodeList = productionDao.listProductionQRCodeByStorage(storage);
        
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
//	@CacheEvict(allEntries=true,value = "dataManageCache",cacheNames="dataManageCache")
	public void CacheEvict() {}
	public void saveTrackingCompletableFuture(DataManageEntity dataManage) throws Exception{
	    System.out.println("异步线程开始");
//		DataManageEntity dmSyn = dataManageDao.getDataManageByLotNumbers(dataManage.getLotNumbers());
//		trackingWebService.saveTrackingWeb(dmSyn);
//        CompletableFuture<String> completableFuture=new CompletableFuture();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            	//数据同步开始
//        		try {
//        		
//        		} catch (Exception e) {
//        			e.printStackTrace();
//        		}
////        		数据同步结束
//                //告诉completableFuture任务已经完成
//                completableFuture.complete("result");
//            }
//        }).start();
        System.out.println("异步线程结束");
    }

 
	/**
	 * Delivery productions and generate orders
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws IOException
	 */

	public Map<String, Object> deliveryProductions(Map<String, Object> params,MultipartFile uploadFile) throws IOException {
		String result = "出库完成。";
		fileLogUtils.writeLog("----准备出库----","");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		StorageEntity storage = storageDao.getStorageById((Integer)params.get("storageId"));

		String packagesIds = storage.getPackagesids();
		fileLogUtils.writeLog("----准备出库----","获取packagesIds"+packagesIds);
//		packagingDao.setPackagingStatus(packagesIds,2,1,(Double)params.get("deliveryAmount"));
		
		//1.出库数量大于 storage中的剩余数量，不允许出库,不论是否样品
		fileLogUtils.writeLog("----准备出库----","检查剩余数量开始");
		if ((Double) params.get("deliveryAmount") > storage
				.getSurplusQuantity()) {
			result = "出库数量大于剩余数量，出库失败！";
			returnMap.put("result", result); 
			returnMap.put("dataManage", null);
			fileLogUtils.writeLog("----准备出库----","检查剩余数量 : "+result);
			return returnMap;
		}
		fileLogUtils.writeLog("----准备出库----","检查剩余数量成功");
		/*
		 * 出库产品为样品 isSample = 1
		 * 1.出库数量大于 storage中的剩余数量，不允许出库
		 * 2.productions表中符合条件的记录小于出库数量，不允许出库：状态为入库，外键 storage表相应 记录中关联的外键
		 * 3.出库，成功后storage表中出库的记录状态更新为出库
		 * 4.订单表插入记录，状态为出库：0，1为已发货
		 * 
		 */
		
		//2.productions表中符合条件的记录小于出库数量，不允许出库：状态为入库，外键 storage表相应 记录中关联的外键
		fileLogUtils.writeLog("----准备出库----","storage ："+new Gson().toJson(storage.getId()));
		List<ProductionsEntity> proList = productionDao.listProductionsByStorageAndStatus(storage, "入库");
		fileLogUtils.writeLog("----准备出库----","productionDao.listProductionsByStorageAndStatus："+new Gson().toJson(proList.size()));
		if((Integer)params.get("isSample") == 1){
			fileLogUtils.writeLog("----开始出库样品----","");
			//获取productions表中符合条件的记录数
			if((Double) params.get("deliveryAmount") > proList.size()){
				result = "库存不足，出库失败！";
				fileLogUtils.writeLog("准备出库",result);
				returnMap.put("result", result);
				returnMap.put("dataManage", null);
				return returnMap;
			}
			//3.出库，成功后storage表中出库的记录状态更新为出库
			storage.setSurplusQuantity(storage.getSurplusQuantity() - (Double)params.get("deliveryAmount"));
			try {
				storageDao.editStorage(storage);
				fileLogUtils.writeLog("出库样品成功","storageDao.editStorage(storage) ："+new Gson().toJson(storage.getId()));
			} catch (Exception e) {
				fileLogUtils.writeLog("出库样品失败","storageDao.editStorage(storage) ："+new Gson().toJson(storage.getId()));
				e.printStackTrace();
			}
			
			
			//从产品表中获取 出库数量的记录，修改状态为 ：出库
			try {
				productionDao.updateProductionStatus((Integer)params.get("storageId"),(Double) params.get("deliveryAmount"));
				fileLogUtils.writeLog("出库样品","productionDao.updateProductionStatus ："+(Integer)params.get("storageId"));
			} catch (Exception e) {
				fileLogUtils.writeLog("出库样品失败","productionDao.updateProductionStatus ："+(Integer)params.get("storageId"));
				e.printStackTrace();
			}
//			for(ProductionsEntity entity:proList){
//				entity.setStatus("出库");
//				productionDao.editProductions(entity);
//			}
			// 获取物流信息，客户信息和销售信息
			LogisticCompanyEntity logisticCompany = dictionaryDao
					.getLogisticCompanyById((Integer)params.get("logisticId"));
			CustomerEntity customer = customerDao.getCustomerById((Integer)params.get("customerId"));
			
			DataManageEntity dataManage = new DataManageEntity();
			
			dataManage.setLogisticCompany(logisticCompany);
			dataManage.setCustomer(customer);
			dataManage.setReceivingTime(null);
			dataManage.setDeliveryTime((Date)params.get("deliveryTime"));
			dataManage.setOutStorageDay((Date)params.get("outStorageDay"));
			dataManage.setStatus(0);
			dataManage.setLotNumbers((String)params.get("lotNumber"));
			dataManage.setOddNumbers((String)params.get("oddNumbers"));
			dataManage.setDeliveryAmount((Double)params.get("deliveryAmount"));
			dataManage.setStorage(storage);
			dataManage.setProductions(proList);
			dataManage.setIsSample((Integer)params.get("isSample"));
			try {
				dataManageDao.saveDataManage(dataManage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//设置包装物状态为 2：出库（只设置出库数量的包装物）
			//出库样品时，取出状态为1：入库，数量为出库数量的包装记录（pckaging表)，将状态设置为2：出库
			try {
				packagingDao.setPackagingStatus(packagesIds,2,1,(Double)params.get("deliveryAmount"));
			} catch (Exception e) {
				fileLogUtils.writeLog("出库样品","packagingDao.setPackagingStatus ："+packagesIds);
				e.printStackTrace();
			}
			fileLogUtils.writeLog("-----出库样品完成----"," ");
			result = "出库完成。";
			returnMap.put("result", result);
			returnMap.put("dataManage", null);
			return returnMap;
		}
		
		/*
		 * 1.出库数量大于 storage中的剩余数量，不允许出库
		 * 2.去除文件中的空行和重复行
		 * 3.文件中的qrcode都在packaging表中找到记录，若有找不到的，返回给前台具体的qrcode信息，若超过10条，提示 不在表中记录过多，请检查出库文件
		 * 4.文件中的qrcode所在的包装物都属于 入库状态:1,若不属于 1，返回给前台具体qrcode信息，若超过10条,提示 出错记录过多，请检查出库文件
		 * 5.文件中产品和出库数量不相等，不允许出库
		 * 6.出库，成功后storage中的剩余数量更新为减去出库数量后的值
		 * 7.production表中出库的记录状态更新为 出库
		 * 8.订单表插入记录，状态为 出库：0 1为已发货
		 * 
		 */
		
		//非样品
		//2.去除文件中的空行和重复行
		//计算文件中的 产品的数量（抛去重复的，需要和出库数量相等
		fileLogUtils.writeLog("----准备出库----","开始读取上传文件");
		File upFile = MultipartFileChangeFileUtils.multipartToFile(uploadFile);
		
		InputStreamReader read = new InputStreamReader(new FileInputStream(upFile));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		ArrayList<String> ayyarsListText = new ArrayList<String>();
		//去除空行，保存到ayyarsListText
		while ((lineTxt = bufferedReader.readLine()) != null) {
			if(lineTxt == null || "".equals(lineTxt.trim())){
				continue;
			}
			ayyarsListText.add(lineTxt);
		}
		read.close();
		bufferedReader.close();
		fileLogUtils.writeLog("----准备出库----","结束读取上传文件");
		ArrayList<String> resultList = new ArrayList<String>();
		fileLogUtils.writeLog("----准备出库----","检查重复开始");
		// 去除文件中的重复数据，保存到resultList
		for (String item : ayyarsListText) {
			if (!resultList.contains(item)) {
				resultList.add(item);
			}
		}
		fileLogUtils.writeLog("----准备出库----","检查重复结束");
		//3.文件中的qrcode都在packaging表中找到记录，若有找不到的，返回给前台具体的qrcode信息，若超过10条，提示 不在表中记录过多，请检查出库文件
		
		//检查文件中的产品在产片表中对应的 记录存在，若有不存在的，则不做出库操作
		//检查文件中的产品 都是 入库状态（没有已 出库 的）
		
		//获取所有产品表中符合条件产品（seedId，packingUnitId，storageDay，状态为入库）
		fileLogUtils.writeLog("----准备出库----","开始获取产品");
		List<ProductionsEntity> productionList = productionDao.listProductionsByStorageAndStatus(storage,"入库");
		fileLogUtils.writeLog("----准备出库----","获取库存: "+new Gson().toJson(productionList.size()));
		List<String> qRCodeList = new ArrayList<String>();
		for(ProductionsEntity entity:productionList){
			qRCodeList.add(entity.getPackaging().getqRCode());
		}
//		System.out.println(productionList.stream().filter(o ->o.getPackaging().getIDCode().equals("EA039E460E96DE2745FF")).findFirst().isPresent());
		
		int sumNotInProduction = 0;
		result = "以下产品不在未出库产品表中：\n";
		System.out.println("storage iD : "+storage.getId());
		fileLogUtils.writeLog("----准备出库----","检查出库文件");
		for(String item:resultList){
			if(qRCodeList.contains(item)){
				continue;
			}
	 
	
			System.out.println("item :"+item);
			sumNotInProduction = sumNotInProduction + 1;
			if(sumNotInProduction > 10){
				result = "文件中不在未出库产品表中记录过多，请检查出库文件！";
				returnMap.put("result", result);
				returnMap.put("dataManage", null);
				return returnMap;
			}
			result = result + item + "\n";
			
//			if(!qRCodeList.contains(item)){
//				result = "有产品不在未出库产品表中·！";
//				returnMap.put("result", result);
//				returnMap.put("dataManage", null);
//				return returnMap;
//			}
		}
		if(sumNotInProduction > 0 && sumNotInProduction < 10){
			returnMap.put("result", result);
			returnMap.put("dataManage", null);
			return returnMap;
		}
		fileLogUtils.writeLog("----准备出库----","productions.add(entity)");
		List<ProductionsEntity> productions = new ArrayList<ProductionsEntity>();
		for(ProductionsEntity entity:productionList){
			if(resultList.contains(entity.getPackaging().getqRCode())){
				productions.add(entity);
			}
		}
		int sumStatusNotCorrect = 0;
		result = "以下产品不属于入库状态：\n";
		for(ProductionsEntity entity:productions){
			if("入库".equals(entity.getStatus())){
				continue;
			}
			sumStatusNotCorrect = sumStatusNotCorrect + 1;
			if(sumStatusNotCorrect > 10){
				result = "已出库状态记录过多，请检查出库文件！";
				returnMap.put("result", result);
				returnMap.put("dataManage", null);
				return returnMap;
			}
			result = result + entity.getPackaging().getqRCode() + "\n";
			
//			if(!"入库".equals(entity.getStatus())){
//				result = "有产品不属于入库状态，出库失败！";
//				returnMap.put("result", result);
//				returnMap.put("dataManage", null);
//				return returnMap;
//			}
		}
		if(sumStatusNotCorrect > 0 && sumStatusNotCorrect < 10){
			returnMap.put("result", result);
			returnMap.put("dataManage", null);
			return returnMap;
		}
		
		//4
		if( (Double)params.get("deliveryAmount") != resultList.size()){
			result = "文件中产品数和出库数量不相等！";
			returnMap.put("result", result);
			returnMap.put("dataManage", null);
			return returnMap;
		}
		fileLogUtils.writeLog("----结束准备出库----","");
		//5
		fileLogUtils.writeLog("-------开始出库------"," ");
		storage.setSurplusQuantity(storage.getSurplusQuantity() - (Double)params.get("deliveryAmount"));
		try {
			fileLogUtils.writeLog("1.出库成功","开始storageDao.editStorage(storage) ：");
			storageDao.editStorage(storage);
			fileLogUtils.writeLog("1.出库成功","storageDao.editStorage(storage) ："+new Gson().toJson(storage.getId()));
		} catch (Exception e) {
			fileLogUtils.writeLog("1.出库失败","storageDao.editStorage(storage) ："+new Gson().toJson(storage.getId()));
			e.printStackTrace();
		}
		
		//根据文件中 识别码去产品表中修改相应产品记录的状态为：出库
		try {
			fileLogUtils.writeLog("2.出库成功","开始productionDao.updateProductionStatusByQRCode(resultList)");
			productionDao.updateProductionStatusByQRCode(resultList);
			fileLogUtils.writeLog("2.出库成功","结束productionDao.updateProductionStatusByQRCode(resultList); ："+resultList.size());
		} catch (Exception e) {
			fileLogUtils.writeLog("2.出库失败","productionDao.updateProductionStatusByQRCode(resultList); ："+e.getMessage());
			e.printStackTrace();
		}
//		for(ProductionsEntity entity:productions){
//				entity.setStatus("出库");
//				productionDao.editProductions(entity);
//		}
		
		// 获取物流信息，客户信息和销售信息
		LogisticCompanyEntity logisticCompany = dictionaryDao
				.getLogisticCompanyById((Integer)params.get("logisticId"));
		CustomerEntity customer = customerDao.getCustomerById((Integer)params.get("customerId"));

		DataManageEntity dataManage = new DataManageEntity();
		
		dataManage.setLogisticCompany(logisticCompany);
		dataManage.setCustomer(customer);
		dataManage.setReceivingTime(null);
		dataManage.setDeliveryTime((Date)params.get("deliveryTime"));
		dataManage.setOutStorageDay((Date)params.get("outStorageDay"));
		dataManage.setStatus(0);
		dataManage.setLotNumbers((String)params.get("lotNumber"));
		dataManage.setOddNumbers((String)params.get("oddNumbers"));
		dataManage.setDeliveryAmount((Double)params.get("deliveryAmount"));
		dataManage.setStorage(storage);
//		dataManage.setProductions(productions);
		dataManage.setIsSample((Integer)params.get("isSample"));
		try {
			dataManageDao.saveDataManage(dataManage);
			fileLogUtils.writeLog("3.出库成功","dataManageDao.saveDataManage(dataManage); ："+dataManage.getLotNumbers());
		} catch (Exception e) {
			fileLogUtils.writeLog("3.出库失败","dataManageDao.saveDataManage(dataManage); ："+e.getMessage());
			e.printStackTrace();
		}
	
		//设置包装物状态为2：出库
		//非样品出库，根据qrcode从packaging表中找到相应的记录，并设置状态为：2：出库
		try {
			packagingDao.setPackagingStatusOfNonSample(resultList,2,dataManage.getId(), productions);
			fileLogUtils.writeLog("4.出库成功"," 设置包装物状态为2：出库: resultList.size : "+ resultList.size());
		} catch (Exception e) {
			fileLogUtils.writeLog("4.出库失败"," 设置包装物状态为2：出库 ："+e.getMessage());
			e.printStackTrace();
		}
	
		fileLogUtils.writeLog("----------出库完成---------"," 出库成功");
		result = "出库完成。";
//		同步数据不需要 这些
		dataManage.setProductions(null);
		dataManage.setStorage(null);
		returnMap.put("result", result);
		returnMap.put("dataManage", dataManage);
		return returnMap;
		
	}
	
	/**
	 * Check if storage is associated by other tables
	 * @param storageId
	 * @return true if being associated by other tables,else return false
	 */
	public boolean checkStorageBeAssociated(Integer storageId) {
		List<DataManageEntity> dataManageList = dataManageDao.listDataManageByStorageId(storageId);
		if(dataManageList != null && dataManageList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
//	/**
//	 * Edit productions information
//	 * @param storage
//	 * @throws NoSuchAlgorithmException
//	 */
//	public void editProductionsByStorage(StorageEntity storage) throws NoSuchAlgorithmException {
//		StorageEntity storageEntity = packagingDao.getStorageByStorageId(storage.getId());
//		
//		Double planAmount = storage.getPlanAmount();
////		SeedEntity seed = seedDao.getSeedById(storageEntity.getSeed().getId());
//		VarietyEntity variety = dictionaryDao.getVarietyById(storage.getVariety().getId());
//		PackingUnitEntity packingUnit = dictionaryDao.getPackingUnitById(storage.getPackingUnit().getId());
//		String iDCode = "";
//		String qRCode = "";
//		
//		//若入库时间有变化，则更新所有产品的入库时间
//		//1.编辑前入库时间为空，编辑后入库时间不为空
//		//2.编辑前入库时间不为空，编辑后入库时间和编辑前入库时间不相等
//		if(storage.getStorageDay() != null && (storageEntity.getStorageDay() == null || !storage.getStorageDay().equals(storageEntity.getStorageDay()))){
//			List<ProductionsEntity> productionList = packagingDao.listProductionsByStorage(storageEntity);
//			for(ProductionsEntity entity:productionList){
//				entity.setStorageDay(storage.getStorageDay());
//				packagingDao.editProductions(entity);
//			}
//		}
//		
//		//若编辑的计划数量大于数据库中的计划数量，则新增产品，使两个计划数量相等
//		if(planAmount > storageEntity.getPlanAmount()){
//			for(int i = 0;i< planAmount - storageEntity.getPlanAmount();i++){
//				iDCode = makeNumbersDetails.generateUnitIdentificationCode(seedDao.getSeedById(111));
//				qRCode = packagingDao.getQRCodePrefix() + iDCode;
//				
//				ProductionsEntity production = new ProductionsEntity();
//				production.setStorage(storageEntity);
//				production.setIDCode(iDCode);
//				production.setQRCode(qRCode);
//				production.setPlanAmount(1.0);
//				production.setCurrentAmount(0.0);
//				production.setSurplusAmount(0.0);
//				production.setPackingUnit(packingUnit);
////				production.setSeed(seed);
//				production.setVariety(variety);
//				if(storage.getStorageDay() == null){
//					production.setStorageDay(null);
//				}else{
//					production.setStorageDay(storage.getStorageDay());
//				}
//				production.setStatus("入库");
//				packagingDao.addProductions(production);
//			}
//		}
//		
//		if(storage.getPlanAmount() != null){
//			storageEntity.setPlanAmount(storage.getPlanAmount());
//		}
//		if(storage.getCurrentAmount() != null){
//			storageEntity.setCurrentAmount(storage.getCurrentAmount());
//			storageEntity.setSurplusAmount(storage.getCurrentAmount());
//		}
//		storageEntity.setPackingUnit(packingUnit);
//		if(storage.getStorageDay() == null){
//			storageEntity.setStorageDay(null);
//		}else{
//			storageEntity.setStorageDay(storage.getStorageDay());
//		}
//		
//		storageDao.saveStorage(storageEntity);
//		
//	}

	
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


//	public StorageEntity getStorageSummary(Integer speciesId, String conmercial_name,
//			Integer packingUnitId) {
//		List<StorageEntity> storageList = packagingDao.listStorageSummary(speciesId,conmercial_name,packingUnitId);
//		if(storageList == null || storageList.size() == 0){
//			return null;
//		}
//		
//		StorageEntity storage = new StorageEntity();
//		Double planAmount = 0.0,currentAmount = 0.0,surplusAmount = 0.0;
//		for(StorageEntity entity:storageList){
//			if(entity.getCurrentAmount() != null){
//				planAmount = planAmount + entity.getPlanAmount();
//			}
//			if(entity.getCurrentAmount() != null){
//				currentAmount = currentAmount + entity.getCurrentAmount();
//			}
//			if(entity.getSurplusAmount() != null){
//				surplusAmount = surplusAmount + entity.getSurplusAmount();
//			}
//		}
//		storage.setPlanAmount(planAmount);
//		storage.setCurrentAmount(currentAmount);
//		storage.setSurplusAmount(surplusAmount);
//		storage.setPackingUnit(storageList.get(0).getPackingUnit());
//		storage.setSeed(storageList.get(0).getSeed());
//		storage.setId(1);
//		
//		return storage;
//		
//	}

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


	public StorageEntity getStorageById(Integer id) {
		
		return packagingDao.getStorageByStorageId(id);
	}
	
	public String getLotNumber(String code){
		StorageEntity storageEntity =productionDao.getLastProductionLotNumber(code);
		 if(storageEntity==null){
			 return code+"0001";
		 }else{
			 String lastLotNumber=storageEntity.getProductionLotNumber();
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
