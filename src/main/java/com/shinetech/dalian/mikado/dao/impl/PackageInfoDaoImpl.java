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
             rows.addAll(baseDao.execute("From PackageInfoEntity"));}
        else{
             rows.addAll(baseDao.executeByLimit("From PackageInfoEntity", startPosition, maxResult));
        }
         return rows;
    }

    @Override
    public Integer getPackageInfoListSize() {
        return  Integer.valueOf(baseDao.executeGetFirst(" SELECT count(*) FROM PackageInfoEntity ").toString());
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

    }

    @Override
    public Integer savePackageInfoEntityList(List<PackageInfoEntity> packageInfoEntityList) {
        Session session = super.getSession();
        NativeQuery query = session.createNativeQuery("{Call proc(?)}");
        query.setParameter(1,"ss");
        return query.executeUpdate();
    }
}
