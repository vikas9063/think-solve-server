package com.vikky.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	public static String getFileBase64(String fileLocation) {
		log.info("----- File Location -----: {}",fileLocation);
		
		try {
            File file = new File(fileLocation);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);
            fileInputStream.close();

            String base64String = Base64.getEncoder().encodeToString(fileBytes);

            return base64String;
        } catch (Exception e) {
        	log.error("---------  File not present ----------- :: {} ",e.getMessage());
            return null;
        }
	}
	
	
}
