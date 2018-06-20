package com.shinetech.dalian.mikado.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.entity.PackagingEntity;

/**
 * 检查是否有重复的
 * @author WangHao
 *
 */
//@Controller
//@RequestMapping(value="/QRcodeCompare")
public class QRcodeCompare {
	@Autowired
	BaseDao baseDao;
	
	@RequestMapping(value="/getCustomerData")
	public @ResponseBody String getCustomerData(HttpServletRequest request) throws IOException{
		String hql = " From PackagingEntity where status=0 ";
		
//		List <PackagingEntity> packList = baseDao.getAll(PackagingEntity.class);
		
		List <PackagingEntity> packList = baseDao.executeSQL(hql, PackagingEntity.class);
		List<String> qrCodeList =  packList.stream().map(o ->o.getqRCode()).collect(Collectors.toList());
		List<String> qrCodeListFile = getQRCodeList();
		qrCodeListFile.removeAll(qrCodeList);
		System.out.println("qrCodeListFile : "+qrCodeListFile.size());
		return "";
	}
	
	public List<String>   getQRCodeList() throws IOException{
		FileInputStream inputStream = new FileInputStream("C:\\Users\\WangHao\\Desktop\\雪婷.txt");  
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
        List<String> strSET = new ArrayList<String>(); 
        String str = null;  
        while((str = bufferedReader.readLine()) != null)  { 
        	strSET.add(str);
        }  
          
        inputStream.close();  
        bufferedReader.close();  
        System.out.println(strSET.size());
        return strSET;
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = new FileInputStream("C:\\Users\\WangHao\\Desktop\\雪婷.txt");  
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
        Set<String> strSET = new HashSet<String>(); 
        List<String> strList = new ArrayList<String>();
 
        String str = null;  
        while((str = bufferedReader.readLine()) != null)  
        {  
        	strList.add(str);
        }  
        strList.stream().forEach(o ->{
        	if(strSET.contains(o)) System.out.println(o);
        	else strSET.add(o);
        });
        //close  
        inputStream.close();  
        bufferedReader.close();
        System.out.println("strSET :"+strSET.size());
        
 
	}

}
