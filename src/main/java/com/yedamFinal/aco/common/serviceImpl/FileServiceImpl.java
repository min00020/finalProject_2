package com.yedamFinal.aco.common.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.service.FileService;

enum FileEnum {
	PROFILE_IMG_FILE,
	BOARD_ATTACH_FILE,
	
}

@Service
public class FileServiceImpl implements FileService {
	@Value("${file.upload.path}")
	private String defaultUploadPath;
	
	@Value("${file.upload.profile.path}")
	private String profileUploadPath;
	
	@Value("${file.upload.attachFile.path}")
	private String attachFileUploadPath;
	
	public void makeDir(String path) {
		File uploadPathFoler = new File(path);
		if(!uploadPathFoler.exists()) {
			uploadPathFoler.mkdirs();
		}
	}
	
	public String profileUpload(MultipartFile file) {
		makeDir(profileUploadPath);
		
		String uuid = UUID.randomUUID().toString();
	    //저장할 파일 이름 중간에 "_"를 이용하여 구분
	     
		Date date = new Date();
		long time = date.getTime();
		String serverFileName = uuid + "_" + time + "_" + file.getOriginalFilename();
	    String uploadFileName = profileUploadPath + File.separator + serverFileName;
	    
	    String saveName = uploadFileName;
	        
	    Path savePath = Paths.get(saveName);
	    //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
	    System.out.println("path : " + saveName);
	    try{
	    	file.transferTo(savePath);
	       //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
	    } catch (IOException e) {
	       e.printStackTrace();	             
	       return null;
	    }
		
		return serverFileName;
	}

	//
	@Override
	public boolean uploadAttachFiles(MultipartFile[] files, int memberNo, String boardType, int boardNo ) {
		// TODO Auto-generated method stub
		makeDir(attachFileUploadPath);
		String uuid = UUID.randomUUID().toString();
	    //저장할 파일 이름 중간에 "_"를 이용하여 구분
	     
		Date date = new Date();
		long time = date.getTime();
		
		
		return false;
	}
	
}
