package com.shinetech.dalian.mikado.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="package_qr_code")
public class PackageQrCodeEntity {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String QR_ID;
    private String packageBatchCode;
    private String WO_No;


    public PackageQrCodeEntity() {
        this.createTime = new Date();
        this.QR_ID = UUID.randomUUID().toString().replaceAll("-","").substring(0,19);
    }
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name = "QR_ID")
    public String getQR_ID() {
        return QR_ID;
    }

    public void setQR_ID(String QR_ID) {
        this.QR_ID = QR_ID;
    }
    @Column(name = "package_batch_code")
    public String getPackageBatchCode() {
        return packageBatchCode;
    }
    public void setPackageBatchCode(String packageBatchCode) {
        this.packageBatchCode = packageBatchCode;
    }

    @Column(name = "WO_No")
    public String getWO_No() {
        return WO_No;
    }

    public void setWO_No(String WO_No) {
        this.WO_No = WO_No;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}