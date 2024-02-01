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
	public Map<String, Object> writeQuestion(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		vo.setTopic(null);
		vo.setPoint(0);
		vo.setTitle(null);
		vo.setTag(null);
		vo.setContents(null);
		vo.setWriteDate(null);
		/*vo.setViewCnt(0); vo.setRecommendCnt(0); 
		 * vo.setBookmarkCnt(0); vo.setAnswerCnt(0);*/
		
		return null;
	}


	
}
