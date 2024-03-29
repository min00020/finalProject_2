package com.yedamFinal.aco.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionService {
	/*질문글 목록*/
	public void getQuestionList(Model model, int pageNo, String search);
	public void getQuestionListTopic(Model model, int pageNo, String topic, String search);
	
	//질문글 + 답변글 + 추가답변 단건 조회
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno,  Model model, int memberNo);
	public Map<String, Object> updateBookmark(int qno, int memberNo);
	
	/*질문글*/
	public Map<String, Object> writeQuestion(QuestionVO vo, MemberVO mvo);
	public Map<String, Object> modifyQuestion(QuestionVO vo);
	public QuestionVO deleteQuestion(int qno);
	
	/*답변글*/
	public Map<String, Object> writeAnswer(QuestionVO vo, MemberVO mvo);
	public Map<String, Object> modifyAnswer(QuestionVO vo);
	public int adoptAnswer(int ano, MemberVO mvo);
	
	/*추가질문답변*/
	public Map<String, Object> writeQuestionAdd(QuestionVO vo);
	public int adoptAddAnswer(int questionAddNo);
	
} 
