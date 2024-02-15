package com.yedamFinal.aco.common.web;

import java.net.URLEncoder;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;
import com.yedamFinal.aco.common.serviceImpl.ReplyServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;


/**
 * 
 * @author 전민교
 * @since 2024.02.10
 * @version 1.0
 * @see <pre>
 * 
 *
 *   수정일        수정자           수정내용
 *  -------      --------    ---------------------------
 *  2024.02.10   전민교        첨부파일 다운로드        
 *  2024.02.12   전민교        댓글 둥록/수정/삭제        
 * </pre>
 */

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
	
	
	
	/**
     * 첨부파일 다운로드
     * @param fileNo
     * @param userAgent
     * @return ResponseEntity<Resource>
     */
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
	
	/**
     * 댓글 등록
     * @param boardType
     * @param boardNo
     * @param replyBody,
     * @param isEmoticon
     * @param replyPno
     * @return ResponseEntity<Resource>
     */
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
	
	
	/**
     * 댓글 삭제
     * @param replyNo
     * @return Map<String, Object>
     */
	@DeleteMapping("/reply")
	@ResponseBody
	public Map<String, Object> deleteReplyControl(@RequestParam String replyNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			return replyService.deleteReply(replyNo);
		}
		else {
			result.put("result", "500");
			return result;
		}
	}

	/**
     * 댓글 수정
     * @param replyNo
     * @param replyBody
     * @param isEmoticon
     * @return Map<String, Object>
     */
	@PutMapping("/reply")
	@ResponseBody
	public Map<String, Object> modifyReplyControl(@RequestParam Integer replyNo, @RequestParam String replyBody, @RequestParam String isEmoticon) {
		Map<String, Object> result = new HashMap<String, Object>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			return replyService.modifyReply(replyNo, replyBody, isEmoticon);
		}
		else {
			result.put("result", "500");
			return result;
		}
	}

	//chae toast ui
	@PostMapping("/texteditorimage")
	@ResponseBody
	public Map<String, String> uploadEditorImage(@RequestParam final MultipartFile image) {
		return fileService.textEditorImage(image);
	}
}
