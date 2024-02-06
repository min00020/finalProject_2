package com.yedamFinal.aco.question.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno, Model model) {
		List<QuestionVO> result = questionMapper.getQuestionInfo(qno);
		Map<Integer, List<QuestionVO>> questionMap 
			= result.stream().collect(Collectors.groupingBy(QuestionVO::getAnswerBoardNo));
		
		//번호 boardNo 기준 > 0부터 시작하게 변경
		Map<Integer, List<QuestionVO>> ret = new HashMap<Integer, List<QuestionVO>>();
		Integer idx = 0;
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
		    ret.put(idx++, entry.getValue());
		}
		
		model.addAttribute("isAdopt",false);
		//답변 채택상태 확인
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
			System.out.println("value : " + entry.getValue());
			List<QuestionVO> adoptStatus = entry.getValue();
			for(QuestionVO vo : adoptStatus) {
				if(vo.getAnswerAdoptStatus() == null) {
					continue;
				}
				if(vo.getAnswerAdoptStatus().equals("I002")) {
					model.addAttribute("isAdopt",true);
					System.out.print("test");
					break;
				}
			}
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
