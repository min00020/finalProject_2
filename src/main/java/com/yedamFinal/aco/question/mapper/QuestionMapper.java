package com.yedamFinal.aco.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionMapper {
	public List<QuestionVO> getQuestionList(@Param(value="pageNo") int pageNo);
	public int getQuestionCount();
	public List<QuestionVO> getQuestionListSelect(String topic);
	
	public List<QuestionVO> getQuestionInfo(int qno);
	
	public int insertQuestion(QuestionVO questionVO);
	public QuestionVO deleteQuestion(int qno);
	public int updateQuestion(QuestionVO questionVO, int qno);
  
}