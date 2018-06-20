package com.shinetech.dalian.mikado.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeDateForDay {

	/**yyyy-mm to Date
	 * @param Date
	 * @return
	 * @throws ParseException 
	 */
	public static Date createDay(String dateFromJs) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		dateFromJs = dateFromJs +"-01";
		java.util.Date date=sdf.parse(dateFromJs);  
		return date;
		
	}
}
