package com.shinetech.dalian.mikado.util;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author  Justin
 *
 * @param <E>
 */
public  class RestultMSG<E>{
	public final JsonObject json = new JsonObject();
	/**
	*  @author Justin
	 * @param entityList
	 * @param total
	 * @return
	 */
	public  JsonObject  setEntityMSGList( final List<E> entityList,int total){
		Gson g = new GsonBuilder().serializeNulls().create();
		JsonParser jp = new JsonParser();
		if(entityList == null){
			json.addProperty("total", 0);
			json.addProperty("rows", "");
		}else{
			json.addProperty("total", total);
			String entityJson = g.toJson(entityList);
			json.add("rows", jp.parse(entityJson));
		}
		return json;
	}
	
}
