package com.shinetech.dalian.mikado.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class CustomerEntity {
	private Integer id;
	private String company; //company name
	private String address; //company address
	private String email; //contact email
	private String simpleName;//company Abbreviation
	private String contact;//contacts
	private String telephone;//contact telephone
	private SaleManagerEntity saleManager;//sale manager of the customer
	private CityEntity city;//city of the customer
	
	private Date firstMonth;
	private String ago;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="company",nullable = false,length = 255)
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name="address",nullable = false,length = 255)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="email",nullable = false,length = 255)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="simple_name",nullable = false,length = 255)
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	
	@Column(name = "telephone")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ManyToOne(targetEntity = SaleManagerEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "sale_manager_id")
	public SaleManagerEntity getSaleManager() {
		return saleManager;
	}
	public void setSaleManager(SaleManagerEntity saleManager) {
		this.saleManager = saleManager;
	}
	
	@ManyToOne(targetEntity = CityEntity.class, cascade = {
		CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return city;
	}
	public void setCity(CityEntity city) {
		this.city = city;
	}
	@Column(name = "contact")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(name = "first_month")
	public Date getFirstMonth() {
		return firstMonth;
	}
	public void setFirstMonth(Date firstMonth) {
		this.firstMonth = firstMonth;
	}
	
	@Column(name = "ago")
	public String getAgo() {
		return ago;
	}
	public void setAgo(String ago) {
		this.ago = ago;
	}
	
}
