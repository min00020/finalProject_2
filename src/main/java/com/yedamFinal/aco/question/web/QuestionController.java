package com.yedamFinal.aco.question.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.service.QuestionService;

@Controller

public class QuestionController {
	/*chae 질문&답변 게시판*/
	
	@Autowired
	private QuestionService questionService;

	
	//전체조회
	@GetMapping("/questionList")
	public String getquestionBoard(Model model) {
		model.addAttribute("questionList", questionService.getQuestionList());
		return "question/questionList";
	}
	
	//질문글 상세조회 페이지
	/*
	 * @GetMapping("/questionInfo") public String getquestionInfo() { return
	 * "question/questionInfo"; }
	 */

	@GetMapping("/questionInfo/{qno}")
	public String getQuestionInfo(@PathVariable("qno") int qno, Model model) {
		model.addAttribute("questionInfo", questionService.getQuestionInfo(qno,model));
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			
			
			model.addAttribute("loginId", username.getId());
		}
		else {
			model.addAttribute("loginId", "-1");
		}
		
		return "question/questionInfo";
	}
	
	//질문글 작성
	@GetMapping("/questionWrite")
	public String questionWrite() {
		return "question/questionWrite";
	}
	
	@PostMapping("/questionWrite/write")
	@ResponseBody
	public Map<String, Object> writeQuestion(QuestionVO question){
		Map<String, Object> ret = new HashMap<String, Object>();
		questionService.writeQuestion(question);
		ret.put("result", "400");
		return ret;
	}
	
	//질문글 수정

	//질문글 삭제
	
}