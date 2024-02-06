package com.yedamFinal.aco.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionService {
	public List<QuestionVO> getQuestionList();
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno,  Model model);
	public Map<String, Object> writeQuestion(QuestionVO vo);
	public Map<String, Object> updateQuestion(QuestionVO vo);
	public QuestionVO deleteQuestion(int qno);
	//public Object getQuestionInfo(int qno);
}
