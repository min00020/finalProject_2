package com.yedamFinal.aco.question.mapper;

import java.util.List;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionMapper {
	public List<QuestionVO> getQuestionList();
	public int insertQuestion(QuestionVO questionVO);
	
}