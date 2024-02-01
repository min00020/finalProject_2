package com.yedamFinal.aco.question.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.question.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionMapper questionMapper;

	@Override
	public List<QuestionVO> getQuestionList() {
		return questionMapper.getQuestionList();
	}
	
}
