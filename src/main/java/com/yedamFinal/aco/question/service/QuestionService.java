package com.yedamFinal.aco.question.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionService {
	public List<QuestionVO> getQuestionList();
	public Map<String, Object> writeQuestion(QuestionVO vo);
}
