package com.shinetech.dalian.sanzeng.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.StorageEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
import com.shinetech.dalian.mikado.util.MakeNumbersDetails;
import com.shinetech.dalian.mikado.util.StringToDateConverter;


public class TestFuction {
	public static String tempString = "acdesfghsadajfkqsfab";
	 public enum  RecordType{
	        EXCHANGE("EXCHANGE2"), //通过兑换获取
	        RECEIVE("EXCHANGE2");  //通过赠送获取
	        private final String text;
            private RecordType(final String text){
                this.text=text;
            }
            @Override
            public String toString(){
                return text;
            }
	    }
	 
		@Test
		public void tesss4() throws ParseException {
		} 
		public static String compactString(String tempString) {
			StringBuffer sb = new StringBuffer();
			byte[] tempBytes = tempString.getBytes();
			for (int i = 0; i < tempBytes.length; i+=2) {
			char firstCharacter = (char)tempBytes[i];
			char secondCharacter = 0;
			if(i+1<tempBytes.length)
			secondCharacter = (char)tempBytes[i+1];
			firstCharacter <<= 8;
			sb.append((char)(firstCharacter+secondCharacter));
			}
			return sb.toString();
			}

		public static String decompressionString(String tempString){
			char[] tempBytes = tempString.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < tempBytes.length; i++) {
			char c = tempBytes[i];
			char firstCharacter = (char) (c >>> 8);
			char secondCharacter = (char) ((byte)c);
			sb.append(firstCharacter);
			if(secondCharacter != 0)
			sb.append(secondCharacter);
			}
			return sb.toString();
			}
		
		protected static void long2bytes(long value, byte[] bytes, int offset) {
	        for (int i = 7; i > -1; i--) {
	            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
	        }
	    }
		public static String gzip(String primStr) {
			if (primStr == null || primStr.length() == 0) {
			return primStr;
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			GZIPOutputStream gzip=null;
			try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
			} catch (IOException e) {
			e.printStackTrace();
			}finally{
			if(gzip!=null){
			try {
			gzip.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
			}
			}


			return null;
			}
//	@Test
	public void tesss3() {
		File file = new File("E://users.json");
		List<String> strYesterday = readinLine(file);
		
		File fileToday = new File("E://now.json");
		List<String> strToday = readinLine(fileToday);
	} 
	 public List<String> readinLine(File file){
		 List<String> strList = new ArrayList<String>();
		  BufferedReader reader=null;  
	        String temp=null;  
	        int line=1;  
	        try{  
	                reader=new BufferedReader(new FileReader(file));  
	                while((temp=reader.readLine())!=null){
	                    line++;  
	                    strList.add(temp);
	                }  
	        }  
	        catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        finally{
	            if(reader!=null){  
	                try{  
	                    reader.close();  
	                }  
	                catch(Exception e){  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
		 return strList;
	 }
}
