package com.yedamFinal.aco.qnaBoard.web;

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
	
	@GetMapping("/qnaBoard")
	public String getQnaBoardListForm(@RequestParam String pg, Model model) {
		// MemberVO 꺼내오기.
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();            
            if(vo == null) {
            	return "redirect:/loginForm";
            }
            
            var qnaInfo = qnaBoardService.getMyQnaBoardList(Integer.valueOf(pg), vo);
            if(qnaInfo == null) {
            	return "redirect:/loginForm";
            }
            model.addAttribute("qnaInfo",qnaInfo);
        }
        else {
        	return "redirect:/";
        }
		
		return "qnaboard/qnaList";
	}
}
