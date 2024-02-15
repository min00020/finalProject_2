package com.yedamFinal.aco.question.web;

import java.util.HashMap;

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
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.service.QuestionService;


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


@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private MemberServiceImpl memberService;
	
    /**
    * 질문&답변 게시판의 질문글과 답변글 전체를 조회한다
    * @param questionVO 
	* @return String
	*/
	@GetMapping("/questionList")
	public String getquestionBoard(@RequestParam int pageNo, Model model) {
		questionService.getQuestionList(model, Integer.valueOf(pageNo));
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
		return "question/questionList";
	}
	
	/**
	* 질문&답변 게시판의 질문글과 답변글의 분류를 나누어 조회한다
	* @param questionVO 
	* @return String
	*/
	@GetMapping("/questionList/{topic}")
	public String getquestionSelect(@PathVariable("topic") String topic, Model model) {
		model.addAttribute("questionList", questionService.getQuestionListSelect(topic));
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
		
		return "question/questionList";
	}
	
	/**
	* 질문&답변 게시판의 질문글과 질문글에 달린 답변을 상세 조회한다
	* @param questionVO 
	* @return String
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
		
		int memberNo = username != null ? username.getMemberNo():-1;
		
		var result = questionService.getQuestionInfo(qno,model,memberNo);
		model.addAttribute("questionInfo", result);
		
		
		return "question/questionInfo";
	}
	
	/**
	* 질문글 작성폼에서 새 글 작성폼 혹은 기존 글 수정폼을 띄워준다.
	* @param bno 
	* @return String
	*/
	@GetMapping("/questionWrite")
	public String questionWrite(Integer bno, Model model) {
		
		if(bno != null) {
			var map = questionService.getQuestionInfo(bno, model, -1);
			var entry = map.entrySet().iterator().next();
			List<QuestionVO> value = entry.getValue();
			
			QuestionVO questionVO = value.get(0);
			model.addAttribute("questionVO",questionVO);
		}
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		
		return "question/questionWrite";
	}
	
	/**
	* 질문&답변 게시판에 질문글을 작성한다
	* @param QuestionVO 
	* @return String
	*/
	@PostMapping("/questionWrite")
	@ResponseBody
	public Map<String, Object> writeQuestion(QuestionVO question){
		return questionService.writeQuestion(question);
		/*
		 * Map<String, Object> ret = new HashMap<String, Object>();
		 * questionService.writeQuestion(question); 
		 * ret.put("result", "400");
		 * 
		 * return ret;
		 */
	}

	/**
	* 질문&답변 게시판에 질문글을 수정한다
	* @param QuestionVO 
	* @return String
	*/
	@PostMapping("/questionModify")
	@ResponseBody
	public Map<String, Object> modifyQuestion(QuestionVO question){
		return questionService.modifyQuestion(question);
	}
	
	/**
	* 질문&답변 게시판에 답변글을 작성한다
	* @param QuestionVO 
	* @return String
	*/
	@PostMapping("/answerWrite")
	@ResponseBody
	public Map<String, Object> writeAnswer(QuestionVO question){
		return questionService.writeAnswer(question);
	}
	
	
	
}