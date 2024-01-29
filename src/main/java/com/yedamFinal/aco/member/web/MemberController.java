package com.yedamFinal.aco.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;

@Controller
public class MemberController {
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@GetMapping("/loginForm")
	public String getLoginForm() {
		return "common/loginForm";
	}
	
	@GetMapping("/")
	public String getMainPageForm(Model model) {
		model.addAttribute("main", "1");
		return "common/mainPage";
	}
	
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm(Model model) {
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		return "common/createAccount";
	}
	
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String,Object> checkDuplicateID(@RequestParam String id) {
		var retData = memberService.checkDuplicateId(id);
		return retData;
	}
	
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Object> checkDuplicateEmail(@RequestParam String email) {
		return memberService.checkDuplicateEmail(email);
	}
	
	@GetMapping("/authPhoneNum")
	@ResponseBody
	public Map<String, Object> sendAuthNumber(@RequestParam String phoneNum) {
		return memberService.sendAuthNumberToPhone(phoneNum);
	}
	
	@GetMapping("/verifyAuthPhoneNum")
	@ResponseBody
	public Map<String, Object> verifyAuthNumber(@RequestParam String authNum, @RequestParam String phoneNum) {
		return memberService.verifyAuthNumber(authNum,phoneNum);	
	}

	//RequestParam은 1:1 (보내는 key와 받는 매개변수가) 매핑이지만 ModelAttribute는 객체매핑.
	@PostMapping("/join")
	public Map<String, Object> joinMember(MemberVO member, @RequestPart("profileImage") MultipartFile profileImage) {
		
		int b = 90;
		return new HashMap<String,Object>();
	}
}
