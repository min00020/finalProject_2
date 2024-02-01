package com.yedamFinal.aco.qnaBoard.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

@Controller
public class QnABoardController {
	
	@GetMapping("/qnaBoard")
	public String getQnaBoardListForm(Model model) {
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
            MemberVO vo = userDetails.getMemberVO();
        }
        else {
        	return "redirect:/";
        }
		
		return "qnaboard/qnaList";
	}
}
