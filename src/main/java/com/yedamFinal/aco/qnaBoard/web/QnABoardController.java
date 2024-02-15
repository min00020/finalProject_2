package com.yedamFinal.aco.qnaBoard.web;

import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.qnaBoard.QnABoardJoinVO;
import com.yedamFinal.aco.qnaBoard.serviceImpl.QnABoardServiceImpl;

@Controller
public class QnABoardController {
	@Autowired
	private QnABoardServiceImpl qnaBoardService;
	
	/**
     * 문의게시판 목록 불러오기(페이징,검색,정렬등)
     * @param pg
     * @param search
     * @param ob
     * @param req
     * @param model
     * @return "qnaboard/qnaList"
     */
	@GetMapping("/qnaBoard")
	public String getQnaBoardListForm(@RequestParam String pg, String search, String ob, HttpServletRequest req, Model model) {// MemberVO 꺼내오기.
		MemberVO vo = null;
		Map<String,Object> ret = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();            
            if(vo == null) {
            	return "redirect:/loginForm";
            }
            // 검색창 입력의 경우.
            if(search == null)
            	ret = qnaBoardService.getMyQnaBoardList(Integer.valueOf(pg), vo, ob);
            else {
            	ret = qnaBoardService.getMyQnaBoardListFromSearch(Integer.valueOf(pg), search, vo, ob);
            	model.addAttribute("search",search); // 해당 search키워드로 페이징해야함.
            }
            
            if(ret == null) {
            	return "redirect:/loginForm";
            }
            
            // 알림창 띄우기위해서
            HttpSession session = req.getSession();
            Object result = session.getAttribute("qnaInsert");
            if(result != null) {
            	session.removeAttribute("qnaInsert");
            	model.addAttribute("qnaInsert","1");
            }
            
            model.addAttribute("qnaInfo",ret);
        }
        else {
        	return "redirect:/";
        }
		
		return "qnaboard/qnaList";
	}
	
	/**
     * 문의게시판 작성 폼 불러오기
     * @param model
     * @return "qnaboard/qnaWrite"
     */
	@GetMapping("/qnaBoard/write")
	public String getQnaBoardWriteForm(Model model) {
		return "qnaboard/qnaWrite";
	}
	
	/**
     * 문의게시판 작성 요청(다중 첨부파일처리 추가)
     * @param title
     * @param content
     * @param attachFile
     * @param model
     * @param req
     * @return Map<String,Object>
     */
	@PostMapping("/qnaBoard/write")
	@ResponseBody
	public Map<String,Object> insertQnaBoard(@RequestParam String title, @RequestParam String content, MultipartFile[] attachFile, HttpServletRequest req) {
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        }
        var ret = qnaBoardService.insertQnaBoard(vo.getMemberNo(), title, content, attachFile);
        if(ret.get("result").equals("200")) {
        	req.getSession().setAttribute("qnaInsert", "1");
        }
        
		return ret;
	}
	
	/**
     * 문의게시판 상세페이지 화면 요청
     * @param qnaBoardNo
     * @param model
     * @return qnaboard/qnaInfo
     */
	@GetMapping("/qnaBoard/{boardNo}")
	public String getQnaBoardInfoPage(@PathVariable("boardNo") int qnaBoardNo, Model model) {
		if(!qnaBoardService.getQnaBoardDetailInfo(model,qnaBoardNo)) {
			return "redirect:/";
		}
		
		// 요청한게 본인게 아니라면
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        }
        
        Object qnaInfoList = model.getAttribute("qnaInfo");
        if(qnaInfoList == null) {
        	return "redirect:/";
        }
        
        try {
        	@SuppressWarnings("unchecked")
			ArrayList<QnABoardJoinVO> list = ((ArrayList<QnABoardJoinVO>)qnaInfoList);
            // 어드민은 무조건 통과
        	if(vo.getPermission().equals("ROLE_USER") && vo.getMemberNo() != list.get(0).getMemberNo()) {
            	return "redirect:/";
            }
        }
        catch(Exception e) {
        	System.out.println(e);
        	return "redirect:/";
        }
		
    	
		return "qnaboard/qnaInfo";
	}
	
	/**
     * 문의게시판 답변 작성/수정
     * @param qnaBoardNo
     * @param answer
     * @return Map<String,Object>
     */
	@PostMapping("/qnaBoard/{boardNo}")
	@ResponseBody
	public Map<String,Object> postQnABoardAnswer(@PathVariable("boardNo") int qnaBoardNo, String answer) {
		return qnaBoardService.postQnAAnswer(qnaBoardNo, answer);
	}
	
	/**
     * 문의게시판 질문 수정/상태변경
     * @param qnaBoardNo
     * @param answer
     * @return Map<String,Object>
     */
	@PutMapping("/qnaBoard/{boardNo}")
	@ResponseBody
	public Map<String, Object> changeQnABoardState(@PathVariable("boardNo") int qnaBoardNo, String state, String modifyComment) {
		if(state != null && !state.equals("")) {
			return qnaBoardService.changeQnAState(qnaBoardNo, state);
		}
		else {
			return qnaBoardService.modifyQnAQuestion(qnaBoardNo, modifyComment);
		}
		
	}
}
