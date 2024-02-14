package com.yedamFinal.aco.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionService {
	//전체조회
	public List<QuestionVO> getQuestionList(Model model, int pageNo);
	public List<QuestionVO> getQuestionListSelect(String topic);
	
	//CRUD
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno,  Model model, int memberNo);
	public Map<String, Object> writeQuestion(QuestionVO vo);
	public Map<String, Object> modifyQuestion(QuestionVO vo);
	public QuestionVO deleteQuestion(int qno);
	
	
} 
