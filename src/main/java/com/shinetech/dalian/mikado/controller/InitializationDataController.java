package com.shinetech.dalian.mikado.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.GroupsEntity;
import com.shinetech.dalian.mikado.entity.PackingUnitEntity;
import com.shinetech.dalian.mikado.entity.ProvinceEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.util.ExcelUtil;
import com.shinetech.dalian.mikado.util.StringToDateConverter;


/**
 * @author 82578
 *   只有数据初始化才会有用的
 */
//@Controller
@RequestMapping(value="/dataSynchronization")
public class InitializationDataController {
	

	@Autowired
	BaseDao baseDao;
 
	/**
	 * 初始化
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/starInitGroup" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitGroup(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,0);//sheet 1
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 for (int i = 0; i < psList.size(); i++) {
			 List<Object>  psListStr = new Gson().fromJson( psList.get(i).toString(), new TypeToken<List<Object>>(){}.getType());  
			 GroupsEntity groupsEntity = new GroupsEntity();
			 groupsEntity.setNameCn(psListStr.get(0).toString());
			 groupsEntity.setNameEn(psListStr.get(1).toString());
			 baseDao.save(groupsEntity);
			
		}
		return 0;
	}
	
	@RequestMapping(value="/starInitSpeciesEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitSpeciesEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,1);;//sheet 2
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<GroupsEntity> groupList  = baseDao.getAll(GroupsEntity.class);
		 Map<String,GroupsEntity> nameAndId = new HashMap<String,GroupsEntity>();
		 
		for (GroupsEntity ge : groupList) {
			nameAndId.put(ge.getNameCn(), ge);// Group的 名字和ID
		}
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//A, 1.00, onion, 洋葱, 洋葱, 叶菜类

			String[] strArray =  StringUtils.split(dataCell,",");
			strArray = trimAllArray(strArray);
			 SpeciesEntity sp =new SpeciesEntity();
			 sp.setCropCode(strArray[0]);;//删除空格
			 sp.setNo(String.valueOf(Float.valueOf(strArray[1].toString()).intValue()));
			 sp.setSpeciesNameEn(strArray[2]);
			 sp.setSpeciesNameCn(strArray[3]);
			 sp.setSpeciesNameCn2(strArray[4]);
			 sp.setGroup(nameAndId.get(strArray[5]));
			 baseDao.save(sp);
		}
		return 0;
	}
	
	
	@RequestMapping(value="/starInitVarietyEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitVarietyEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,2);;//sheet 2
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<SpeciesEntity> entityList  = baseDao.getAll(SpeciesEntity.class);
		 Map<String,SpeciesEntity> nameAndObj = new HashMap<String,SpeciesEntity>();
		 
		for (SpeciesEntity ge : entityList) {
			nameAndObj.put(ge.getCropCode()+ge.getNo(), ge);// VarietyEntity的 略号+No
		}
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//A001, A, 1, Amposta , AMPOSTA, 2012/07以前

			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);;//删除空格
			 VarietyEntity ve = new VarietyEntity();
			 ve.setVarietyCode(strArray[0]);//略号
			 ve.setCommercialName(strArray[3]);
			 ve.setVarietyName(strArray[4]);
			 ve.setBeginDate(new StringToDateConverter("yyyy/MM").convert(strArray[5].replace("以前", "")));
			 ve.setSpecies(nameAndObj.get(strArray[1]+strArray[2]));
			 baseDao.save(ve);
		}
		return 0;
	}
	
	
	@RequestMapping(value="/starInitAreaEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitAreaEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,3);;//sheet 3
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//[DM, 大连总部,  Dalian Head Office]

			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);;//删除空格
			 AreaEntity ae = new AreaEntity();
			 ae.setRegionCode(strArray[0]);
			 ae.setNameCn(strArray[1]);
			 ae.setNameEn(strArray[2]);
			 baseDao.save(ae);
		}
		return 0;
	}
	
	
	@RequestMapping(value="/starInitProvinceEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitProvinceEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,4);;//sheet 4
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<AreaEntity> entityList  = baseDao.getAll(AreaEntity.class);
		 Map<String,AreaEntity> nameAndObj = new HashMap<String,AreaEntity>();
			for (AreaEntity ge : entityList) {
				nameAndObj.put(ge.getRegionCode(), ge);
			}
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//DM	AC	全国
			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);//删除空格
			 ProvinceEntity pe = new ProvinceEntity();
			 pe.setArea(nameAndObj.get(strArray[0]));
			 pe.setProvinceCode(strArray[1]);
			 pe.setNameCn(strArray[2]);
			 baseDao.save(pe);
		}
		return 0;
	}
	@RequestMapping(value="/starInitCityEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitCityEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,5);;//sheet 5
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<ProvinceEntity> entityList  = baseDao.getAll(ProvinceEntity.class);
		 Map<String,ProvinceEntity> nameAndObj = new HashMap<String,ProvinceEntity>();
			for (ProvinceEntity ge : entityList) {
				nameAndObj.put(ge.getProvinceCode(), ge);
			}
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//AC	寿光
			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);//删除空格
			 CityEntity pe = new CityEntity();
			 pe.setProvince(nameAndObj.get(strArray[0]));
			 pe.setNameCn(strArray[1]);
			 baseDao.save(pe);
		}
		return 0;
	}
	
	@RequestMapping(value="/starInitSaleManagerEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitSaleManagerEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,6);;//sheet 6
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<AreaEntity> entityList  = baseDao.getAll(AreaEntity.class);
		 Map<String,AreaEntity> nameAndObj = new HashMap<String,AreaEntity>();
			for (AreaEntity ge : entityList) {
				nameAndObj.put(ge.getRegionCode() ,ge);
			}
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");
			 dataCell =dataCell.replace("]", "");//LFZ	刘发忠	DM
			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);//删除空格
			 SaleManagerEntity pe = new SaleManagerEntity();
			 pe.setSMCode(strArray[0]);
			 pe.setName(strArray[1]);
			 pe.setArea(nameAndObj.get(strArray[2]));
			 baseDao.save(pe);
		}
		return 0;
	}
	
	@RequestMapping(value="/starInitPackingUnitEntity" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  starInitPackingUnitEntity(HttpServletRequest request){
		File file = new File("E://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,7);;//sheet 6
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 /*List<AreaEntity> entityList  = baseDao.getAll(AreaEntity.class);
		 Map<String,AreaEntity> nameAndObj = new HashMap<String,AreaEntity>();
			for (AreaEntity ge : entityList) {
				nameAndObj.put(ge.getRegionCode() ,ge);
			}*/
		 for (int i = 0; i < psList.size(); i++) {
			 String dataCell = psList.get(i).toString();
			 dataCell = dataCell.replace("[", "");//;规格略号	规格名称	单位	长L	宽W	高H	用途
			 dataCell =dataCell.replace("]", "");//   A	    10g彩色袋子	袋	   16.5	11.5   --	    十字花科10g,西甜瓜100粒,200粒
			 String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);//删除空格
			 PackingUnitEntity pe = new PackingUnitEntity();
			 if(strArray[0].indexOf('.') == -1){
				 pe.setSpecificationCode(strArray[0]);
			 }else{
				 pe.setSpecificationCode(String.valueOf(Float.valueOf(strArray[0].toString()).intValue()));
			 }
			 pe.setSpecificationName(strArray[1]);
			 pe.setSpecifications(strArray[2]);
			 pe.setLength(strArray[3]);
			 pe.setWidth(strArray[4]);
			 pe.setHeight(strArray[5]);
			 pe.setPurpose(strArray[6]);
			 baseDao.save(pe);
		}
		return 0;
	}
	@RequestMapping(value="/delCustomerJpaDao" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  delCustomerJpaDao(HttpServletRequest request){
		 List<CustomerEntity> cityList = baseDao.getAll(CustomerEntity.class);
		 for(CustomerEntity c :cityList){
			 try {
				baseDao.delete(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return 0;
	}
	@RequestMapping(value="/CustomerJpaDao" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int  CustomerJpaDao(HttpServletRequest request){
		File file = new File("C://data.xlsx"); 
		 ArrayList<ArrayList<Object>> data = ExcelUtil.readExcel2007(file,0);//sheet 4
		 List<Object> psList = new Gson().fromJson(new Gson().toJson(data).toString(), new TypeToken<List<Object>>(){}.getType());  
		 List<String> objStr = psList.stream().map(Object::toString).map(str->str.replace("[", "").replace("]", "")).collect(Collectors.toList());
		 List<CityEntity> cityList = baseDao.getAll(CityEntity.class);
		 
		 
		 List<SaleManagerEntity> saleManagerEntityList = baseDao.getAll(SaleManagerEntity.class);
		 Map<String,CityEntity> cityMap = cityList.stream().collect(Collectors.toMap(CityEntity::getNameCn, cityEntity->cityEntity));
		 Map<String,SaleManagerEntity> saleManagerEntityMap = saleManagerEntityList.stream().collect(Collectors.toMap(SaleManagerEntity::getSMCode, obj->obj));
 
		 for (int i = 0; i < objStr.size(); i++) {
			 String dataCell = objStr.get(i);
			String[] strArray =  StringUtils.split(dataCell,",");
			 trimAllArray(strArray);
 
			 CustomerEntity customerEntity = new CustomerEntity();
			 customerEntity.setCompany(strArray[0]);
			 customerEntity.setSimpleName(strArray[1]);
			 customerEntity.setContact(strArray[2]);
			 if(StringUtils.isNotBlank(strArray[3]))
			 customerEntity.setTelephone(delSym(strArray[3]));
			 customerEntity.setAddress(strArray[4]);
			 customerEntity.setEmail(strArray[5]);
			 customerEntity.setCity(cityMap.get(strArray[8]));
			 customerEntity.setSaleManager(findSaleManagerEntity(saleManagerEntityList,strArray[9]));
			
			 baseDao.save(customerEntity);
			 }
		return 0;
	}
	public static void main(String[] args) {
		String  str = "151.056456";
		
	}
	public static String delSym(String str){
		if(StringUtils.isNotBlank(str)){
			if(str.indexOf(".")>0){
				return  str = str.substring(0,str.indexOf("."));
			}
		}
		return str;
	}
	public SaleManagerEntity findSaleManagerEntity(	 
			 List<SaleManagerEntity> saleManagerEntityList ,String str){
		for(SaleManagerEntity se:saleManagerEntityList){
			if(StringUtils.equals(StringUtils.trim(se.getSMCode()), StringUtils.trim(str)) ){
				return se;
			}
		}
		return  null;
	}
	public static String[] trimAllArray (String[] strArray ){
		for (int i = 0; i < strArray.length; i++) {
			strArray[i] = StringUtils.trim(strArray[i]);
		}
		return strArray;
	}
		 
}
