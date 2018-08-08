package com.shinetech.dalian.sanzeng.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;
import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.util.FileLogUtils;


public class Test2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		StopWatch watch=new  StopWatch();
		watch.start("search1");
		Thread.sleep(1500);
		Thread.sleep(1000);
		watch.stop();
		watch.start("search2");
		Thread.sleep(1000);
		watch.stop();
		System.out.println(watch.prettyPrint());
	}

	 

}
