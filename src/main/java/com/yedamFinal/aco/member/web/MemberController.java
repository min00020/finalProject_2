package com.yedamFinal.aco.member.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;

@Controller
@PropertySource("classpath:config.properties")
public class MemberController {
	
	@Autowired
	private MemberServiceImpl memberService;
	
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
		return "common/mainPage";
	}
	
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm() {
		return "common/createAccount";
	}
	@GetMapping("/myPage")
	public String getMyPageForm(MemberVO memberVO, Model model) {
		List<MemberVO> list = memberService.getMemberInfo(memberVO);
		model.addAttribute("memberInfo", list);
		return "common/myPage"; 
	}
}
