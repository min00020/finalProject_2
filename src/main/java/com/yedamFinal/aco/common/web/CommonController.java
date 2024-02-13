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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;
import com.yedamFinal.aco.common.serviceImpl.ReplyServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;

@Controller
public class CommonController {
	@Autowired
	private FileServiceImpl fileService;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@Autowired
	private ReplyServiceImpl replyService;
	
	@Value("${file.upload.attachFile.path}")
	private String attachFilePath;
	
	
	// min 첨부파일 다운로드
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
	
	// min 댓글 등록(댓글 등록 요청은 여기다가 하고, 댓글 select하는건 개별 controller에서 처리)
	@PostMapping("/reply")
	@ResponseBody
	public Map<String,Object> postReplyControl(@RequestParam String boardType,
			@RequestParam String boardNo, String replyBody, String isEmoticon, String replyPno) {
		// replyBody는 댓글내용 혹은 이모티콘 파일이름이 올 수 있음.
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
			
			memberVO = memberService.getMemberInfo(memberVO);
		}
		else {
			return null;
		}
		
		return replyService.postReply(boardType, boardNo, replyBody, isEmoticon,replyPno, memberVO);
	}
	
	// min 댓글 추천
	@PutMapping("/reply")
	@ResponseBody
	public Map<String, Object> recommendReplyControl(String replyNo) {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
			
			memberVO = memberService.getMemberInfo(memberVO);
		}
		else {
			return null;
		}
		
		return null;
	}
	
}
