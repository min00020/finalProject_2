package com.yedamFinal.aco.common.web;

import java.net.URLEncoder;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.TextEditorImageVO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;

@Controller
public class FileController {
	@Autowired
	private FileServiceImpl fileService;
	
	@Value("${file.upload.attachFile.path}")
	private String attachFilePath;
	
	@Value("${file.upload.texteditorimage.path}")
	private String textEditorImagePath;
	
	
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
	
	//toast ui
	// 파일을 업로드할 디렉터리 경로

    /**
	private final String uploadDir = Paths.get("C:", "upload", "texteditorimage").toString();
     * 에디터 이미지 업로드
     * @param image 파일 객체
     * @return 업로드된 파일명
     */
    @PostMapping("/texteditorimage")
    @ResponseBody
    public Map<String, String> uploadEditorImage(@RequestParam final MultipartFile image) {
		return fileService.textEditorImage(image);

    }
}
