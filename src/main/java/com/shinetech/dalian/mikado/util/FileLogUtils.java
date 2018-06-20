package com.shinetech.dalian.mikado.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.shinetech.dalian.mikado.entity.LogSwitch;
@Component
public class FileLogUtils {
	
	@Value(value = "open")
	public String LogSwitch;
	@Qualifier(value ="logSwitchFlat")
	public LogSwitch log;
	
	public  void  writeLog( String operationType,String str) throws IOException {
		
		System.out.println("log : "+log.getFlat());
		if(StringUtils.equalsIgnoreCase(LogSwitch, "false") || StringUtils.isEmpty(LogSwitch) ) {
			return;
		}
		String dirs = "C://Log";
		String filePath =  dirs+"//"+LocalDate.now()+"-log.txt";
		try {
			writeDetailLog(operationType,str);
			File filedir = new File(dirs);  
			 Path path = Paths.get(filePath);	
			 
			 if(!filedir.exists()) {
				 filedir.mkdirs();
			 }
			if(Files.notExists(path)) {
				Files.createFile(path);
			}
			BufferedWriter writer =Files.newBufferedWriter(path,StandardOpenOption.APPEND);
			if(str.length()>= 5000) str = str.substring(0, 4999)+".........";
			writer.write(operationType+": "+str);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	public static void  writeDetailLog( String operationType,String str) throws IOException {
		String dirs = "C://Log";
		String filePath =  dirs+"//"+LocalDate.now()+"-detail"+"-log.txt";
	
		File filedir = new File(dirs);  
		 Path path = Paths.get(filePath);	
		 
		 if(!filedir.exists()) {
			 filedir.mkdirs();
		 }
		if(Files.notExists(path)) {
			Files.createFile(path);
		}
		BufferedWriter writer =Files.newBufferedWriter(path,StandardOpenOption.APPEND);
		writer.write(operationType+": "+str);
		writer.newLine();
		writer.flush();
		writer.close();
	}  
	
}
