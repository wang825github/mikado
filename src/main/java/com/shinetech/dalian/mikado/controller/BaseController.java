package com.shinetech.dalian.mikado.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	public String getYear(){
		
//        Date date = new Date();
        return sdf.format(new Date());
	}
}
