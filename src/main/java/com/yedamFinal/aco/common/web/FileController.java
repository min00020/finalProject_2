package com.yedamFinal.aco.common.web;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;

@Controller
public class FileController {
	@Autowired
	private FileServiceImpl fileService;
	
	@Value("${file.upload.attachFile.path}")
	private String attachFilePath;
	
	
	@GetMapping("/attachFile/{fileNo}")
	public ResponseEntity<?> downloadFile(@PathVariable("fileNo") int fileNo, @RequestHeader("User-Agent") String userAgent) {
		AttachedFileVO fileVO = fileService.getFile(fileNo);
		if(fileVO == null) {
			String errorMessage = "존재하지 않는 파일 다운로드 요청";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		Resource resource = new FileSystemResource(attachFilePath + fileVO.getFilePath());
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				downloadName = URLEncoder.encode(fileVO.getOriginFileName(), "UTF-8").replaceAll("\\+", " ");
			}
			else if(userAgent.contains("Edge")) {
				downloadName = URLEncoder.encode(fileVO.getOriginFileName(), "UTF-8");
			}
			else { //크롬
				downloadName = new String(fileVO.getOriginFileName().getBytes("UTF-8"),"ISO-8859-1");
			}
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
}
