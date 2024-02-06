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

import com.yedamFinal.aco.activity.ActivityPointVO;
import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.MemberQuestionChartVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.point.PointDetailJoinVO;
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
		if (join != null && join.equals("1")) {
			model.addAttribute("join", 1);
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

	// 회원정보 단건조회
	@GetMapping("/myPage")
	public String getMyPageForm(Model model) {
		// MemberVO 꺼내오기.
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}

		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MyemoticonVO> emoinfo = memberService.getMyemoList(memberVO);
		List<PointDetailJoinVO> list = memberService.getPointList(memberVO);
		List<ActivityPointVO> list2 = memberService.getActivityList(memberVO);
		model.addAttribute("pointList", list);
		model.addAttribute("activityList", list2);
		model.addAttribute("emoInfo", emoinfo);
		model.addAttribute("memberInfo", findVO);
		return "common/myPage";
	}
	
	
	@PostMapping("/updateMemberPoint")
	@ResponseBody
	public Map<String,Object> updateResPoint(@RequestParam Integer resPoint) {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}
		
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		Map<String,Object> mp = memberService.updateMemberPoint(resPoint, findVO);
		MemberVO findVO2 = memberService.getMemberInfo(memberVO);
		mp.put("memberInfo", findVO2);
		
		return mp;
	}

	@GetMapping("/deleteBookmark")
	@ResponseBody
	public Map<String, Object>  delBookmarkList(@RequestParam Integer questionBoardNo) {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		Map<String, Object> map = new HashMap<>();
		
		boolean result = memberService.delBookmarkList(questionBoardNo, memberVO.getMemberNo());
		
		if(result) {
			map.put("result", "200");
		}else {
			map.put("result", "500");
		}
		
		return map;
	}
	

	// 책갈피목록, 질문글 목록
	@GetMapping("/myPage2")
	public String getMyPageForm2(Model model) {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}
		MemberQuestionChartVO chartVO = memberService.getMemberChart(memberVO);
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MybookmarkVO> bmark = memberService.getMybmList(memberVO);
		List<MyquestionVO> myquestion = memberService.getMyqList(memberVO);
		List<MybookmarkVO> bmarkList = memberService.getMyBookList(memberVO);
		model.addAttribute("memberInfo", findVO);
		model.addAttribute("bmarkList", bmark);
		model.addAttribute("mquestionList", myquestion);
		model.addAttribute("bookmarkList2", bmarkList);
		model.addAttribute("memberChart", chartVO); 

		return "common/myPage2";
	}
	
	// min 아이디 중복체크 요청
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String, Object> checkDuplicateID(@RequestParam String id) {
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
		return memberService.verifyAuthNumber(authNum, phoneNum);
	}

	// min 회원가입
	@PostMapping("/join")
	@ResponseBody
	public Map<String, Object> joinMember(MemberVO member, MultipartFile file) {
		if (member == null) {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("result", "400");
			return ret;
		}

		return memberService.joinMember(member, file);
	}
	// min login처리(안씀)
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam("userid") String userid, @RequestParam("userid") String userpw) {
		return memberService.loginMember(userid, userpw);
	}
	// min 깃허브 연동 page
	@GetMapping("/gitLinkPage")
	public String gitLinkPageForm(HttpServletRequest req, String id, Model model) {
		if (id != null)
			req.getSession().setAttribute("tempId", id);
		model.addAttribute("clientId", gitClientId);
		return "common/gitLinkPage";
	}
	// min 깃허브 연동
	@PostMapping("/gitLink")
	@ResponseBody
	public Map<String, Object> gitLink(HttpServletRequest req, @RequestParam String gitCode, Model model) {
		return memberService.processGitLink((String) req.getSession().getAttribute("tempId"), gitCode);
	}

	@GetMapping("/test")
	public String test() {
		return "common/test";
	}
	
	// min 계정찾기 폼
	@GetMapping("/findAccount")
	public String findAccountForm() {
		return "common/findAccount";
	}

	// min 계정찾기 (이메일 전송)
	@PostMapping("/findAccount")
	@ResponseBody
	public Map<String,Object> findAccount(@RequestParam String email) {
		return memberService.findAccount(email);
	}
	
	// min 계정찾기 (비밀번호 변경 화면 -> accessKey 검증)
	@GetMapping("/changePassword")
	public String changePasswordForm(@RequestParam String key, Model model) {
		if(!memberService.verifyChangePasswordForm(key)) {
			return "common/errorPage";
		}
		
		model.addAttribute("accessKey",key);
		return "common/changePassword";
	}

	// min 계정찾기 (비밀번호 변경 -> accessKey 검증)
	@PostMapping("/changePassword")
	@ResponseBody
	public Map<String,Object> changePassword(@RequestParam String accessKey, @RequestParam String password, @RequestParam String passwordVerify) {
		return memberService.changePassword(accessKey,password,passwordVerify);
	}
	
}

