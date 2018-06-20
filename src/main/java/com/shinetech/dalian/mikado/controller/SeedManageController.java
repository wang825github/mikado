package com.shinetech.dalian.mikado.controller;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.ImportNameEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.service.DataManageService;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.SeedService;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.ResultMessage;
/**
 * @author Justin
 *
 */
@Controller
@RequestMapping("/seed")
public class SeedManageController extends BaseController{
	
	@Autowired
	private DataManageService dataManageService;
	@Autowired
	private SeedService seedService;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private MakeNumbersDetails makeNumberDetails;
	
	/**
	 * Direct to merchandise management page
	 * @param request
	 * @return return login page if user is null,else return merchandise manage page
	 */
	@RequestMapping(value="/manage" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public String  seedMangePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		
		if(user == null){
			return "redirect:/";
		}
		//弃用
//		List<ImportNameEntity> importNameLists = dictionaryService.getImportNameList();
		List<VarietyEntity> varietyList = dictionaryService.getVarietyList();
		List<ImporterEntity> importerLists = dictionaryService.getImporterList();
		List<SupplierEntity> supplierLists = dictionaryService.getSupplierList();
		List<SpeciesEntity> speciesLists = dictionaryService.getSpeciesList();
		
//		request.setAttribute("importNameLists", importNameLists);
		request.setAttribute("varietyList", varietyList);
		request.setAttribute("importerLists", importerLists);
		request.setAttribute("supplierLists", supplierLists);
		request.setAttribute("speciesLists", speciesLists);
		
		return "jsp\\seed-manage";
	}
	
	/**
	 * Get seed data from DB by search conditions
	 * if search conditions are empty,get all seed data
	 * @param request
	 * @return map which contains the searched seeds by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getSeedData")
	public @ResponseBody Map<String, Object> getSeedData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String speciesName = request.getParameter("speciesName");
		String importNumber = request.getParameter("importNumber");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("speciesName", speciesName);
		param.put("importNumber", importNumber);
		
		return seedService.getSeedByPagination(param);
	}
	
//	/**
//	 * Get import name list from species table by species id
//	 * @param request
//	 * @param speciesId
//	 * @return import name list
//	 */
//	@RequestMapping(value="/getImportName")
//	public @ResponseBody List<ImportNameEntity> getImportName(HttpServletRequest request,Integer speciesId){
//		request.setAttribute("importNameLists", dictionaryService.getImportNameBySpeciesId(speciesId));
//		return dictionaryService.getImportNameBySpeciesId(speciesId);
//	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	} 
	
	@RequestMapping(value = "/add")
	public @ResponseBody ResultMessage addSeed(HttpServletRequest request,
			SeedEntity seed) {
		seedService.saveSeed(seed);
		//record add seed log into DB
		logService.logForOperation(logContent.getADDSEED(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("新增成功", true);
	}
	
	 @RequestMapping(value="/generateLotNumber",method = RequestMethod.GET)
	   public @ResponseBody JsonObject generateLotNumber(HttpServletRequest request,Integer speciesID) throws NoSuchAlgorithmException{
//		 	VarietyEntity varietyEntity=dataManageService.getVariety(verietyID);
		 	SpeciesEntity speciesEntity = dictionaryService.getSpeciesById(speciesID);
//			String lotNumber=seedService.getLotNumber(getYear()+varietyEntity.getSpecies().getCropCode());
		 	String lotNumber = seedService.getLotNumber(getYear()+speciesEntity.getCropCode());
			
			JsonObject json = new JsonObject();
			json.addProperty("lotNumber", lotNumber);
			return json;
	   }

	@RequestMapping(value = "/edit")
	public @ResponseBody ResultMessage editSeed(HttpServletRequest request,
			SeedEntity seed) {
//		SpeciesEntity species = dictionaryService.getSpeciesById(seed.getSpecies().getId());
//		ImportNameEntity importName = dictionaryService.getImportNameById(seed.getImportName().getId());
//		seed.setSpecies(species);
//		seed.setImportName(importName);
		if(seed.getWeight() != null){
			seed.setSurplusWeight(seed.getWeight());
		}
		seedService.saveSeed(seed);
		//record edit seed log into DB
		logService.logForOperation(logContent.getEDITSEED(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("编辑成功。", true);
	}
	
	@RequestMapping(value = "/update")
	public @ResponseBody ResultMessage updateSurplusWeight(HttpServletRequest request,
			SeedEntity seed) {
		
		seedService.updateSurplusWeight(seed);
		//record edit seed log into DB
		logService.logForOperation(logContent.getUPDATE_SURPLUS_WEIGHT(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage("编辑成功。", true);
	}
	
	@RequestMapping(value="/delete")
	public @ResponseBody ResultMessage deleteSeed(HttpServletRequest request,@RequestBody List<Integer> list){
		String result = "删除成功。";
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		
		int i = 0;
		/*
		 * Delete seeds by id circulated
		 * if seed is associated by other data, then it cannot be deleted
		 */
		for(Integer id:list){
			if(seedService.checkSeedBeAssociated(id) == true){
				i = i +1;
				//record delete seed failed log into DB
				logService.logForOperation(logContent.getDELETESEEDFAIL(), logContent.getERRORTYPE(),user);
				continue;
			}
			
			seedService.deleteSeedById(id);
			//record delete seed success log into DB
			logService.logForOperation(logContent.getDELETESEED(), logContent.getINFOTYPE(),user);
		}
		if(list.size() == 1 && i == 1){
			result = "该种子已生成产品，不能删除！";
		}
		if(list.size() >1 && i >1){
			result = "删除完成。其中" + i +"项已生成产品，未删除";
		}
		return new ResultMessage(result, true);
	}
	
}
