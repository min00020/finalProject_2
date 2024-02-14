package com.yedamFinal.aco.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionMapper {
	//전제조회
	public List<QuestionVO> getQuestionList(@Param(value="pageNo") int pageNo);
	public int getQuestionCount();
	public List<QuestionVO> getQuestionListSelect(String topic);
	
	//CRUD
	public List<QuestionVO> getQuestionInfo(int qno);
	public int insertQuestion(QuestionVO questionVO);
	public QuestionVO deleteQuestion(int qno);
	public int updateQuestion(QuestionVO questionVO);
	
	//etc
	public int updateQuestionViewCnt(int qno);
	
}