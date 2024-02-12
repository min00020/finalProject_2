package com.yedamFinal.aco.common.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

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
    private final String uploadDir = Paths.get("C:", "upload", "texteditorimage").toString();

    /**
     * 에디터 이미지 업로드
     * @param image 파일 객체
     * @return 업로드된 파일명
     */
    @PostMapping("/texteditorimage")
    @ResponseBody
    public Map<String, Object> uploadEditorImage(@RequestParam final MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
        String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return null;

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }
}
