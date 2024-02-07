package com.yedamFinal.aco.question.mapper;

import java.util.List;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionMapper {
	public List<QuestionVO> getQuestionList();
	public List<QuestionVO> getQuestionListSelect(String topic);
	
	public List<QuestionVO> getQuestionInfo(int qno);
	public int insertQuestion(QuestionVO questionVO);
	public int updateQuestion(QuestionVO questionVO, int qno);
	public QuestionVO deleteQuestion(int qno);
  
}