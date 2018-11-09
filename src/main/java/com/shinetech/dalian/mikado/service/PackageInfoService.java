package com.shinetech.dalian.mikado.service;

import com.google.gson.Gson;
import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.PackageInfoDao;
import com.shinetech.dalian.mikado.dao.PackageQrCodeEntityDao;
import com.shinetech.dalian.mikado.entity.PackageInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class PackageInfoService {
    @Value("#{tracking['url']}")
    String trackWebUrl;
    @Autowired
    PackageInfoDao packageInfoDao;
    @Autowired
    PackageQrCodeEntityDao packageQrCodeEntityDao;
    @Autowired
    private BaseDao baseDao;
    public   Map<String, Object> getPackageInfoList(Integer startPosition, Integer maxResult) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",packageInfoDao.getPackageInfoListSize());
        List<PackageInfoEntity> packageInfoEntities = packageInfoDao.getPackageInfoList(startPosition,maxResult);
        result.put("rows",packageInfoEntities);
        return result;
    }

    public   Map<String, Object> queryPackageInfoList(Integer startPosition, Integer maxResult,String packageItemName) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",packageInfoDao.getPackageInfoListSizeByPackageItemName(packageItemName));
        List<PackageInfoEntity> packageInfoEntities = packageInfoDao.getPackageInfoList(startPosition,maxResult,packageItemName);
        result.put("rows",packageInfoEntities);
        return result;
    }

    public   Map<String,String> add(Date createTime, String packageItemName, Integer qty){
        Map<String, String> result = new HashMap<>();
        PackageInfoEntity packageInfoEntity = new PackageInfoEntity(createTime,packageItemName,qty);
        try {
            packageInfoDao.savePackageInfoEntity(packageInfoEntity);
            result.put("Success","新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("Fail","新增失败");
        }
        packageQrCodeEntityDao.insertQRCode(packageInfoEntity.getId(), qty);
        return result;
    }
    public void generateQRcode() {

    }
    public Map<String,String> del(String packageInfoID){
        Map<String, String> result = new HashMap<>();
        Integer packages =  packageQrCodeEntityDao.checkCountPackageQrCodeEntity(packageInfoID);
        if(packages == 0){
            result.put("Success","删除成功");
            try {
                PackageInfoEntity  packageInfoEntity = baseDao.get(PackageInfoEntity.class,Integer.valueOf(packageInfoID));
                if(packageInfoEntity == null){
                    result.put("Success","当前ID的PackageInfo  不存在");
                }else {
                    packageQrCodeEntityDao.delPackageQRListByPackageInfoId(packageInfoEntity.getId());
                    packageInfoDao.delPackageInfoEntity(packageInfoEntity);
                }
            } catch (NumberFormatException e) {
                result.put("Success","删除失败");
            }
        }else {
            result.put("Failed","删除失败,部分二维码已入库");
        }
        return result;
    }
    public ResponseEntity<byte[]>  export(Integer packageId,HttpServletResponse response,String packageName){
        PackageInfoEntity  packageInfoEntity = baseDao.get(PackageInfoEntity.class,packageId);
        System.out.println(packageInfoEntity.getPackageQrCodeEntityList().size());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + packageName + ".txt");
        headers.add("Expires", "0");
        byte[] body = packageInfoEntity.getPackageQrCodeEntityList()
                .stream()
                .map(o -> trackWebUrl + o.getQR_ID()+ "\n")
                .reduce("",String::concat).getBytes();
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
