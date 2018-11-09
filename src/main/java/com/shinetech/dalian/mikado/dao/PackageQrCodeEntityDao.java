package com.shinetech.dalian.mikado.dao;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.HibernateDao;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageQrCodeEntityDao extends HibernateDao {
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
    public void delPackageQRListByPackageInfoId(Integer packageInfoId){
        String hql = "DELETE PackageQrCodeEntity pq WHERE pq.packageInfoId = '"+packageInfoId+"'";
        baseDao.execute(hql);
    }
    public void insertQRCode(Integer packageInfoId,Integer total){
        Session session = super.getSession();
        String proc = "declare @QR_ID varchar(20)\n" +
                "declare @total INT\n" +
                "BEGIN \n" +
                "set @total = 0--初始化循环次数\n" +
                "set IDENTITY_INSERT package_qr_code OFF \n" +
                "WHILE @total<"+ total +"\n" +
                    "\tBEGIN\n" +
                    "\t\tset @total = @total+1\n" +
                    "\t\tset @QR_ID = cast(newid() as varchar(255))\n" +
                    "\t\tset @QR_ID = convert(varchar,DATEPART(yyyy,GETDATE()))+SUBSTRING(replace(@QR_ID,'-',''),1,16)\n" +
                    "\t\tINSERT package_qr_code(QR_ID,createTime,package_info_id) VALUES(@QR_ID,GETDATE(),"+ packageInfoId +")\n" +
                    "\tEND\n"+
                "END \n" ;
        NativeQuery query = session.createNativeQuery(proc);
        query.executeUpdate();
    }
}
