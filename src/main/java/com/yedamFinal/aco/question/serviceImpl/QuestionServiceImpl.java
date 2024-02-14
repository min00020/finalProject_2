package com.yedamFinal.aco.question.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.question.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionMapper questionMapper;
	
	//리스트 조회
	@Override
	public List<QuestionVO> getQuestionList(Model model, int pageNo) {
		var questionList = questionMapper.getQuestionList(pageNo);
		PaginationDTO dto = null;
		if(questionList.size() > 0) {
			dto = new PaginationDTO(questionMapper.getQuestionCount(),pageNo,5);
		}
		
		model.addAttribute("pageDTO", dto);
		model.addAttribute("questionList", questionList);
		
		return null;
	}
	
	@Override
	public List<QuestionVO> getQuestionListSelect(String topic) {
		return questionMapper.getQuestionListSelect(topic);
	}

	//단건조회
	@Override
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno, Model model, int memberNo) {
		//조회수 +1
		questionMapper.updateQuestionViewCnt(qno);
		List<QuestionVO> result = questionMapper.getQuestionInfo(qno);
		Map<Integer, List<QuestionVO>> questionMap 
			= result.stream().collect(Collectors.groupingBy(QuestionVO::getAnswerBoardNo));
		
		//번호 boardNo 기준 > 0부터 시작하게 변경
		Map<Integer, List<QuestionVO>> ret = new HashMap<Integer, List<QuestionVO>>();
		Integer idx = 0;
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
		    ret.put(idx++, entry.getValue());
		}
		
		//답변 채택상태 확인
		model.addAttribute("isAdopt",false);
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
			System.out.println("value : " + entry.getValue());
			List<QuestionVO> adoptStatus = entry.getValue();
			for(QuestionVO vo : adoptStatus) {
				if(vo.getAnswerAdoptStatus() == null) {
					continue;
				}
				if(vo.getAnswerAdoptStatus().equals("I002")) {
					model.addAttribute("isAdopt",true);
					break;
				}
			}
		}	
		System.out.print(memberNo);
		//로그인 유저의 답변글 작성 여부 체크
		model.addAttribute("writePost",false);
		for( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
			List<QuestionVO> writeStatus = entry.getValue();
			for(QuestionVO vo : writeStatus) {
				if(vo.getAnswerMemberNo() == memberNo) {
					model.addAttribute("writePost", true);
					System.out.print("test22");
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
		int bno = vo.getPk();
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			ret.put("vo", vo);
			ret.put("bno", bno);
		}
		
		return ret;
	}
	
	//질문글 수정
	@Override
	public Map<String, Object> modifyQuestion(QuestionVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = questionMapper.updateQuestion(vo);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}

	@Override
	public QuestionVO deleteQuestion(int qno) {
		return questionMapper.deleteQuestion(qno);
	}

	@Override
	public Map<String, Object> writeAnswer(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertAnswer(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			ret.put("vo", vo);
		}
		
		return ret;
	}

	


	
}
