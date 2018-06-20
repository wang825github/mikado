package com.shinetech.dalian.mikado.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
	
	private String datePattern;
	
	public StringToDateConverter(String datePattern){
		this.datePattern = datePattern;
	}
	
	@Override
	public Date convert(String s) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			dateFormat.setLenient(false);
			return dateFormat.parse(s);
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid date format.Please use this pattern:" + datePattern);
		}
	}

}
