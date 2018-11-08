package com.shinetech.dalian.mikado.dao;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageQrCodeEntityDao {
    @Autowired
    BaseDao baseDao;

    public Integer checkCountPackageQrCodeEntity(String package_info_id){
        String hql = "Select COUNT(*) FROM PackageQrCodeEntity pq WHERE pq.packageBatchCode != null and packageInfoId =  "+package_info_id;
        return ((Number)baseDao.executeGetFirst(hql)).intValue();
    }

    public void delPackageBatchCodeListByBatchCode(String packageBatchCode){
        String hql = "DELETE PackageQrCodeEntity pq WHERE pq.packageBatchCode = '"+packageBatchCode+"'";
        baseDao.execute(hql);
    }
}
