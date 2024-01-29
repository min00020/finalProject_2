package com.yedamFinal.aco.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String getMainPageForm() {
		return "common/mainPage";
	}
	
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm() {
		return "common/createAccount";
	}
}
