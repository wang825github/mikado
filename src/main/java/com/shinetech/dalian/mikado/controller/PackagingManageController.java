package com.shinetech.dalian.mikado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.google.gson.JsonObject;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackageEntity;
import com.shinetech.dalian.mikado.entity.PackagingEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.service.CustomerService;
import com.shinetech.dalian.mikado.service.DataManageService;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.PackagingService;
import com.shinetech.dalian.mikado.service.SeedService;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.ResultMessage;

/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/packaging")
public class PackagingManageController extends BaseController{
	
	@Autowired
	private DataManageService dataManageService;
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
	private SeedService seedService;
	@Autowired
	private CustomerService customerService;
	
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
		List<SeedEntity> seedLists = dictionaryService.getSeedList(); //种子信息
		List<PackingUnitEntity> packingUnitLists = dictionaryService.getPackingUnitList(); //包装物信息
		List<LogisticCompanyEntity> ligisticsCompanyLists = dictionaryService.getLogisticsCompanyList(); //物流信息
		List<CustomerEntity> customerLists = dictionaryService.listCustomers(); //客户信息
		List<ImporterEntity> importerLists = dictionaryService.getImporterList();
		List<SupplierEntity> supplierLists = dictionaryService.getSupplierList();
//		List<String> conmercialNameList = SeedService.getConmercialNamesFromSeeds();
		List<String> conmercialNameList = dictionaryService.getConmercialNameFromVarity();
//		List<VarietyEntity> varietyList = dictionaryService.getVarietyList();
		List<SpeciesEntity> speciesLists = dictionaryService.getSpeciesList();
		
		request.setAttribute("seedLists", seedLists);
		request.setAttribute("packingUnitLists", packingUnitLists);
		request.setAttribute("ligisticsCompanyLists", ligisticsCompanyLists);
		request.setAttribute("customerLists", customerLists);
		request.setAttribute("importerLists", importerLists);
		request.setAttribute("supplierLists", supplierLists);
		request.setAttribute("conmercialNameList", conmercialNameList);
//		request.setAttribute("varietyList", varietyList);
		request.setAttribute("speciesLists", speciesLists);
		
		return "jsp/packaging-manage";
	}
	
	/**
	 * Get productions data from DB by search conditions
	 * If search conditions are empty, then get all productions
	 * @param request
	 * @return map which contains the searched productions by search conditions and pagination parameters
	 * @throws ParseException
	 */
	@RequestMapping(value="/getPackagingData")
	public @ResponseBody Map<String, Object> getPackagingData(HttpServletRequest request) throws ParseException{
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String speciesNameCn = request.getParameter("speciesNameCn");
		String packingUnitId = request.getParameter("packingUnitId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", offset);
		params.put("maxResult", limit);
		params.put("speciesNameCn", speciesNameCn);
		params.put("packingUnitId", packingUnitId);
		
		return packagingService.getPackagingByGroup(params);
	}
	
