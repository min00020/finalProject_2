package com.yedamFinal.aco.answer.mapper;

import java.util.List;

import com.yedamFinal.aco.answer.AnswerVO;
import com.yedamFinal.aco.question.QuestionVO;

public interface AnswerMapper {
	public List<AnswerVO> getAnswerList();
	public int insertAnswer(QuestionVO questionVO);
}