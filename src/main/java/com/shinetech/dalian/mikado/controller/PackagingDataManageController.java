package com.shinetech.dalian.mikado.controller;

import com.shinetech.dalian.mikado.dao.PackageInfoDao;
import com.shinetech.dalian.mikado.entity.PackageInfoEntity;
import com.shinetech.dalian.mikado.service.PackageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/packagingData")
public class PackagingDataManageController {

    @Autowired
    PackageInfoService packageInfoService;

    @RequestMapping(value="/manage")
    public String deliveryManagePage(){
        return "jsp/packaging-data-manage";
    }

    @RequestMapping(value="/getPackageInfoList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody Map<String, Object> getPackageInfoList(Integer startPosition, Integer maxResult){
        return packageInfoService.getPackageInfoList(startPosition,maxResult);
    }

    @RequestMapping(value="/add",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody Map<String, String> add(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTime, String packageItemName,Integer qty){
        return packageInfoService.add(createTime,packageItemName,qty);
    }
}
