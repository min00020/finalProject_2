package com.yedamFinal.aco.member.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

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
            
            int a = 90;
        }
		
		return "common/mainPage";
	}
	
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm(HttpServletRequest request, Model model) {
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		return "common/createAccount";
	}
	//회원정보 단건조회
	@GetMapping("/myPage")
	public String getMyPageForm(MemberVO memberVO, Model model) {
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MyemoticonVO> emoinfo = memberService.getMyemoList(memberVO);
		model.addAttribute("emoInfo", emoinfo);
		model.addAttribute("memberInfo", findVO);
		return "common/myPage"; 
	}
	// 책갈피목록, 질문글 목록
	@GetMapping("/myPage2")
	public String getMyPageForm2(MemberVO memberVO, Model model) {
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MybookmarkVO> bmark = memberService.getMybmList(memberVO);
		List<MyquestionVO> myquestion = memberService.getMyqList(memberVO);
		List<MybookmarkVO> bmarkList = memberService.getMyBookList(memberVO);
		model.addAttribute("memberInfo", findVO);
		model.addAttribute("bmarkList", bmark);
		model.addAttribute("mquestionList", myquestion);
		model.addAttribute("bookmarkList2", bmarkList);
		return "common/myPage2";
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
	
	@PostMapping("/login")
	@ResponseBody
	public Map<String,Object> login(@RequestParam("userid") String userid, @RequestParam("userid") String userpw) {
		return memberService.loginMember(userid, userpw);
	}
	
	@GetMapping("/gitLinkPage")
	public String gitLinkPageForm(HttpServletRequest req, String id, Model model) {
		if(id != null)
			req.getSession().setAttribute("tempId", id);
		model.addAttribute("clientId",gitClientId);
		return "common/gitLinkPage";
	}
	
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
