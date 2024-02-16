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
	
	//질문글 리스트 조회
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
	
	//질문글 리스트 분류 조회
	@Override
	public List<QuestionVO> getQuestionListTopic(Model model, int pageNo, String topic) {
		var questionListTopic = questionMapper.getQuestionListSelect(pageNo, topic);
		PaginationDTO dto = null;
		if(questionListTopic.size() > 0) {
			dto = new PaginationDTO(questionMapper.getQuestionTopicCount(topic),pageNo,5);
		}
		
		model.addAttribute("pageDTO", dto);
		model.addAttribute("questionListTopic", questionListTopic);
		
		return null;
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
				//채택답변 AdoptQuestionVO에 담아주기
				if(vo.getAnswerAdoptStatus().equals("I002")) {
					model.addAttribute("isAdopt",true);
					for(Map.Entry<Integer, List<QuestionVO>> myEntry : questionMap.entrySet()) {
		                  List<QuestionVO> list = myEntry.getValue();
		                  var findData = list.stream().filter(questionVO -> questionVO.getAnswerAdoptStatus().equals("I002")).findFirst();
		                  if(findData.isPresent()) {
		                     model.addAttribute("AdoptQuestionVO", findData.get());
		                     break;
		                  }
		             }
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
		
		//질문글 작성
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
	
	//답변글 작성
	@Override
	public Map<String, Object> writeAnswer(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertAnswer(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			//답변수+1
			questionMapper.plusAnswerCnt(vo.getQuestionBoardNo());
			//활동점수 지급
			
		}
		return ret;
	}
	
	//답변글 수정
	@Override
	public Map<String, Object> modifyAnswer(QuestionVO vo) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = questionMapper.updateAnswer(vo);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}
	
	//답변글 채택
	@Override
	public int adoptAnswer(int ano) {
		return questionMapper.adoptAnswer(ano);
	}
	
	//추가질문답변 작성
	@Override
	public Map<String, Object> writeQuestionAdd(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertQuestionAdd(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
		}
		return ret;
	}
}
