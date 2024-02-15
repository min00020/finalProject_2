package com.yedamFinal.aco.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.question.QuestionVO;

public interface QuestionMapper {
	/*질문글*/
	//질문 리스트 전제조회
	public List<QuestionVO> getQuestionList(@Param(value="pageNo") int pageNo);
	public int getQuestionCount();
	//질문 리스트 분류조회
	public List<QuestionVO> getQuestionListSelect(String topic);
	
	/*질문글 + 답변글 + 추가답변 단건 조회*/
	public List<QuestionVO> getQuestionInfo(int qno);
	//조회수 +1
	public int updateQuestionViewCnt(int qno);
	
	/*질문글 작성*/
	public int insertQuestion(QuestionVO questionVO);
	
	
	//질문글 수정
	public int updateQuestion(QuestionVO questionVO);
	public QuestionVO deleteQuestion(int qno);
	
	
	/*답변글*/
	//답변글 작성
	public int insertAnswer(QuestionVO questionVO);
	public int plusAnswerCnt(int qno);
	
	//답변글 수정
	public int updateAnswer(QuestionVO questionVO);
}