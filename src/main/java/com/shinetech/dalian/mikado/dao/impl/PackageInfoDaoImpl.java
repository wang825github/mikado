package com.shinetech.dalian.mikado.dao.impl;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.HibernateDao;
import com.shinetech.dalian.mikado.dao.PackageInfoDao;
import com.shinetech.dalian.mikado.entity.LogEntity;
import com.shinetech.dalian.mikado.entity.PackageInfoEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PackageInfoDaoImpl extends HibernateDao implements PackageInfoDao  {
    @Autowired
    private BaseDao baseDao;
    @Override
    public  List<PackageInfoEntity> getPackageInfoList(Integer startPosition,Integer maxResult) {

        List<PackageInfoEntity> rows = new ArrayList<>();
        if(startPosition == null && maxResult == null){
             rows.addAll(baseDao.execute("SELECT new PackageInfoEntity(id,createTime,packageItemCode,packageItemName,qty) From PackageInfoEntity"));}
        else{
             rows.addAll(baseDao.executeByLimit("SELECT new PackageInfoEntity(id,createTime,packageItemCode,packageItemName,qty)  From PackageInfoEntity", startPosition, maxResult));
        }
         return rows;
    }

    @Override
    public  List<PackageInfoEntity> getPackageInfoList(Integer startPosition,Integer maxResult,String packageItemName) {

        List<PackageInfoEntity> rows = new ArrayList<>();
        if(startPosition == null && maxResult == null){
            rows.addAll(baseDao.execute("SELECT new PackageInfoEntity(id,createTime,packageItemCode,packageItemName,qty) From PackageInfoEntity pi WHERE pi.packageItemName like '%"+packageItemName+"%'"));}
        else{
            rows.addAll(baseDao.executeByLimit("SELECT new PackageInfoEntity(id,createTime,packageItemCode,packageItemName,qty)  From PackageInfoEntity pi WHERE pi.packageItemName like '%"+packageItemName+"%'", startPosition, maxResult));
        }
        return rows;
    }

    @Override
    public Integer getPackageInfoListSize() {
        return  Integer.valueOf(baseDao.executeGetFirst(" SELECT count(*) FROM PackageInfoEntity ").toString());
    }
    @Override
    public Integer getPackageInfoListSizeByPackageItemName(String packageItemName) {
        return  Integer.valueOf(baseDao.executeGetFirst(" SELECT count(*) FROM PackageInfoEntity pi WHERE pi.packageItemName like '%" + packageItemName+"%'").toString());
    }
    @Override
    public List<PackageInfoEntity> queryPackageInfoList(String packageItemCode, String packageItemName,Integer startPosition,Integer maxResult) {
        return null;
    }

    @Override
    public void savePackageInfoEntity(PackageInfoEntity packageInfoEntity) {
         baseDao.save(packageInfoEntity);
    }

    @Override
    public void delPackageInfoEntity(PackageInfoEntity packageInfoEntity) {
        baseDao.delete(packageInfoEntity);
    }

    @Override
    public Integer savePackageInfoEntityList(List<PackageInfoEntity> packageInfoEntityList) {
        Session session = super.getSession();
        NativeQuery query = session.createNativeQuery("{Call proc(?)}");
        query.setParameter(1,"ss");
        return query.executeUpdate();
    }
}
