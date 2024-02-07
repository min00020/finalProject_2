package com.yedamFinal.aco.question.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.question.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionMapper questionMapper;
	
	//리스트 조회
	@Override
	public List<QuestionVO> getQuestionList() {
		return questionMapper.getQuestionList();
	}

	//단건조회
	@Override
	/*
	 * public List<QuestionVO> getQuestionInfo(int qno) { return
	 * questionMapper.getQuestionInfo(qno); }
	 */
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno) {
		List<QuestionVO> result = questionMapper.getQuestionInfo(qno);
		Map<Integer, List<QuestionVO>> questionMap 
			= result.stream().collect(Collectors.groupingBy(QuestionVO::getAnswerBoardNo));
		
		Map<Integer, List<QuestionVO>> ret = new HashMap<Integer, List<QuestionVO>>();
		Integer idx = 0;
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
		    ret.put(idx++, entry.getValue());
		}
			
		return ret;
	}
	
	//질문글작성
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