//	/**
//	 * Get sale manager form customer table by customer id
//	 * @param request
//	 * @param customerId
//	 * @return
//	 */
//	@RequestMapping(value="/getSaleManager")
//	public @ResponseBody SaleManagerEntity getSaleManager(HttpServletRequest request,Integer customerId){
//		return customerService.getSaleManagerByCustomerId(customerId);
//	}
	
	@RequestMapping(value = "/add")
	public @ResponseBody ResultMessage addPackagings(HttpServletRequest request,
			PackageEntity packages) throws NoSuchAlgorithmException {
		String result = "包装订购成功。";

		packagingService.addPackagingsByPackage(packages);
		//record add production log into DB
		logService.logForOperation(logContent.getADDPACKAGES(), logContent
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
	
	@RequestMapping(value="/edit")
	public @ResponseBody ResultMessage editPackagings(HttpServletRequest request,PackageEntity packages) throws NoSuchAlgorithmException{
		String result = "编辑成功";
		
		//首先检查包装状态，如果有 出库记录，则不能修改实际数量
		PackageEntity packageEntity = packagingService.getPackagesById(packages.getId());
		if(packageEntity.getCurrentAmount() != packages.getCurrentAmount()){
			
			int deliveryPackagingCount = packagingService.getDeliveryPackagingByPackageId(packages.getId());
			if(deliveryPackagingCount > 0){
				result = "有出库记录，不能编辑实际数量！";
				logService.logForOperation(logContent.getUPDATE_PACKAGE_AMOUNT_FAIL(), logContent
						.getERRORTYPE(),
						(UserEntity) request.getSession().getAttribute("userEntity"));
				return new ResultMessage(result, true);
			}
		}
		
		packagingService.editPackagingsByPackages(packages);
		//record edit production log into DB
		logService.logForOperation(logContent.getEDITPRODUCTION(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage(result, true);
	}
	
	/**
	 * Mainly update the current_amount of the production before delivery
	 * @param request
	 * @param storage
	 * @return
	 */
	@RequestMapping(value="/update")
	public @ResponseBody ResultMessage updatePackagings(HttpServletRequest request ,PackageEntity packages){
		String result = "更新成功";
		
		//首先检查包装状态，如果有 出库记录，则不能修改实际数量
		int deliveryPackagingCount = packagingService.getDeliveryPackagingByPackageId(packages.getId());
		if(deliveryPackagingCount > 0){
			result = "有出库记录，不能更新实际数量！";
			logService.logForOperation(logContent.getUPDATE_PACKAGE_AMOUNT_FAIL(), logContent
					.getERRORTYPE(),
					(UserEntity) request.getSession().getAttribute("userEntity"));
			return new ResultMessage(result, true);
		}
		
		packagingService.updatePackagingsByPackages(packages);
		//record update production log into DB
		logService.logForOperation(logContent.getUPDATE_PACKAGE_AMOUNT(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete productions from DB
	 * @param request
	 * @param Idlists
	 * @return result message if productions are deleted success or not 
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ResultMessage deletePackagings(HttpServletRequest request,@RequestBody List<Integer> Idlists){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功";
		
		int i = 0;
		/*
		 * Delete production by id circulated
		 * if productions is associated by data manage, then it cannot be deleted
		 */
		for(Integer id:Idlists){
			if(packagingService.checkPackagesBeAssociated(id) == true){
				i = i +1;
				logService.logForOperation(logContent.getDELETEPACKAGESFAIL(), logContent.getERRORTYPE(),user);
				continue;
			}
			
			packagingService.deletePackagingsByPackageId(id);
			
			logService.logForOperation(logContent.getDELETEPACKAGES(), logContent.getINFOTYPE(),user);
		}
		if(Idlists.size() == 1 && i == 1){
			result = "该包装已生成产品，删除失败！。";
		}
		if(Idlists.size() >1 && i >1){
			result = "删除完成。其中" + i +"项包装已生成产品，未删除";
		}
		return new ResultMessage(result, true);
	}
	
	/**
	 * Export txt file of QRCodes of a batch of productions
	 * The batch productions have the same seed info and storage day and packing unit specifications
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="/exportTxt",produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void exportTxtData(HttpServletRequest request,HttpServletResponse response, @RequestParam Integer id) {
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				"userEntity");
		OutputStream os;
		try {
			os = response.getOutputStream();
			/*
			 * Set response header and content type
			 */
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment; filename=QRCode.txt");
			response.setContentType("application/octet-stream; charset=utf-8");

			PackageEntity packages = packagingService.getPackagesById(id);
			
			//write the txt content to os
			os.write(packagingService.exportTxtData(packages).toString()
					.getBytes());
			os.flush();
			os.close();
			//Record export txt success log if exporting successfully
			logService.logForOperation(logContent.getEXPOTTXTSUCCESS(),
					logContent.getINFOTYPE(), user);
		} catch (IOException e) {
			//Record export txt failed log if exporting failed
			logService.logForOperation(logContent.getEXPOTTXTFAIL(),
					logContent.getINFOTYPE(), user);
			e.printStackTrace();
		} 

	}
	
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
   public @ResponseBody JsonObject generateLotNumber(HttpServletRequest request,Integer speciesID) throws NoSuchAlgorithmException{
//	    VarietyEntity varietyEntity=dataManageService.getVariety(verietyID);
	    SpeciesEntity speciesEntity = dictionaryService.getSpeciesById(speciesID);
//		String lotNumber=packagingService.getLotNumber(getYear()+varietyEntity.getSpecies().getCropCode());
	    String lotNumber = packagingService.getLotNumber(getYear()+speciesEntity.getCropCode());
		
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
    */
//	@RequestMapping(value = "/delivery", produces = "application/json;charset=UTF-8", method = {
//			RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody Map<String, Object> deliveryProductions(
//			HttpServletRequest request,
//			@RequestParam(value = "deliveryAmount", required = true) Double deliveryAmount,
//			@RequestParam(value = "lotNumber", required = true) String lotNumber,
//			@RequestParam(value = "logisticId", required = true) Integer logisticId,
//			@RequestParam(value = "customerId", required = true) Integer customerId,
//			@RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile,
//			@RequestParam(value = "seedId", required = true) Integer seedId,
//			@RequestParam(value = "packingUnitId", required = true) Integer packingUnitId,
//			@RequestParam(value = "storageId", required = true) Integer storageId,
//			@RequestParam(value = "storageDay", required = true) Date storageDay,
//			@RequestParam(value = "original_lot", required = true) String original_lot,
//			@RequestParam(value = "original", required = true) String original,
//			@RequestParam(value = "purchase_day", required = true) Date purchase_day,
//			@RequestParam(value = "deliveryTime", required = true) Date deliveryTime,
//			@RequestParam(value = "phyto_no", required = true) String phyto_no,
//			@RequestParam(value = "import_permit_no", required = true) String import_permit_no,
//			@RequestParam(value = "germination", required = false) String germination,
//			@RequestParam(value = "test_time", required = false) Date test_time,
//			@RequestParam(value = "importer.id", required = true) Integer importerId,
//			@RequestParam(value = "supplier.id", required = true) Integer supplierId
//			) throws IOException {
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("deliveryAmount", deliveryAmount);
//		params.put("lotNumber", lotNumber);
//		params.put("logisticId", logisticId);
//		params.put("seedId", seedId);
//		params.put("customerId", customerId);
//		params.put("packingUnitId", packingUnitId);
//		params.put("storageId", storageId);
//		params.put("storageDay", storageDay);
//		params.put("original_lot", original_lot);
//		params.put("original", original);
//		params.put("purchase_day", purchase_day);
//		params.put("deliveryTime", deliveryTime);
//		params.put("phyto_no", phyto_no);
//		params.put("import_permit_no", import_permit_no);
//		params.put("germination", germination);
//		params.put("test_time", test_time);
//		params.put("importerId", importerId);
//		params.put("supplierId", supplierId);
//		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap = packagingService.deliveryProductions(params, uploadFile);
//		//Record delivery production log into DB
//		logService.logForOperation(logContent.getDELIVERYPRODUCTION(), logContent
//				.getINFOTYPE(),
//				(UserEntity) request.getSession().getAttribute("userEntity"));
//		
//		return resultMap;
//	}
	
	@RequestMapping(value="/saveTrackingWeb")
	public @ResponseBody ResultMessage saveTrackingWeb(HttpServletRequest request,DataManageEntity dataManage){
		packagingService.saveTrackingWeb(dataManage);
		
		return new ResultMessage("success", true);
	}
	
	@RequestMapping(value="/getPackageListBySpecies",method = RequestMethod.GET)
	public @ResponseBody List<PackageEntity> getPackageListByVeriety(HttpServletRequest request,Integer seedID) throws NoSuchAlgorithmException{
		SeedEntity seed=seedService.getSeedById(seedID);
		List<PackageEntity> packageList = new ArrayList<PackageEntity>();
		if(seed!=null){
			packageList=packagingService.getPackagesBySpecies(seed.getSpecies().getId());
		}
		return packageList;
	 }
	
	@RequestMapping(value="/getVarietyListBySeedId",method = RequestMethod.GET)
	public @ResponseBody List<VarietyEntity> getVarietyListBySeedId(HttpServletRequest request,Integer seedID) throws NoSuchAlgorithmException{
		SeedEntity seed=seedService.getSeedById(seedID);
		List<VarietyEntity> varietyList = new ArrayList<VarietyEntity>();
		if(seed!=null){
			varietyList=dictionaryService.getVarietyListBySpecies(seed.getSpecies());
		}
		return varietyList;
	 }
	
}
