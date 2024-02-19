package com.yedamFinal.aco.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionService {
	/*질문글*/
	//질문글 전체조회
	public List<QuestionVO> getQuestionList(Model model, int pageNo);
	//질문글 분류조회
	public List<QuestionVO> getQuestionListTopic(Model model, int pageNo, String topic);
	
	//질문글 + 답변글 + 추가답변 단건 조회
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno,  Model model, int memberNo);
	//질문글 작성
	public Map<String, Object> writeQuestion(QuestionVO vo, MemberVO mvo);
	
	//질문글 수정
	public Map<String, Object> modifyQuestion(QuestionVO vo);
	public QuestionVO deleteQuestion(int qno);
	
	/*답변글*/
	//답변글 작성
	public Map<String, Object> writeAnswer(QuestionVO vo);
	//답변글 수정
	public Map<String, Object> modifyAnswer(QuestionVO vo);
	//답변글 채택
	public int adoptAnswer(int ano);
	
	/*추가질문답변*/
	//추가질문답변 작성
	public Map<String, Object> writeQuestionAdd(QuestionVO vo);
	
	
} 
