package com.shinetech.dalian.mikado.service;

import com.shinetech.dalian.mikado.dao.PackageInfoDao;
import com.shinetech.dalian.mikado.entity.PackageInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PackageInfoService {
    @Autowired
    PackageInfoDao packageInfoDao;

    public Map<String, Object> getPackageInfoList(Integer startPosition, Integer maxResult) {
        Map<String, Object> result = new HashMap<>();
        result.put("total",packageInfoDao.getPackageInfoListSize());
        result.put("rows",packageInfoDao.getPackageInfoList(startPosition,maxResult));
        return result;
    }

    public Map<String,String> add(Date createTime,String packageItemName,Integer qty){
        Map<String, String> result = new HashMap<>();
        PackageInfoEntity packageInfoEntity = new PackageInfoEntity(createTime,packageItemName,qty);
        try {
            packageInfoDao.savePackageInfoEntity(packageInfoEntity);
            result.put("Success","新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("Fail","新增失败");
        }
        return result;
    }
}
