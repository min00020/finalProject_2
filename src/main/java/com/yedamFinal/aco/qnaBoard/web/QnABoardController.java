package com.yedamFinal.aco.qnaBoard.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
