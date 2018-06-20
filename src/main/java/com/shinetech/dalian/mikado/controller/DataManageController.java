package com.shinetech.dalian.mikado.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.ImporterEntity;
import com.shinetech.dalian.mikado.entity.LogisticCompanyEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SupplierEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.CustomerService;
import com.shinetech.dalian.mikado.service.DataManageService;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.SeedService;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.ResultMessage;
/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/data")
public class DataManageController extends BaseController{
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DataManageService dataManageService;
	@Autowired
	MakeNumbersDetails makeNumberDetails;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	private SeedService seedService;
	
	/**
	 * Direct to order management page
	 * @param request
	 * @return the abstract URL of order management page
	 */
	@RequestMapping(value="/manage")
	public String deliveryManagePage(HttpServletRequest request){
		List<LogisticCompanyEntity> ligisticsCompanyLists = dictionaryService.getLogisticsCompanyList();
		List<CustomerEntity> customerLists = customerService.getCustomerList();
		List<ImporterEntity> importerLists = dictionaryService.getImporterList();
		List<SupplierEntity> supplierLists = dictionaryService.getSupplierList();
		List<SaleManagerEntity> salesLists = dictionaryService.getSaleManagerList();
		List<CityEntity> cityLists = dictionaryService.getCityLists();
		
		request.setAttribute("ligisticsCompanyLists", ligisticsCompanyLists);
		request.setAttribute("customerLists", customerLists);
		request.setAttribute("importerLists", importerLists);
		request.setAttribute("supplierLists", supplierLists);
		request.setAttribute("salesLists", salesLists);
		request.setAttribute("cityLists", cityLists);
		
		return "jsp/data-manage";
	}
	
	/**
	 * Get orders from DB by search conditions
	 * If search conditions are empty,get all orders
	 * @param request
	 * @return map which contains the searched orders by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getDataManageData")
	public @ResponseBody Map<String, Object> getDataManageData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String conmercialName = request.getParameter("conmercialName");
		String cityId = request.getParameter("cityId");
		String salesManagerId = request.getParameter("salesManager.id");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("conmercialName", conmercialName);
		param.put("salesManagerId", salesManagerId);
		param.put("cityId", cityId);
		
		return dataManageService.getDataManageByPagination(param);
	}
	
	/**
	 * Update order status
	 * @param request
	 * @param dataManage
	 * @return result message about updating orders status success or not
	 */
	@RequestMapping(value="/updateStatus",produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResultMessage updateStatus(HttpServletRequest request,DataManageEntity dataManage){
		
		dataManageService.editStatus(dataManage);
		logService.logForOperation(logContent.getUPDATEDATAMANAGE(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("更新成功", true);
	}
	
	/**
	 * Edit order information
	 * @param request
	 * @param dataManage
	 * @return result message about editing order information success or not
	 */
	@RequestMapping(value="/edit",produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResultMessage editDataManage(HttpServletRequest request,DataManageEntity dataManage){
		
		dataManageService.editDataManage(dataManage);
		
		logService.logForOperation(logContent.getEDITDATAMANAGE(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		
		return new ResultMessage("编辑成功", true);
	}
	
	/**
	 * Export orders information by report type
	 * @param request
	 * @param response
	 * @param conmercialName
	 * @param cityId
	 * @param saleId
	 * @param exportType
	 * @throws IOException
	 */
	@RequestMapping(value="/exportExcel",produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void exportExcel(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="conmercialName",required = true) String conmercialName,
			@RequestParam(value="cityId",required = true) Integer cityId,
			@RequestParam(value="saleId",required = true) Integer saleId,
			@RequestParam(value="exportType",required = true) Integer exportType
			) throws IOException {
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		
		try {
			String fileName = "";
			/*
			 * The excel file name is based on export report type
			 */
			if (exportType == 1) {
				fileName = "Processing Lot";
			}
			if (exportType == 2) {
				fileName = "Sale Information";
			}
			if (exportType == 3) {
				fileName = "Logistics Information";
			}
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			Workbook wb = dataManageService.exportExcel(conmercialName, cityId,saleId,exportType);
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			/*
			 * set parameters of response.so that the excel is opened successfully
			 */
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
			
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				//if export excel successfully,then record log of exporting excel to log table
				logService.logForOperation(logContent.getEXPORTEXCEL(), logContent.getINFOTYPE(),user);
			} catch (final IOException e) {
				//if export excel failed,then record failed log of exporting excel to log table
				logService.logForOperation(logContent.getEXPORTEXCELFAIL(), logContent.getERRORTYPE(),user);
				throw e;
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		} catch (Exception e1) {
			//if there is some exception happened when exporting excel,record failed log 
			logService.logForOperation(logContent.getEXPORTEXCELFAIL(), logContent.getERRORTYPE(),user);
			e1.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/generateLotNumber",method = RequestMethod.GET)
	   public @ResponseBody JsonObject generateLotNumber(HttpServletRequest request,Integer seedID) throws NoSuchAlgorithmException{
		    SeedEntity seedEntity=seedService.getSeedById(seedID);
		    
			String lotNumber=dataManageService.getLotNumber(getYear()+seedEntity.getSpecies().getCropCode());
			
			JsonObject json = new JsonObject();
			json.addProperty("lotNumber", lotNumber);
			return json;
	   }
	
}
