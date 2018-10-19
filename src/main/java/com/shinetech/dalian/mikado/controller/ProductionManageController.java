package com.shinetech.dalian.mikado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.varia.FallbackErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.service.CustomerService;
import com.shinetech.dalian.mikado.service.DataManageService;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.PackagingService;
import com.shinetech.dalian.mikado.service.ProductionService;
import com.shinetech.dalian.mikado.service.SeedService;
import com.shinetech.dalian.mikado.util.FileLogUtils;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.ResultMessage;

/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/productions")
public class ProductionManageController extends BaseController{
	
	@Autowired
	private PackagingService packagingService;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private MakeNumbersDetails makeNumberDetails;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductionService productionService;
	@Autowired
	private SeedService seedService;
	@Autowired
	private DataManageService dataManageService;
	@Autowired
	private FileLogUtils fileLogUtils;
	/**
	 * Direct to packing manage page
	 * @param request
	 * @return if user is null, redirect login page, then return to pading manage page
	 */
	@RequestMapping(value="/manage")
	public String packagingManagePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(user == null){
			return "redirect:/";
		}
		/**
		 * Get data information from DB, then set request attributes,so that the front end can user them
		 */
		List<SeedEntity> seedLists = seedService.getSeedList(); //种子信息
		List<PackingUnitEntity> packingUnitLists = packagingService.getPackingUnitsFromPackages(); //包装物信息
		List<LogisticCompanyEntity> ligisticsCompanyLists = dictionaryService.getLogisticsCompanyList(); //物流信息
		List<CustomerEntity> customerLists = dictionaryService.listCustomers(); //客户信息
		List<ImporterEntity> importerLists = dictionaryService.getImporterList();
		List<SupplierEntity> supplierLists = dictionaryService.getSupplierList();
//		List<String> conmercialNameList = SeedService.getConmercialNamesFromSeeds();
		List<String> conmercialNameList = dictionaryService.getConmercialNameFromVarity();
		List<VarietyEntity> varietyList = dictionaryService.getVarietyList();
		List<PackageEntity> packagesList = packagingService.getAllPackages();
		
		request.setAttribute("seedLists", seedLists);
		request.setAttribute("packingUnitLists", packingUnitLists);
		request.setAttribute("ligisticsCompanyLists", ligisticsCompanyLists);
		request.setAttribute("customerLists", customerLists);
		request.setAttribute("importerLists", importerLists);
		request.setAttribute("supplierLists", supplierLists);
		request.setAttribute("conmercialNameList", conmercialNameList);
		request.setAttribute("varietyList", varietyList);
		request.setAttribute("packagesList", packagesList);
		
		return "jsp/production-manage";
	}
	
	/**
	 * Get productions data from DB by search conditions
	 * If search conditions are empty, then get all productions
	 * @param request
	 * @return map which contains the searched productions by search conditions and pagination parameters
	 * @throws ParseException
	 */
	@RequestMapping(value="/getProductionsData")
	public @ResponseBody Map<String, Object> getProductionsData(HttpServletRequest request) throws ParseException{
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String conmercialName = request.getParameter("conmercialName");
		String packingUnitId = request.getParameter("packingUnit.id");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", offset);
		params.put("maxResult", limit);
		params.put("conmercialName", conmercialName);
		params.put("packingUnitId", packingUnitId);
		
		return productionService.getStorageByGroup(params);
	}
	
	/**
	 * Get sale manager form customer table by customer id
	 * @param request
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/getSaleManager")
	public @ResponseBody SaleManagerEntity getSaleManager(HttpServletRequest request,Integer customerId){
		return customerService.getSaleManagerByCustomerId(customerId);
	}
//	入库操作
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResultMessage addProductions(HttpServletRequest request,
//			StorageEntity storage
			@RequestParam(value = "seedIdList", required = true) List<Integer> seedIdList,
			@RequestParam(value = "isSample", required = true) Integer isSample,
			@RequestParam(value = "productionLotNumber", required = true) String productionLotNumber,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			@RequestParam(value = "packagesIdList", required = true) List<Integer> packagesIdList,
			@RequestParam(value = "varietyId",required = true) Integer varietyId,
//			@RequestParam(value = "packagingAmountList", required = true) List<Integer> packagingAmountList,
			@RequestParam(value = "quantity", required = true) Double quantity,
			@RequestParam(value = "storageDay", required = true) Date storageDay,
			@RequestParam(value = "startDay", required = true) Date startDay,
			@RequestParam(value = "endDay", required = true) Date endDay
			) throws NoSuchAlgorithmException, IOException {
		String result = "入库成功。";
		
		Map<String, Object> storageParam = new HashMap<String, Object>();
		storageParam.put("uploadFile", uploadFile);
		storageParam.put("seedIdList", seedIdList);
		storageParam.put("isSample", isSample);
		storageParam.put("productionLotNumber", productionLotNumber);
		storageParam.put("packagesIdList", packagesIdList);
		storageParam.put("varietyId", varietyId);
//		storageParam.put("packagingAmountList", packagingAmountList);
		storageParam.put("quantity", quantity);
		storageParam.put("storageDay", storageDay);
		storageParam.put("startDay", startDay);
		storageParam.put("endDay", endDay);
		result = productionService.addProductionsByStorage(storageParam);
		
		//record add production log into DB
		logService.logForOperation(logContent.getADDPRODUCTION(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));

		return new ResultMessage(result, true);
	}
	
	/**
	 * @param binder
	 */
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	} 
	
