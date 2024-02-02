package com.yedamFinal.aco.member.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;

@Controller
public class MemberController {
	
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private FreeBoardService freeBoardService;
	
	@Value("${github.oauth.client.id}")
	private String gitClientId;
	
	@GetMapping("/loginForm")
	public String getLoginForm(String join, Model model) {
		if(join != null && join.equals("1")) {
			model.addAttribute("join",1);
		}
		return "common/loginForm";
	}
	
	@GetMapping("/")
	public String getMainPageForm(Model model) {
		model.addAttribute("main", "1");
		model.addAttribute("getFreeBoardList", freeBoardService.getFreeBoardAll());
		
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
            MemberVO username = userDetails.getMemberVO();
        }
		
		return "common/mainPage";
	}
	
	// min 회원가입 form
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm(HttpServletRequest request, Model model) {
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		return "common/createAccount";
	}
	
	// min 아이디 중복체크 요청
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String,Object> checkDuplicateID(@RequestParam String id) {
		var retData = memberService.checkDuplicateId(id);
		return retData;
	}
	
	// min 이메일 인증요청
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Object> checkDuplicateEmail(@RequestParam String email) {
		return memberService.checkDuplicateEmail(email);
	}
	
	// min 번호 인증요청
	@GetMapping("/authPhoneNum")
	@ResponseBody
	public Map<String, Object> sendAuthNumber(@RequestParam String phoneNum) {
		return memberService.sendAuthNumberToPhone(phoneNum);
	}
	
	// min 번호 인증 확인(시연 시 실제 문자서비스 이용)
	@GetMapping("/verifyAuthPhoneNum")
	@ResponseBody
	public Map<String, Object> verifyAuthNumber(@RequestParam String authNum, @RequestParam String phoneNum) {
		return memberService.verifyAuthNumber(authNum,phoneNum);	
	}

	// min 회원가입
	@PostMapping("/join")
	@ResponseBody
	public Map<String, Object> joinMember(MemberVO member, MultipartFile file) {  
		if(member == null) {
			Map<String,Object> ret = new HashMap<String, Object>();
			ret.put("result", "400");
			return ret;
		}
		
		return memberService.joinMember(member, file);
	}
	
	// min login처리(안씀)
	@PostMapping("/login")
	@ResponseBody
	public Map<String,Object> login(@RequestParam("userid") String userid, @RequestParam("userid") String userpw) {
		return memberService.loginMember(userid, userpw);
	}
	
	// min 깃허브 연동 page
	@GetMapping("/gitLinkPage")
	public String gitLinkPageForm(HttpServletRequest req, String id, Model model) {
		if(id != null)
			req.getSession().setAttribute("tempId", id);
		model.addAttribute("clientId",gitClientId);
		return "common/gitLinkPage";
	}
	
	// min 깃허브 연동
	@PostMapping("/gitLink")
	@ResponseBody
	public Map<String,Object> gitLink(HttpServletRequest req, @RequestParam String gitCode, Model model) {
		return memberService.processGitLink((String)req.getSession().getAttribute("tempId"), gitCode);
	}
	
	@GetMapping("/test")
	public String test() {
		return "common/test";
	}
	
	
}

