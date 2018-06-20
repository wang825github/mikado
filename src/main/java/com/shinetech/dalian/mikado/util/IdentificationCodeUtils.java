package com.shinetech.dalian.mikado.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shinetech.dalian.mikado.Trancking.entity.SeedDetail;

public class IdentificationCodeUtils {

	public static int  findSameidentificationCode(List<SeedDetail> sdList,String identificationCode){
		for(SeedDetail sd:sdList){
			if(StringUtils.equals(sd.getIdentificationCode(), identificationCode))
				return 0;
		}
		return 1;
	}
	
	public static SeedDetail  getSameidentificationCode(List<SeedDetail> sdList,String identificationCode){
		for(SeedDetail sd:sdList){
			if(StringUtils.equals(sd.getIdentificationCode(), identificationCode))
				return sd;
		}
		return null;
	}
	
}
