package com.shinetech.dalian.mikado.entity;



import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="package_info")
public class PackageInfoEntity {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String packageItemCode;
    private String packageItemName;
    private Integer qty;
    private List<PackageQrCodeEntity> packageQrCodeEntityList;
    @Transient
    private String createTimeDto;
    @Transient
    public String getCreateTimeDto() {
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(createTime);
    }

    public PackageInfoEntity(Integer id, Date createTime, String packageItemCode, String packageItemName, Integer qty) {
        this.id = id;
        this.createTime = createTime;
        this.packageItemCode = packageItemCode;
        this.packageItemName = packageItemName;
        this.qty = qty;
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTimeDto = myFmt.format(createTime);
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL,
            CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "package_info_id")
    public List<PackageQrCodeEntity> getPackageQrCodeEntityList() {
        return packageQrCodeEntityList;
    }

    public void setPackageQrCodeEntityList(List<PackageQrCodeEntity> packageQrCodeEntityList) {
        this.packageQrCodeEntityList = packageQrCodeEntityList;
    }

    public PackageInfoEntity(Date createTime, String packageItemName, Integer qty) {
        this.createTime = createTime;
        this.packageItemCode = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        this.packageItemName = packageItemName;
        this.qty = qty;
    }

    public PackageInfoEntity() {
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
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name = "package_item_code")
    public String getPackageItemCode() {
        return packageItemCode;
    }

    public void setPackageItemCode(String packageItemCode) {
        this.packageItemCode = packageItemCode;
    }
    @Column(name = "package_item_name")
    public String getPackageItemName() {
        return packageItemName;
    }

    public void setPackageItemName(String packageItemName) {
        this.packageItemName = packageItemName;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
