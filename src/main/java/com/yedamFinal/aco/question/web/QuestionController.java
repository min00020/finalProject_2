package com.yedamFinal.aco.question.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.service.MemberService;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.question.service.QuestionService;

import lombok.extern.log4j.Log4j2;


 /**
 * @author 김채민
 * @since 2024.02.13
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *  
 *  *   수정일     수정자          수정내용
 *  -------    --------    ---------------------------
 *  2024.02.01   김채민          최초 생성
 *  </pre>
 * 
 * 
 **/

@Log4j2
@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private MemberService memberService;
	
    /**
    * 질문글과 답변글 전체조회
    * @param pageNo 
    * @param model 
	* @return question/questionList
	*/
	@GetMapping("/questionList")
	public String getquestionBoard(@RequestParam int pageNo, String topic, Model model, String search) {
		if(topic == null) {
			questionService.getQuestionList(model, Integer.valueOf(pageNo),search);
		}
		else {
			questionService.getQuestionListTopic(model, Integer.valueOf(pageNo), topic, search);
		}
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			model.addAttribute("loginId", username.getId());
		}
		else {
			model.addAttribute("loginId", "-1");
		}
		model.addAttribute("topic", topic);
		model.addAttribute("search", search);
		
		return "question/questionList";
	}
	
	
	/**
	* 질문글 상세조회 페이지
	* @param qno 
	* @param model
	* @return question/questionInfo
	*/
	@GetMapping("/questionInfo/{qno}")
	public String getQuestionInfo(@PathVariable("qno") int qno, Model model) {
		// MemberVO 꺼내오기.
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			model.addAttribute("loginId", username.getId());
		}
		else {
			model.addAttribute("loginId", "-1");
		}
		//로그인아이디 확인용
		int memberNo = username != null ? username.getMemberNo():-1;
		
		var result = questionService.getQuestionInfo(qno, model, memberNo);
		model.addAttribute("questionInfo", result);
		
		return "question/questionInfo";
	}
	
	/**
	* 질문글 북마크
	* @param MybookmarkVO 
	* @return 
	*/
	@PostMapping("/bookmarkUpdate")
	@ResponseBody
	public Map<String, Object> updateBookmark(int qno){
		//memberVO
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			username = memberService.getMemberInfo(username);
		}
		return questionService.updateBookmark(qno, username.getMemberNo());
	}
	
	/**
	* 질문글 작성폼 : 새 글 작성폼 혹은 기존 글 수정폼
	* @param bno 
	* @param model 
	* @return String
	*/
	@GetMapping("/questionWrite")
	public String questionWrite(Integer bno, Model model) {
		//보유 포인트조회
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			username = memberService.getMemberInfo(username);
			model.addAttribute("memberInfo", username);
		}
		
		//작성폼인지 수정폼인지 판단하기
		QuestionVO questionVO = new QuestionVO();
		if(bno != null) {
			var map = questionService.getQuestionInfo(bno, model, -1);
			var entry = map.entrySet().iterator().next();
			List<QuestionVO> value = entry.getValue();
			
			questionVO = value.get(0);
		}
		model.addAttribute("questionVO",questionVO);
		
		//작성폼 태그
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		
		return "question/questionWrite";
	}
	
	/**
	* 질문글 작성
	* @param QuestionVO 
	* @return Map<String, Object>
	*/
	@PostMapping("/questionWrite")
	@ResponseBody
	public Map<String, Object> writeQuestion(QuestionVO question){
		//memberVO
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			username = memberService.getMemberInfo(username);
		}
			
		return questionService.writeQuestion(question, username);
	}

	/**
	* 질문글 수정
	* @param question 
	* @return Map<String, Object>
	*/
	@PostMapping("/questionModify")
	@ResponseBody
	public Map<String, Object> modifyQuestion(QuestionVO question){
		return questionService.modifyQuestion(question);
	}
	
	/**
	* 답변글 작성
	* @param question 
	* @return String
	*/
	@PostMapping("/answerWrite")
	@ResponseBody
	public Map<String, Object> writeAnswer(QuestionVO question){
		MemberVO username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			username = userDetails.getMemberVO();
			username = memberService.getMemberInfo(username);
			
			//활동점수 계산
			username.getAccumActivityPoint();
			username.getAvailableActivityPoint();
		}
		
		return questionService.writeAnswer(question, username);
	}
	
	/**
	* 답변글 수정
	* @param question 
	* @return questionService.modifyAnswer(question)
	*/
	@PostMapping("/answerModify")
	@ResponseBody
	public Map<String, Object> modifyAnswer(QuestionVO question){
		return questionService.modifyAnswer(question);
	}
	
	/**
	* 답변글 채택
	* @param question 
	* @return Map<String, Object>
	*/
	@PostMapping("/answerAdopt")
	@ResponseBody
	public int adoptAnswer(int ano){
		//memberVO
				MemberVO username = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
					UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
					username = userDetails.getMemberVO();
					username = memberService.getMemberInfo(username);
				}
		return questionService.adoptAnswer(ano,username);
	}
	
	/**
	* 추가질문 작성
	* @param question 
	* @return String
	*/
	@PostMapping("/questionAddWrite")
	@ResponseBody
	public Map<String, Object> writeQuestionAdd(QuestionVO question){
		return questionService.writeQuestionAdd(question);
	}
	
	/**
	* 추가질문 답변채택
	* @param question 
	* @return Map<String, Object>
	*/
	@PostMapping("/answerAddAdopt")
	@ResponseBody
	public int adoptAddAnswer(int questionAddNo){
		return questionService.adoptAddAnswer(questionAddNo);
	}
	
}