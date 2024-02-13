package com.yedamFinal.aco.common.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.TextEditorImageVO;
import com.yedamFinal.aco.common.mapper.FileMapper;
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
	
	@Value("${file.upload.texteditorimage.path}")
	private String textEditorImagePath;
	
	@Autowired
	private FileMapper fileMapper;
	
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
		// attachFile / 게시판 타입 폴더 / 게시판 번호 폴더
		String savePath = attachFileUploadPath + File.separator + boardType + File.separator + boardNo;
		makeDir(savePath);
		String uuid = UUID.randomUUID().toString();
		long time = new Date().getTime();
		String serverName = uuid + "_" + time + "_" + memberNo;
		for(int i = 0; i < files.length; ++i) {
			String originFileName = files[i].getOriginalFilename();
			int dotIndex = originFileName.lastIndexOf(".");
			if(dotIndex < 0) {
				continue;
			}
			String fileExtension = originFileName.substring(dotIndex);
			String serverFileName = serverName + "_" + originFileName;
			
			String saveName = savePath + File.separator + serverFileName;
			Path path = Paths.get(saveName);
			//Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			try{
				files[i].transferTo(path);
			} catch (IOException e) {
				e.printStackTrace();	             
				return false;
			}
			
			AttachedFileVO vo = new AttachedFileVO();
			vo.setBoardNo(boardNo);
			vo.setBoardType(boardType);
			vo.setFileExtension(fileExtension);
			vo.setServerFileName(serverFileName);
			vo.setOriginFileName(originFileName);
			String accessPath = File.separator + boardType + File.separator + boardNo + File.separator + serverFileName; 
			vo.setFilePath(accessPath);
			vo.setFileOrder(i + 1);
			
			fileMapper.insertFile(vo);
		}
		return true;
	}

	@Override
	public AttachedFileVO getFile(int fileNo) {
		// TODO Auto-generated method stub
		return fileMapper.selectFile(fileNo);
	}

	@Override
	public Map<String,String> textEditorImage(MultipartFile image) {
		Map<String,String> ret = new HashMap<String, String>();
		
		String uploadDir = Paths.get("C:", "upload", "texteditorimage").toString();

		if (image.isEmpty()) {
	        return null;
	    }

	    String orgFilename = image.getOriginalFilename();   // 원본 파일명
	    String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 32자리 랜덤 문자열
	    String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
	    String saveFilename = uuid + "." + extension;                       // 디스크에 저장할 파일명
	    String fileFullPath = Paths.get(uploadDir, saveFilename).toString();// 디스크에 저장할 파일의 전체 경로

	    // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
	    File dir = new File(uploadDir);
	    if (dir.exists() == false) {
	        dir.mkdirs();
	    }

	  
	    try {
	        // 파일 저장 (write to disk)
	        File uploadFile = new File(fileFullPath);
	        image.transferTo(uploadFile);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    String dbInsertImagePath = File.separator + "upload" + File.separator + "texteditorimage" + File.separator + saveFilename;
	    TextEditorImageVO vo = new TextEditorImageVO();
	    vo.setBoardNo(0);
	    vo.setBoardType("Z000");
	    vo.setFileExtension(extension);
	    vo.setFilePath(dbInsertImagePath);
	    vo.setOriginFileName(orgFilename);
	    vo.setServerFileName(saveFilename);
	    
	    fileMapper.insertImage(vo);
	    
	   
	    int imageNo = vo.getPk();
	    ret.put("imageNo", String.valueOf(imageNo));
	    ret.put("imageUrl", dbInsertImagePath);
	    
		return ret;
	}
	
}
