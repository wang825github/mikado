package com.shinetech.dalian.mikado.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@SuppressWarnings("unused")
public class MultipartFileChangeFileUtils {

	public  static File multipartToFile(MultipartFile multfile) throws IOException {  
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
        //这个myfile是MultipartFile的  
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
        File file = fi.getStoreLocation();  
        //手动创建临时文件  
        if(null == file ||file.length() <= 0){
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
                    file.getName());  
            multfile.transferTo(tmpFile);  
            return tmpFile;  
        }  
        return file;  
    } 
}
