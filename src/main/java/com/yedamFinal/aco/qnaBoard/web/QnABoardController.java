package com.yedamFinal.aco.qnaBoard.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.qnaBoard.serviceImpl.QnABoardServiceImpl;

@Controller
public class QnABoardController {
	@Autowired
	private QnABoardServiceImpl qnaBoardService;
	
	// min 문의게시판 리스트 불러오기(페이징, 검색, 정렬등)
	@GetMapping("/qnaBoard")
	public String getQnaBoardListForm(@RequestParam String pg, String search, String ob, HttpServletRequest req, Model model) {// MemberVO 꺼내오기.
		MemberVO vo = null;
		Map<String,Object> ret = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();            
            if(vo == null) {
            	return "redirect:/loginForm";
            }
            // 검색창 입력의 경우.
            if(search == null)
            	ret = qnaBoardService.getMyQnaBoardList(Integer.valueOf(pg), vo, ob);
            else {
            	ret = qnaBoardService.getMyQnaBoardListFromSearch(Integer.valueOf(pg), search, vo, ob);
            	model.addAttribute("search",search); // 해당 search키워드로 페이징해야함.
            }
            
            if(ret == null) {
            	return "redirect:/loginForm";
            }
            
            // 알림창 띄우기위해서
            HttpSession session = req.getSession();
            Object result = session.getAttribute("qnaInsert");
            if(result != null) {
            	session.removeAttribute("qnaInsert");
            	model.addAttribute("qnaInsert","1");
            }
            
            model.addAttribute("qnaInfo",ret);
        }
        else {
        	return "redirect:/";
        }
		
		return "qnaboard/qnaList";
	}
	
	// min 문의게시판 작성 폼 불러오기(페이징, 검색, 정렬등)
	@GetMapping("/qnaBoard/write")
	public String getQnaBoardWriteForm(Model model) {
		return "qnaboard/qnaWrite";
	}
	
	@PostMapping("/qnaBoard/write")
	@ResponseBody
	public Map<String,Object> insertQnaBoard(@RequestParam String title, @RequestParam String content, MultipartFile[] attachFile, HttpServletRequest req) {
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        }
        var ret = qnaBoardService.insertQnaBoard(vo.getMemberNo(), title, content, attachFile);
        if(ret.get("result").equals("200")) {
        	req.getSession().setAttribute("qnaInsert", "1");
        }
        
		return ret;
	}
	
	@GetMapping("/qnaBoard/{boardNo}")
	public String getQnaBoardInfoPage(@PathVariable("boardNo") int qnaBoardNo, Model model) {
		var boardInfo = qnaBoardService.getQnaBoardDetailInfo(qnaBoardNo);
		if(boardInfo == null) {
			return "redirect:/";
		}
		
		// 요청한게 본인게 아니라면
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        }
        
        // 어드민은 무조건 통과
    	if(vo.getPermission().equals("ROLE_USER") && vo.getMemberNo() != boardInfo.get(0).getMemberNo()) {
        	return "redirect:/";
        }
    	
		model.addAttribute("qnaInfo",boardInfo);
		return "qnaboard/qnaInfo";
	}
	
	@PostMapping("/qnaBoard/{boardNo}")
	@ResponseBody
	public Map<String,Object> postQnABoardAnswer(@PathVariable("boardNo") int qnaBoardNo, String answer) {
		return qnaBoardService.postQnAAnswer(qnaBoardNo, answer);
	}
	
	@PutMapping("/qnaBoard/{boardNo}")
	@ResponseBody
	public Map<String, Object> changeQnABoardState(@PathVariable("boardNo") int qnaBoardNo, String state) {
		return qnaBoardService.changeQnAState(qnaBoardNo, state);
	}
}