//	@RequestMapping(value="/edit")
//	public @ResponseBody ResultMessage editProductions(HttpServletRequest request,StorageEntity storage) throws NoSuchAlgorithmException{
//		String result = "编辑成功";
//		
//		productionService.editProductionsByStorage(storage);
//		//record edit production log into DB
//		logService.logForOperation(logContent.getEDITPRODUCTION(), logContent
//				.getINFOTYPE(),
//				(UserEntity) request.getSession().getAttribute("userEntity"));
//		
//		return new ResultMessage(result, true);
//	}
	
	/**
	 * Mainly update the current_amount of the production before delivery
	 * @param request
	 * @param storage
	 * @return
	 */
//	@RequestMapping(value="/update")
//	public @ResponseBody ResultMessage updateProductions(HttpServletRequest request ,StorageEntity storage){
//		String result = "更新成功";
//		
//		productionService.updateProductionsByStorage(storage);
//		//record update production log into DB
//		logService.logForOperation(logContent.getUPDATEPRODUCTION(), logContent
//				.getINFOTYPE(),
//				(UserEntity) request.getSession().getAttribute("userEntity"));
//		
//		return new ResultMessage(result, true);
//	}
	
	/**
	 * Delete productions from DB
	 * @param request
	 * @param Idlists
	 * @return result message if productions are deleted success or not 
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ResultMessage deleteProductions(HttpServletRequest request,@RequestBody List<Integer> Idlists){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功";
		
		int i = 0;
		/*
		 * Delete production by id circulated
		 * if productions is associated by data manage, then it cannot be deleted
		 */
		for(Integer id:Idlists){
			if(productionService.checkStorageBeAssociated(id) == true){
				i = i +1;
				logService.logForOperation(logContent.getDELETEPRODUCTIONFAIL(), logContent.getERRORTYPE(),user);
				continue;
			}
			
			productionService.deleteProductionsByStorageId(id);
			
			logService.logForOperation(logContent.getDELETEPRODUCTION(), logContent.getINFOTYPE(),user);
		}
		if(Idlists.size() == 1 && i == 1){
			result = "产品有出库记录，不能删除！";
		}
		if(Idlists.size() >1 && i >1){
			result = "删除完成。其中" + i +"项有出库记录，未删除";
		}
		return new ResultMessage(result, true);
	}
	
