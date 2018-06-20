package com.shinetech.dalian.mikado.dao;

import com.shinetech.dalian.mikado.entity.ProductionsEntity;

public interface ProductionsDao {
	
	ProductionsEntity getProductionsByIDCode(String IDCode);
	
 
}
