package com.shinetech.dalian.mikado.controller;


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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @RequestMapping(value="/export",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<byte[]> export(@RequestParam(value = "packageInfoID") Integer packageInfoID, HttpServletResponse response,@RequestParam(defaultValue = "QrCode") String packageName){
        return packageInfoService.export(packageInfoID,response,packageName);
    }

    @RequestMapping(value="/getPackageInfoList",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> getPackageInfoList(@RequestParam(value = "offset") Integer startPosition,@RequestParam(value = "limit")  Integer maxResult){
        return packageInfoService.getPackageInfoList(startPosition,maxResult);
    }

    @RequestMapping(value="/queryPackageInfoList",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> getPackageInfoList(@RequestParam(value = "offset") Integer startPosition,@RequestParam(value = "limit")  Integer maxResult,String packageItemName){
        return packageInfoService.queryPackageInfoList(startPosition,maxResult,packageItemName);
    }

    @RequestMapping(value="/add",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, String> add(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTime,@RequestParam(defaultValue = "qrCode") String packageItemName,Integer qty){
        System.out.println(packageItemName);
        return packageInfoService.add(createTime,packageItemName,qty);
    }

    @RequestMapping(value="/del",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, String> del(String packageInfoID){
        return packageInfoService.del(packageInfoID);
    }

    @RequestMapping(value="/edit",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, String> edit(String packageInfoID,String packageNewName){
        return packageInfoService.edit(packageInfoID,packageNewName);
    }


    @RequestMapping(value="/getById",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public @ResponseBody PackageInfoEntity getById(String packageInfoID){
        return packageInfoService.getById(packageInfoID);
    }

}
