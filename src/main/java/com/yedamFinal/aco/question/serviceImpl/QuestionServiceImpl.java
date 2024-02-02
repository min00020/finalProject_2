package com.yedamFinal.aco.question.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public QuestionVO getQuestionInfo(int qno) {
		return questionMapper.getQuestionInfo(qno);
	}
	
	@Override
	public Map<String, Object> writeQuestion(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertQuestion(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			ret.put("vo", vo);
		}
		return ret;
	}

	@Override
	public Map<String, Object> updateQuestion(QuestionVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuestionVO deleteQuestion(int qno) {
		return questionMapper.deleteQuestion(qno);
	}



	
}
