package com.shinetech.dalian.mikado.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.ProductionsDao;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.DataManageService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.TrackingWebService;


@Controller
@RequestMapping(value="/dataSynchronization")
public class DataSynchronizationController {
	@Autowired
	private DataManageService dataManageService;
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private ProductionsDao productionsDao;
	@Autowired
	TrackingWebService trackingWebService;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	BaseDao baseDao;
	@RequestMapping(value="/view" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getview(){
		ModelAndView mv = new ModelAndView("jsp\\data-syn");
		return mv;
	}
	
	/**
	 * 
	 * @param request
	 * @param synModel 0 Batch synchronization 1 Is through the unit identification code
	 * @param code  identification code
	 * @return
	 */
	@RequestMapping(value="/startSyn" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  startSyn(HttpServletRequest request,
			@RequestParam(value="synModel") int synModel,@RequestParam(value="code") String code){
		DataManageEntity dm = new DataManageEntity();
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(synModel == 0 ){
			//0 Batch synchronization
			  dm = dataManageDao.getDataManageByLotNumbers(code);
			if(dm == null)
				return 2;// The product has not been shipped yet
		}else{
			ProductionsEntity pe= productionsDao.getProductionsByIDCode(code);
			if(pe == null){
				return 3;// The product has not been shipped yet
			}
			if(pe.getDataManageId() == null){
				return 3;// The product has not been shipped yet
			}
			dm = dataManageDao.getDataManageById(pe.getDataManageId());
			dm.setProductions(null);//Empty everything else
			List<ProductionsEntity> productions = new ArrayList<ProductionsEntity>(); // 产品信息
			productions.add(pe);
			dm.setProductions(productions);
		}
		//Start synchronizing data
		int res = trackingWebService.saveTrackingWeb(dm);
		//Insert Log into the database
		if(res == 0){
			logService.logForOperation(logContent.getDATASYNSUCESS(), logContent.getINFOTYPE(), user);
		}
		else{
			logService.logForOperation(logContent.getDATASYNFAILED() ,logContent.getERRORTYPE(), user);
		}
		return res;
	}
	@RequestMapping(value="/startSyns" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<DataManageEntity>   startSyns(HttpServletRequest request){
		List<DataManageEntity> dm = baseDao.getAll(DataManageEntity.class);
		return dm;
	}
	@RequestMapping(value="/test2",produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String customerManagePage(HttpServletRequest request,@RequestParam(value="id") String id){
		return id;
	}
}