//	/**
//	 * Export txt file of QRCodes of a batch of productions
//	 * The batch productions have the same seed info and storage day and packing unit specifications
//	 * @param request
//	 * @param response
//	 * @param id
//	 */
//	@RequestMapping(value="/exportTxt",produces = "application/json;charset=UTF-8", method = {
//			RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody void exportTxtData(HttpServletRequest request,HttpServletResponse response, @RequestParam Integer id) {
//		UserEntity user = (UserEntity) request.getSession().getAttribute(
//				"userEntity");
//		OutputStream os;
//		try {
//			os = response.getOutputStream();
//			/*
//			 * Set response header and content type
//			 */
//			response.reset();
//			response.setHeader("Content-Disposition",
//					"attachment; filename=QRCode.txt");
//			response.setContentType("application/octet-stream; charset=utf-8");
//
//			StorageEntity storage = packagingService.getStorageById(id);
//			
//			//write the txt content to os
//			os.write(productionService.exportTxtData(storage).toString()
//					.getBytes());
//			os.flush();
//			os.close();
//			//Record export txt success log if exporting successfully
//			logService.logForOperation(logContent.getEXPOTTXTSUCCESS(),
//					logContent.getINFOTYPE(), user);
//		} catch (IOException e) {
//			//Record export txt failed log if exporting failed
//			logService.logForOperation(logContent.getEXPOTTXTFAIL(),
//					logContent.getINFOTYPE(), user);
//			e.printStackTrace();
//		} 
//
//	}
	
	@InitBinder("dataManage")   
    public void initBinder1(WebDataBinder binder) {   
            binder.setFieldDefaultPrefix("dataManage.");   
    }  
   @InitBinder("storage")   
    public void initBinder2(WebDataBinder binder) {   
            binder.setFieldDefaultPrefix("storage.");   
   } 
   
   /**
    * Generate lot number of orders of delivery productions
    * @param request
    * @param seedId
    * @return
    * @throws NoSuchAlgorithmException
    */
   @RequestMapping(value="/generateLotNumber",method = RequestMethod.GET)
   public @ResponseBody JsonObject generateLotNumber(HttpServletRequest request,Integer seedID) throws NoSuchAlgorithmException{
	    SeedEntity seedEntity=seedService.getSeedById(seedID);
	    
		String lotNumber=productionService.getLotNumber(getYear()+seedEntity.getSpecies().getCropCode());
		
		JsonObject json = new JsonObject();
		json.addProperty("lotNumber", lotNumber);
		return json;
   }
	
   /**
    * Delivery productions to customer and generate order
    * @param request
    * @param deliveryAmount
    * @param lotNumber
    * @param logisticId
    * @param customerId
    * @param uploadFile
    * @param seedId
    * @param packingUnitId
    * @param storageId
    * @param storageDay
    * @param original_lot
    * @param original
    * @param purchase_day
    * @param deliveryTime
    * @param phyto_no
    * @param import_permit_no
    * @param germination
    * @param test_time
    * @param importerId
    * @param supplierId
    * @return
    * @throws IOException
    * 出库操作
    */
	@RequestMapping(value = "/delivery", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object> deliveryProductions(
			HttpServletRequest request,
			@RequestParam(value = "isSample", required = true) Integer isSample,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			@RequestParam(value = "lotNumber", required = true) String lotNumber,
			@RequestParam(value = "oddNumbers", required = false) String oddNumbers,
			@RequestParam(value = "deliveryAmount", required = true) Double deliveryAmount,
			@RequestParam(value = "outStorageDay",required = true) Date outStorageDay,
			@RequestParam(value = "deliveryTime", required = true) Date deliveryTime,
			@RequestParam(value = "logisticId", required = true) Integer logisticId,
			@RequestParam(value = "customerId", required = true) Integer customerId,
			@RequestParam(value = "storageId", required = true) Integer storageId
			) throws IOException {
		fileLogUtils.writeLog("-------Controller 开始出库------"," ");
	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lotNumber", lotNumber);
		params.put("oddNumbers", oddNumbers);
		params.put("deliveryAmount", deliveryAmount);
		params.put("deliveryTime", deliveryTime);
		params.put("outStorageDay", outStorageDay);
		params.put("logisticId", logisticId);
		params.put("customerId", customerId);
		params.put("storageId", storageId);
		params.put("isSample", isSample);
		fileLogUtils.writeLog("-------Controller 出库参数------","params : "+new Gson().toJson(params));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		productionService.CacheEvict();
		resultMap = productionService.deliveryProductions(params, uploadFile);
		//Record delivery production log into DB
		logService.logForOperation(logContent.getDELIVERYPRODUCTION(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		fileLogUtils.writeLog("-------Controller 出库完成------","出库结果: "+new Gson().toJson(new Gson().toJson(resultMap)));
		return resultMap;
	}
	
	 
	
	@RequestMapping(value="/saveTrackingWeb")
	public @ResponseBody ResultMessage saveTrackingWeb(HttpServletRequest request,DataManageEntity dataManage){
		packagingService.saveTrackingWeb(dataManage);
		
		return new ResultMessage("success", true);
	}
	
	
}
