package com.shinetech.dalian.mikado.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.entity.GroupsEntity;
import com.shinetech.dalian.mikado.entity.ImportNameEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProvinceEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.PublicService;
import com.shinetech.dalian.mikado.util.MakeDateForDay;
import com.shinetech.dalian.mikado.util.ResultMessage;

/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/data")
@SuppressWarnings("rawtypes")
public class DictionaryController {
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	BaseDao baseDao;
	@Autowired
	PublicService publicService;
	/**
	 * Direct to dictionary page
	 * @param request
	 * @return the string of the data dictionary page url
	 */
	@RequestMapping(value="/dictionary")
	public String logManagePage(HttpServletRequest request){
		
		List<SpeciesEntity> speciesList = dictionaryService.getSpeciesList();
		List<AreaEntity> areaList = dictionaryService.getAreaList();
		List<GroupsEntity> groupsList =  baseDao.getAll(GroupsEntity.class);
		List<ProvinceEntity> provinceList =  baseDao.getAll(ProvinceEntity.class);
		request.setAttribute("speciesList", speciesList);
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("provinceList", provinceList);
		return "jsp/data-dictionary";
	}
	
	/**
	 * Get species data from DB
	 * @param request
	 * @return map which contains the searched species by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getSpeciesData")
	public @ResponseBody Map<String, Object> getSpeciesData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getSpeciesByFy(param);
	}
	
	/**
	 * Get packingUnit data from DB
	 * @param request
	 * @return map which contains the searched packing units by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getPackingUnitData")
	public @ResponseBody Map<String, Object> getPackagesData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getPackingUnitByFy(param);
	}
	
	/**
	 * Get import name data from DB
	 * @param request
	 * @return map which contains the searched import names by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getImportNameData")
	public @ResponseBody Map<String, Object> getImportNameData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getImportNameByFy(param);
	}
	
	/**
	 * Get importers data from DB
	 * @param request
	 * @return map which contains the searched importers by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getImporterData")
	public @ResponseBody Map<String, Object> getImporterData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getImporterByFy(param);
	}
	
	/**
	 * Get import suppliers from DB
	 * @param request
	 * @return map which contains the searched suppliers by search conditions and pagination parameters
	 */
	@RequestMapping(value = "/getSupplierData")
	public @ResponseBody Map<String, Object> getSupplierData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getSupplierByFy(param);
	}
	
	/**
	 * Get sales managers data from DB
	 * @param request
	 * @return map which contains the searched sales managers by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getSaleManagerData")
	public @ResponseBody Map<String, Object> getSaleManagerData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getSaleManagerByFy(param);
	}
	
	
	@RequestMapping(value="/getVarietyData")
	public @ResponseBody Map<String, Object> getVarietyData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		Map<String, Object> res  = dictionaryService.getVarietyByFy(param);
		return res;
	}
	
	/**
	 * Add VarietyEntity information into DB
	 * @param request
	 * @return result message about adding Logistics Company information success or not
	 * @throws  
	 */
	@RequestMapping(value="/addVariety")
	public @ResponseBody ResultMessage addVariety(HttpServletRequest request,VarietyEntity variety,String beginDateShow) {
		try {
			variety.setBeginDate(MakeDateForDay.createDay(beginDateShow));
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResultMessage("日期格式错误！", true);
		}
		if(dictionaryService.checkVariety(variety) == true){
			return new ResultMessage("品种简码已存在！", true);
		}
		
		dictionaryService.saveVariety(variety);
		//Record the log of adding Logistics Company information
		logService.logForOperation(LogContent.ADD_VARIETY_MSG, logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	/**
	 * Edit varietyEntity information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@RequestMapping(value="/editVariety")
	public @ResponseBody ResultMessage editVariety(HttpServletRequest request,VarietyEntity variety,String beginDateShow){
		try {
			variety.setBeginDate(MakeDateForDay.createDay(beginDateShow));
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResultMessage("日期格式错误！", true);
		}
		if(dictionaryService.checkVariety(variety) == true){
			return new ResultMessage("品种简码已存在！", true);
		}
		
		dictionaryService.saveVariety(variety);

		logService.logForOperation(LogContent.EDIT_VARIETY_MSG , logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	@RequestMapping(value="/deleteVariety")
	public @ResponseBody ResultMessage deleteVariety(HttpServletRequest request,@RequestBody List<VarietyEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		for (VarietyEntity variety : list) {
			try {
				dictionaryService.delVariety(variety, variety.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联。", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_VARIETY_MSG , logContent.getINFOTYPE(),user);
		return new ResultMessage("删除完成", true);
	}
	/**
	 * Get logistics companies data from DB
	 * @param request
	 * @return map which contains the searched logistics companies by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getLogisticsCompanyData")
	public @ResponseBody Map<String, Object> getLogisticsCompanyData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		
		return dictionaryService.getLogisticsCompanyByFy(param);
	}
	
	/**
	 * Add species information into DB
	 * @param request
	 * @return result message about adding species success or not
	 */
	@RequestMapping(value="/addSpecies")
	public @ResponseBody ResultMessage addSpecies(HttpServletRequest request,SpeciesEntity species){
		
		if(dictionaryService.checkSpeciesNameExists(species) == true){
			return new ResultMessage("作物名已存在！", true);
		}
		
		dictionaryService.saveSpecies(species);
		//Record the log of adding species
		logService.logForOperation(logContent.getADDVARIETY(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Edit species information into DB
	 * @param request
	 * @return result message about editing species success or not
	 */
	@RequestMapping(value="/editSpecies")
	public @ResponseBody ResultMessage editSpecies(HttpServletRequest request,SpeciesEntity species){
		
		if(dictionaryService.checkSpeciesNameExists(species) == true){
			return new ResultMessage("作物名已存在！", true);
		}
		
		dictionaryService.saveSpecies(species);
		//Record the log of editing species
		logService.logForOperation(logContent.getEDITVARIETY(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
		
	}
	
	/**
	 * Add packing unit information into DB
	 * @param request
	 * @return result message about adding packing unit success or not
	 */
	@RequestMapping(value="/addPackingUnit")
	public @ResponseBody ResultMessage addPackingUnit(HttpServletRequest request,PackingUnitEntity packingUnit){

		if(dictionaryService.checkPackingUnitExists(packingUnit)  == true){
			return new ResultMessage("包装物规格已存在！", true);
		}
		
		dictionaryService.savePackingUnit(packingUnit);
		//Record the log of adding packing unit
		logService.logForOperation(logContent.getADDPACKAGES(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Edit packing unit information into DB
	 * @param request
	 * @return result message about editing packing unit success or not
	 */
	@RequestMapping(value="/editPackingUnit")
	public @ResponseBody ResultMessage editPackingUnit(HttpServletRequest request,PackingUnitEntity packingUnit){
		
		if(dictionaryService.checkPackingUnitExists(packingUnit)  == true){
			return new ResultMessage("包装物规格已存在！", true);
		}
		
		dictionaryService.savePackingUnit(packingUnit);
		//Record the log of editing packing unit
		logService.logForOperation(logContent.getEDITPACKAGES(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Add import name information into DB
	 * @param request
	 * @return result message about adding import name success or not
	 */
	@RequestMapping(value="/addImportName")
	public @ResponseBody ResultMessage addImportName(HttpServletRequest request,ImportNameEntity importName){
		
		if(dictionaryService.checkImportNameExists(importName) == true){
			return new ResultMessage("进口名已存在！", true);
		}
		
		dictionaryService.saveImportName(importName);
		//Record the log of adding import name
		logService.logForOperation(logContent.getADDIMPORTNAME(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Edit import name information into DB
	 * @param request
	 * @return result message about editing import name success or not
	 */
	@RequestMapping(value="/editImportName")
	public @ResponseBody ResultMessage editImportName(HttpServletRequest request,ImportNameEntity importName){
		
		if(dictionaryService.checkImportNameExists(importName) == true){
			return new ResultMessage("进口名已存在！", true);
		}
		
		dictionaryService.saveImportName(importName);
		//Record the log of editing import name
		logService.logForOperation(logContent.getEDITIMPORTNAME(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Add importer information into DB
	 * @param request
	 * @return result message about adding importer success or not
	 */
	@RequestMapping(value="/addImporter")
	public @ResponseBody ResultMessage addImporter(HttpServletRequest request,ImporterEntity importer){
		
		if(dictionaryService.checkImporterExists(importer) == true){
			return new ResultMessage("进口商名称已存在！", true);
		}
		
		dictionaryService.saveImporter(importer);
		//Record the log of adding import name
		logService.logForOperation(logContent.getADDIMPORTER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
		
	}
	
	/**
	 * Edit importer information into DB
	 * @param request
	 * @return result message about editing importer success or not
	 */
	@RequestMapping(value="/editImporter")
	public @ResponseBody ResultMessage editImporter(HttpServletRequest request,ImporterEntity importer){
		
		if(dictionaryService.checkImporterExists(importer) == true){
			return new ResultMessage("进口商名称已存在！", true);
		}
		
		dictionaryService.saveImporter(importer);
		//Record the log of editing importer
		logService.logForOperation(logContent.getEDITIMPORTER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Add supplier information into DB
	 * @param request
	 * @return result message about adding supplier success or not
	 */
	@RequestMapping(value="/addSupplier")
	public @ResponseBody ResultMessage addSupplier(HttpServletRequest request,SupplierEntity supplier){
		
		if(dictionaryService.checkSupplierExists(supplier) == true){
			return new ResultMessage("供应商名称已存在！", true);
		}
		
		dictionaryService.saveSupplier(supplier);
		//Record the log of adding supplier
		logService.logForOperation(logContent.getADDSUPPLIER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
		
	}
	
	/**
	 * Edit supplier information into DB
	 * @param request
	 * @return result message about editing supplier success or not
	 */
	@RequestMapping(value="/editSupplier")
	public @ResponseBody ResultMessage editSupplier(HttpServletRequest request,SupplierEntity supplier){
		
		if(dictionaryService.checkSupplierExists(supplier) == true){
			return new ResultMessage("供应商名称已存在！", true);
		}
		
		
		dictionaryService.saveSupplier(supplier);
		//Record the log of editing supplier
		logService.logForOperation(logContent.getEDITSUPPLIER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Add sales manager information into DB
	 * @param request
	 * @return result message about added sales manager success or not
	 */
	@RequestMapping(value="/addSaleManager")
	public @ResponseBody ResultMessage addSaleManager(HttpServletRequest request,SaleManagerEntity saleManager){
		
//		if(dictionaryService.checkSaleManagerExists(saleManager) == true){
//			return new ResultMessage("销售名称已存在！", true);
//		}
		
		dictionaryService.saveSaleManager(saleManager);
		//Record the log of adding sales manager
		logService.logForOperation(logContent.getADDSALEMANAGER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Edit sales manager information into DB
	 * @param request
	 * @return result message about editing sales manager success or not
	 */
	@RequestMapping(value="/editSaleManager")
	public @ResponseBody ResultMessage editSaleManager(HttpServletRequest request,SaleManagerEntity saleManager){
		
//		if(dictionaryService.checkSaleManagerExists(saleManager) == true){
//			return new ResultMessage("销售名称已存在！", true);
//		}
		
		dictionaryService.saveSaleManager(saleManager);
		//Record the log of editing sales manager
		logService.logForOperation(logContent.getEDITSALEMANAGER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Add Logistics Company information into DB
	 * @param request
	 * @return result message about adding Logistics Company information success or not
	 */
	@RequestMapping(value="/addLogisticsCompany")
	public @ResponseBody ResultMessage addLogisticsCompany(HttpServletRequest request,LogisticCompanyEntity logisticsCompany){
		
		if(dictionaryService.checkLogisticsExists(logisticsCompany) == true){
			return new ResultMessage("物流公司名已存在！", true);
		}
		
		dictionaryService.saveLogisticsCompany(logisticsCompany);
		//Record the log of adding Logistics Company information
		logService.logForOperation(logContent.getADDLOGISTICCOMPANY(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Edit Logistics Company information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@RequestMapping(value="/editLogisticsCompany")
	public @ResponseBody ResultMessage editLogisticsCompany(HttpServletRequest request,LogisticCompanyEntity logisticsCompany){
		
		if(dictionaryService.checkLogisticsExists(logisticsCompany) == true){
			return new ResultMessage("物流公司名已存在！", true);
		}
		
		dictionaryService.saveLogisticsCompany(logisticsCompany);
		//Record the log of editing Logistics Company information
		logService.logForOperation(logContent.getEDITLOGISTICCOMPANY(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	
	/**
	 * Delete Species from DB
	 * @param request
	 * @return result message about deleting species success or not
	 */
	@RequestMapping(value="/deleteSpecies")
	public @ResponseBody ResultMessage deleteSpecies(HttpServletRequest request,@RequestBody List<SpeciesEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result= "删除成功。";
		int i = 0;
		
		for(SpeciesEntity entity:list){
			if(dictionaryService.checkSpeciesBeAssociated(entity)==true){
				i = i +1;
				//If the species cannot be deleted,then record delete failed log
				logService.logForOperation(logContent.getDELETEVARIETYFAIL(), logContent.getERRORTYPE(),user);
				continue;
			}
			dictionaryService.deleteSpecies(entity);
			//If the species cannot be deleted successfully,then record delete success log
			logService.logForOperation(logContent.getDELETEVARIETY(), logContent.getINFOTYPE(),user);
		}
		
		if(list.size() == 1 && i == 1){
			result = "删除失败，有其他数据关联。。";
		}
		if(list.size() >1 && i >1){
			result = "删除完成。其中" + i +"项有其他数据关联，未删除";
		}
		
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete packing units from DB
	 * @param request
	 * @return result message about deleting packing units success or not
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePackingUnit")
	public @ResponseBody ResultMessage deletePackingUnit(HttpServletRequest request,@RequestBody List<PackingUnitEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		int i = 0;
		for (PackingUnitEntity entity : list) {
			try {
				publicService.delEntity(PackingUnitEntity.class, entity.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联。", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_VARIETY_MSG , logContent.getINFOTYPE(),user);
 
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete import name from DB
	 * @param request
	 * @return result message about deleting import name success or not
	 */
	@RequestMapping(value="/deleteImportName")
	public @ResponseBody ResultMessage deleteImportName(HttpServletRequest request,@RequestBody List<ImportNameEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		
		int i = 0;
		for(ImportNameEntity entity:list){
			if(dictionaryService.checkImportNameBeAssociated(entity)){
				i = i + 1;
				//If the import name cannot be deleted,then record delete failed log
				logService.logForOperation(logContent.getDELETEIMPORTNAMEFAIL(), logContent.getERRORTYPE(),user);
				continue;
			}
			dictionaryService.deleteImportName(entity);
			//If the import name cannot be deleted successfully,then record delete success log
			logService.logForOperation(logContent.getDELETEIMPORTNAME(), logContent.getINFOTYPE(),user);
		}
		if(list.size() == 1 && i == 1){
			result = "删除失败，有其他数据关联。";
		}
		if(list.size() >1 && i >1){
			result = "删除完成。其中" + i +"项有其他数据关联，未删除";
		}
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete importers from DB
	 * @param request
	 * @return result message about deleting importers success or not
	 */
	@RequestMapping(value="/deleteImporter")
	public @ResponseBody ResultMessage deleteImporter(HttpServletRequest request,@RequestBody List<ImporterEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		for (ImporterEntity groups : list) {
			try {
				publicService.delEntity(ImporterEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(logContent.getDELETEIMPORTER(), logContent.getINFOTYPE(),user);
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete suppliers from DB
	 * @param request
	 * @return result message about deleting suppliers success or not
	 */
	@RequestMapping(value="/deleteSupplier")
	public @ResponseBody ResultMessage deleteSupplier(HttpServletRequest request,@RequestBody List<SupplierEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		
		for (SupplierEntity groups : list) {
			try {
				publicService.delEntity(SupplierEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(logContent.getDELETESUPPLIER(), logContent.getINFOTYPE(),user);
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete sales managers from DB
	 * @param request
	 * @return result message about deleting sales managers success or not
	 */
	@RequestMapping(value="/deleteSaleManager")
	public @ResponseBody ResultMessage deleteSaleManager(HttpServletRequest request,@RequestBody List<SaleManagerEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		for (SaleManagerEntity groups : list) {
			try {
				publicService.delEntity(SaleManagerEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
	 
		logService.logForOperation(logContent.getDELETESALEMANAGER(), logContent.getINFOTYPE(),user);
		return new ResultMessage(result, true);
	}
	
	/**
	 * Delete logistics companies from DB
	 * @param request
	 * @return result message about deleting logistics companies success or not
	 */
	@RequestMapping(value="/deleteLogisticCompany")
	public @ResponseBody ResultMessage deleteLogisticCompany(HttpServletRequest request,@RequestBody List<LogisticCompanyEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "删除成功。";
		for (LogisticCompanyEntity groups : list) {
			try {
				publicService.delEntity(LogisticCompanyEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(logContent.getDELETELOGISTICCOMPANY(), logContent.getINFOTYPE(),user);
		return new ResultMessage(result, true);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getGroupsData")
	public @ResponseBody Map<String, Object> getGroupsData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		Map<String, Object> res  = publicService.getEntitysByFy(param, GroupsEntity.class);
		return res;
	}
	
	/**
	 * Add GroupsEntity information into DB
	 * @param request
	 * @return result message about adding Logistics Company information success or not
	 * @throws  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addGroups")
	public @ResponseBody ResultMessage addGroups(HttpServletRequest request,GroupsEntity groups) {
		if(publicService.checkEntitys(GroupsEntity.class, "nameCn", groups.getNameCn(), groups.getId()) == true){
			
			return new ResultMessage("分类名称已存在！", true);
		}
		publicService.saveEntity(groups);
		//Record the log of adding Logistics Company information
		logService.logForOperation(LogContent.ADD_Groups_MSG, logContent.getINFOTYPE(),(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("新增成功", true);
	}
	/**
	 * Edit GroupsEntity information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editGroups")
	public @ResponseBody ResultMessage editGroups(HttpServletRequest request,GroupsEntity groups){
	 
		if(publicService.checkEntitys(GroupsEntity.class, "nameCn", groups.getNameCn(), groups.getId()) == true){
			
			return new ResultMessage("分类名称已存在！", true);
		}
		
		publicService.saveEntity(groups);

		logService.logForOperation(LogContent.EDIT_Groups_MSG , logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteGroups")
	public @ResponseBody ResultMessage deleteGroups(HttpServletRequest request,@RequestBody List<GroupsEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		for (GroupsEntity groups : list) {
			try {
				publicService.delEntity(GroupsEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联。", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_VARIETY_MSG , logContent.getINFOTYPE(),user);
		return new ResultMessage("删除完成", true);
	}
	
	
	
	/**
	 * @param request
	 * @param city
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addAreaEntity")
	public @ResponseBody ResultMessage CityEntity(HttpServletRequest request, AreaEntity entity) {
		if(publicService.checkEntitys(AreaEntity.class, "nameCn", entity.getNameCn(), entity.getId()) == true){
			return new ResultMessage("名称已存在！", true);
		}
		publicService.saveEntity(entity);
		//Record the log of adding Logistics Company information
		logService.logForOperation(LogContent.ADD_Groups_MSG, logContent.getINFOTYPE(),(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("新增成功", true);
	}
	/**
	 * Add AreaEntity or CityEntity or ProvinceEntity  information into DB
	 * @param request
	 * @param groups
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addCityEntity")
	public @ResponseBody ResultMessage CityEntity(HttpServletRequest request, CityEntity city) {
		if(publicService.checkEntitys(CityEntity.class, "nameCn", city.getNameCn(), city.getId()) == true){
			return new ResultMessage("名称已存在！", true);
		}
		publicService.saveEntity(city);
		//Record the log of adding Logistics Company information
		logService.logForOperation(LogContent.ADD_Groups_MSG, logContent.getINFOTYPE(),(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("新增成功", true);
	}
	/**
	 * Add AreaEntity or CityEntity or ProvinceEntity  information into DB
	 * @param request
	 * @param groups
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addProvinceEntity")
	public @ResponseBody ResultMessage place(HttpServletRequest request,  ProvinceEntity province) {
		if(publicService.checkEntitys(ProvinceEntity.class, "nameCn", province.getNameCn(), province.getId()) == true){
			return new ResultMessage("名称已存在！", true);
		}
		publicService.saveEntity(province);
		//Record the log of adding Logistics Company information
		logService.logForOperation(LogContent.ADD_Province_MSG, logContent.getINFOTYPE(),(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("新增成功", true);
	}
	 
	/**
	 * Edit ProvinceEntity   information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editProvinceEntity")
	public @ResponseBody ResultMessage editProvinceEntity(HttpServletRequest request,ProvinceEntity entity){
	 
		if(publicService.checkEntitys(ProvinceEntity.class, "nameCn", entity.getNameCn(), entity.getId()) == true){
			
			return new ResultMessage("名称已存在！", true);
		}
		
		publicService.saveEntity(entity);

		logService.logForOperation(LogContent.EDIT_Groups_MSG , logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Edit CityEntity   information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editCityEntity")
	public @ResponseBody ResultMessage editCityEntity(HttpServletRequest request,CityEntity entity){
	 
		if(publicService.checkEntitys(CityEntity.class, "nameCn", entity.getNameCn(), entity.getId()) == true){
			
			return new ResultMessage("名称已存在！", true);
		}
		
		publicService.saveEntity(entity);

		logService.logForOperation(LogContent.EDIT_Groups_MSG , logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	/**
	 * Edit AreaEntity   information into DB
	 * @param request
	 * @return result message about editing Logistics Company information success or not
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editAreaEntity")
	public @ResponseBody ResultMessage editAllPlace(HttpServletRequest request,AreaEntity entity){
	 
		if(publicService.checkEntitys(AreaEntity.class, "nameCn", entity.getNameCn(), entity.getId()) == true){
			
			return new ResultMessage("分类名称已存在！", true);
		}
		
		publicService.saveEntity(entity);

		logService.logForOperation(LogContent.EDIT_Groups_MSG , logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	/** del AreaEntity   information into DB
	 * @param request
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteAreaEntity")
	public @ResponseBody ResultMessage deleteAreaEntity(HttpServletRequest request,@RequestBody List<AreaEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		for (AreaEntity groups : list) {
			try {
				publicService.delEntity(AreaEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_AreaEntity_MSG , logContent.getINFOTYPE(),user);
		return new ResultMessage("删除完成", true);
	}
	/** del AreaEntity or CityEntity or ProvinceEntity  information into DB
	 * @param request
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteCityEntity")
	public @ResponseBody ResultMessage deleteCityEntity(HttpServletRequest request,@RequestBody List<CityEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		for (CityEntity groups : list) {
			try {
				publicService.delEntity(CityEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_VARIETY_MSG , logContent.getINFOTYPE(),user);
		return new ResultMessage("删除完成", true);
	}
	/** del    ProvinceEntity  information into DB
	 * @param request
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteProvinceEntity")
	public @ResponseBody ResultMessage deleteAllPlace(HttpServletRequest request,@RequestBody List<ProvinceEntity> list){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		for (ProvinceEntity groups : list) {
			try {
				publicService.delEntity(ProvinceEntity.class, groups.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("删除失败,有其他数据关联", true);
			}
		}
		logService.logForOperation(LogContent.DELETE_Province_MSG , logContent.getINFOTYPE(),user);
		return new ResultMessage("删除完成", true);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getAreaEntityData")
	public @ResponseBody Map<String, Object> getAreaEntityData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		Map<String, Object> res  = publicService.getEntitysByFy(param, AreaEntity.class);
		return res;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCityEntityData")
	public @ResponseBody Map<String, Object> getCityEntityData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		Map<String, Object> res  = publicService.getEntitysByFy(param, CityEntity.class);
		return res;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getProvinceEntityData")
	public @ResponseBody Map<String, Object> getProvinceEntityData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		Map<String, Object> res  = publicService.getEntitysByFy(param, ProvinceEntity.class);
		return res;
	}
}
