package com.yedamFinal.aco.question.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.question.service.QuestionService;

@Controller

public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	/*chae 질문&답변 게시판*/
	@GetMapping("/questionList")
	public String getquestionBoard(Model model) {
		model.addAttribute("questionList", questionService.getQuestionList());
		return "question/questionList";
	}
	@GetMapping("/questionInfo")
	public String getquestionInfo() {
		return "question/questionInfo";
	}
	@GetMapping("/questionWrite")
	public String questionWrite() {
		return "question/questionWrite";
	}
	
	
}