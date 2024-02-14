package com.yedamFinal.aco.question.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.service.QuestionService;

@Controller

public class QuestionController {
	/**
	 * @author 김채민
	 * @since 20240213
	 * @version 1.0
	 * @see
	 */


	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	//전체조회
	@GetMapping("/questionList")
	public String getquestionBoard(@RequestParam int pageNo, Model model) {
		questionService.getQuestionList(model, Integer.valueOf(pageNo));
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
		return "question/questionList";
	}
	
	@GetMapping("/questionList/{topic}")
	public String getquestionSelect(@PathVariable("topic") String topic, Model model) {
		model.addAttribute("questionList", questionService.getQuestionListSelect(topic));
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
		
		return "question/questionList";
	}
	
	//질문글 상세조회 페이지
	/*
	 * @GetMapping("/questionInfo") public String getquestionInfo() { return
	 * "question/questionInfo"; }
	 */

	@GetMapping("/questionInfo/{qno}")
	public String getQuestionInfo(@PathVariable("qno") int qno, Model model) {
		// MemberVO 꺼내오기.
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			model.addAttribute("loginId", username.getId());
		}
		else {
			model.addAttribute("loginId", "-1");
		}
		
		int memberNo = username != null ? username.getMemberNo():-1;
		
		var result = questionService.getQuestionInfo(qno,model,memberNo);
		model.addAttribute("questionInfo", result);
		
		
		return "question/questionInfo";
	}
	
	//질문글 작성
	@GetMapping("/questionWrite")
	public String questionWrite(Integer bno, Model model) {
		
		if(bno != null) {
			var map = questionService.getQuestionInfo(bno, model, -1);
			var entry = map.entrySet().iterator().next();
			List<QuestionVO> value = entry.getValue();
			
			QuestionVO questionVO = value.get(0);
			model.addAttribute("questionVO",questionVO);
		}
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		
		return "question/questionWrite";
	}
	
	@PostMapping("/questionWrite")
	@ResponseBody
	public Map<String, Object> writeQuestion(QuestionVO question){
		Map<String, Object> ret = new HashMap<String, Object>();
		questionService.writeQuestion(question);
		ret.put("result", "400");
		
		return ret;
	}
	
	//질문글 수정
	@PostMapping("/questionModify")
	@ResponseBody
	public Map<String, Object> modifyQuestion(QuestionVO question){
		Map<String, Object> ret = new HashMap<String, Object>();
		questionService.modifyQuestion(question);
		ret.put("result", "400");
		
		return ret;
	}
	
	//질문글 삭제
	
	
	
	
}