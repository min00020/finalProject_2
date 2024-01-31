package com.yedamFinal.aco.member.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

@Controller
@PropertySource("classpath:config.properties")
public class MemberController {
	
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private FreeBoardService freeBoardService;
	
	@Value("${test}")
	private int test = 0;
	
	@GetMapping("/loginForm")
	public String getLoginForm() {
		
		int a = test;
		return "common/loginForm";
	}
	
	@GetMapping("/")
	public String getMainPageForm(Model model) {
		model.addAttribute("main", "1");
		model.addAttribute("getFreeBoardList", freeBoardService.getFreeBoardAll());
		
		
		return "common/mainPage";
	}
	
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm() {
		return "common/createAccount";
	}
	//회원정보 단건조회
	@GetMapping("/myPage")
	public String getMyPageForm(MemberVO memberVO, Model model) {
		List<MemberVO> list = memberService.getMemberInfo(memberVO);
		List<MyemoticonVO> emoinfo = memberService.getMyemoList(memberVO);
		model.addAttribute("emoInfo", emoinfo);
		model.addAttribute("memberInfo", list);
		return "common/myPage"; 
	}
	// 책갈피목록, 질문글 목록
	@GetMapping("/myPage2")
	public String getMyPageForm2(MemberVO memberVO, Model model) {
		List<MemberVO> list = memberService.getMemberInfo(memberVO);
		List<MybookmarkVO> bmark = memberService.getMybmList(memberVO);
		List<MyquestionVO> myquestion = memberService.getMyqList(memberVO);
		model.addAttribute("memberInfo", list);
		model.addAttribute("bmarkList", bmark);
		model.addAttribute("mquestionList", myquestion);
		return "common/myPage2";
	}
	
	
	
	@GetMapping("/zxc")
	public String zxc() {
		return "common/zxzx";
	}
}
