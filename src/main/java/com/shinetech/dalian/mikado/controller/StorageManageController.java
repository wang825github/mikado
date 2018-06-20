package com.shinetech.dalian.mikado.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.PackagingService;
import com.shinetech.dalian.mikado.service.SeedService;
/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/storage")
public class StorageManageController {
	@Autowired
	private PackagingService packagingService;
	@Autowired
	private SeedService seedService;
	
	/**
	 * Direct to storage manage page
	 * @param request
	 * @return return storage manage page if user is not null,else return to login page
	 */
	@RequestMapping(value="/manage")
	public String storageManagePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(user == null){
			return "redirect:/";
		}
		List<SpeciesEntity> speciesList = seedService.getSpeciesFromSeed();
		List<PackingUnitEntity> packingUnitList = packagingService.getPackingUnitsFromStorage();
		
		request.setAttribute("speciesList", speciesList);
		request.setAttribute("packingUnitList", packingUnitList);
		
		return "jsp/storage-manage";
	}
	/**
	 * Get commercial name from seed table by species id
	 * @param request
	 * @param speciesId
	 * @return commercial name string list
	 */
	@RequestMapping(value="/getConmercialName")
	public @ResponseBody List<String> getConmercialName(HttpServletRequest request,Integer speciesId){
		List<String> conmercialnameList = seedService.getConmercialNameBySpecies(speciesId);
		return conmercialnameList;
	}
	
	/**
	 * Get storage info from DB by search conditions
	 * @param request
	 * @return map which contains the searched storage data by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getStorageInfo",produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object>  getStorageInfo(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String speciesId = request.getParameter("speciesId");
		String conmercial_name = request.getParameter("conmercial_name");
		String packingUnitId = request.getParameter("packingUnitId");
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("speciesId", speciesId);
		param.put("conmercial_name", conmercial_name);
		param.put("packingUnitId", packingUnitId);
		
		return packagingService.selectStorageSummary(param);
	}
}
