package com.yedamFinal.aco.member.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.MainTotalVO;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.activity.ActivityPointVO;
import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.service.SessionUtil;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.AccountChangeDTO;
import com.yedamFinal.aco.member.MemberQuestionChartVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.SettlementVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.service.MemberService;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.noticeboard.service.NoticeBoardService;
import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.PointDetailJoinVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;
import com.yedamFinal.aco.sideboard.service.SideService;

/**
 * 회원로그인, 마이페이지
 * @author 태경
 * 수정일자    수정자       수정내용
 * ------------------------------------
 *            태경         마이페이지
 *
 */

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private FreeBoardService freeBoardService;
	@Autowired
	private SideService sideService;
	@Autowired
	private NoticeBoardService noticeBoardService;

	@Value("${github.oauth.client.id}")
	private String gitClientId;

	/**
	 * 
	 * @param join
	 * @param model
	 * @return
	 */
	@GetMapping("/loginForm")
	public String getLoginForm(String join, Model model) {
		if (join != null && join.equals("1")) {
			model.addAttribute("join", 1);
		}
		return "common/loginForm";
	}
	
	/**
	 * 메인페이지 
	 * @param model
	 * @return common/mainPage
	 */
	@GetMapping("/")
	public String getMainPageForm(Model model) {
		model.addAttribute("main", "1");
		
		//메인-자유게시판 글 표시
		model.addAttribute("getFreeBoardMainPage", freeBoardService.getFreeBoardMainPage());
		
		//메인-공지사항 글 표시
		model.addAttribute("getNoticeBoardMainPage", freeBoardService.getNoticeBoardMainPage());
		
		//메인-질문&답변 게시판 글 표시
		model.addAttribute("getQuestionBoardMainPage", freeBoardService.getQuestionBoardMainPage());
		
		//메인-사이드프로젝트 게시판 글 표시
		model.addAttribute("getSideProjectBoardMainPage", freeBoardService.getSideProjectBoardMainPage());

		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
            MemberVO username = userDetails.getMemberVO();
        }
		return "common/mainPage";
	}

	//메인페이지 통합검색
	@GetMapping("/mainTotalSearch")
	public String getMainTotalSearch(Model model, @RequestParam String search, @RequestParam int pg) {
		
		List<MainTotalVO> ret = null;
		 // 검색창 입력의 경우.
		if(search == null)
    	   return "/";
       else {
       		ret = freeBoardService.getMainTotalSearch(model,search,pg);
       		model.addAttribute("search",search); // 해당 search키워드로 페이징해야함.
        }
       
        int ret2=freeBoardService.getMainTotalSearchCnt(search);
		model.addAttribute("getMainTotalSearch", ret);
		model.addAttribute("totalCnt",ret2);
		return "freeBoard/mainTotalSearch";
	}
	
	
	// min 회원가입 form
	@GetMapping("/createAccountForm")
	public String getCreateAccountForm(HttpServletRequest request, Model model) {
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		return "common/createAccount";
	}
	
	/**
	 * 마이페이지로 이동(회원정보, 이모티콘 구매내역, 활동 및 포인트 점수, 계좌정보)
	 * @param model 
	 * @return common/myPage 
	 */
	@GetMapping("/myPage")
	public String getMyPageForm(Model model) {
		MemberVO memberVO = SessionUtil.getSession();

		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MyemoticonVO> emoinfo = memberService.getMyemoList(memberVO);
		List<PointDetailJoinVO> list = memberService.getPointList(memberVO);
		List<ActivityPointVO> list2 = memberService.getActivityList(memberVO);
		List<AccountVO> list3 = memberService.getMemberAccountList(memberVO);
		var tagList = memberService.getTagList();
		var sideList = sideService.getParticipateList(findVO.getMemberNo());
		model.addAttribute("sideList", sideList);
		model.addAttribute("tagList", tagList);
		model.addAttribute("accountList", list3);
		model.addAttribute("pointList", list);
		model.addAttribute("activityList", list2);
		model.addAttribute("emoInfo", emoinfo);
		model.addAttribute("memberInfo", findVO);
		return "common/myPage";
	}
	 
	/**
	 * 회원 포인트 환전요청
	 * @param resPoint
	 * @return
	 */
	@PostMapping("/updateMemberPoint")
	@ResponseBody
	public Map<String,Object> updateResPoint(@RequestParam Integer resPoint) {
		MemberVO memberVO = SessionUtil.getSession();
		
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		Map<String,Object> mp = memberService.updateMemberPoint(resPoint, findVO);
		MemberVO findVO2 = memberService.getMemberInfo(memberVO);
		mp.put("memberInfo", findVO2);
		
		return mp;
	}
	
	/**
	 * 마이페이지 북마크 삭제 
	 * @param questionBoardNo
	 * @return map (삭제결과)
	 */
	@GetMapping("/deleteBookmark")
	@ResponseBody
	public Map<String, Object>  delBookmarkList(@RequestParam Integer questionBoardNo) {
		MemberVO memberVO = SessionUtil.getSession();
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
	
	/**
	 * 마이페이지 책갈피목록, 질문글 목록
	 * @param model
	 * @return common/myPage2
	 */
	@GetMapping("/myPage2")
	public String getMyPageForm2(Model model) {
		MemberVO memberVO = SessionUtil.getSession();
		
		MemberQuestionChartVO chartVO = memberService.getMemberChart(memberVO);
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		List<MybookmarkVO> bmark = memberService.getMybmList(memberVO);
		var ret = memberService.getMyQuestionList(memberVO);
		List<MybookmarkVO> bmarkList = memberService.getMyBookList(memberVO);
		List<PointDetailJoinVO> list = memberService.getPointList(memberVO);
		List<ActivityPointVO> list2 = memberService.getActivityList(memberVO);
		List<AccountVO> list3 = memberService.getMemberAccountList(memberVO);
		List<MyquestionVO> myQuestionList = memberService.getMyQuestionListModal(memberVO);
		var tagList = memberService.getTagList();
		model.addAttribute("tagList", tagList);
		model.addAttribute("accountList", list3);
		model.addAttribute("pointList", list);
		model.addAttribute("activityList", list2);
		model.addAttribute("memberInfo", findVO);
		model.addAttribute("myQuestionList", myQuestionList);
		model.addAttribute("bmarkList", bmark);
		model.addAttribute("mquestionList", ret.get("questionList"));
		model.addAttribute("bookmarkList2", bmarkList);
		model.addAttribute("memberChart", chartVO);

		return "common/myPage2";
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	// min 아이디 중복체크 요청
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String, Object> checkDuplicateID(@RequestParam String id) {
		var retData = memberService.checkDuplicateId(id);
		return retData;
	}
	/**
	 * 
	 * @param email
	 * @return
	 */
	// min 이메일 인증요청
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Object> checkDuplicateEmail(@RequestParam String email) {
		return memberService.checkDuplicateEmail(email);
	}
	/**
	 * 
	 * @param phoneNum
	 * @return
	 */
	// min 번호 인증요청
	@GetMapping("/authPhoneNum")
	@ResponseBody
	public Map<String, Object> sendAuthNumber(@RequestParam String phoneNum) {
		return memberService.sendAuthNumberToPhone(phoneNum);
	}
	/**
	 * 
	 * @param authNum
	 * @param phoneNum
	 * @return
	 */
	// min 번호 인증 확인(시연 시 실제 문자서비스 이용)
	@GetMapping("/verifyAuthPhoneNum")
	@ResponseBody
	public Map<String, Object> verifyAuthNumber(@RequestParam String authNum, @RequestParam String phoneNum) {
		return memberService.verifyAuthNumber(authNum, phoneNum);
	}
	/**
	 * 
	 * @param member
	 * @param file
	 * @return
	 */
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
	/**
	 * 
	 * @param userid
	 * @param userpw
	 * @return
	 */
	// min login처리(안씀)
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam("userid") String userid, @RequestParam("userid") String userpw) {
		return memberService.loginMember(userid, userpw);
	}
	/**
	 * 
	 * @param req
	 * @param id
	 * @param model
	 * @return
	 */
	// min 깃허브 연동 page
	@GetMapping("/gitLinkPage")
	public String gitLinkPageForm(HttpServletRequest req, String id, Model model) {
		if (id != null)
			req.getSession().setAttribute("tempId", id);
		model.addAttribute("clientId", gitClientId);
		return "common/gitLinkPage";
	}
	/**
	 * 
	 * @param req
	 * @param gitCode
	 * @param model
	 * @return
	 */
	// min 깃허브 연동
	@PostMapping("/gitLink")
	@ResponseBody
	public Map<String, Object> gitLink(HttpServletRequest req, @RequestParam String gitCode, Model model) {
		return memberService.processGitLink((String) req.getSession().getAttribute("tempId"), gitCode);
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/test")
	public String test(Model model) {
		
		Map<Integer, List<ReplyJoinVO>> map = new HashMap<Integer, List<ReplyJoinVO>>();
		
		List<ReplyJoinVO> list1 = new ArrayList<ReplyJoinVO>();
		ReplyJoinVO vo = new ReplyJoinVO();
		vo.setParentReplyNo(1);
		vo.setParentComment("hihi");
		list1.add(vo);
		
		map.put(1, list1);
		
		List<ReplyJoinVO> list2 = new ArrayList<ReplyJoinVO>();
		ReplyJoinVO vo2 = new ReplyJoinVO();
		vo2.setParentReplyNo(2);
		vo2.setParentEmoticon("조로_1.png");
		list2.add(vo2);
		
		map.put(2, list2);
		
		model.addAttribute("replyList",map);
		return "common/test";
	}
	/**
	 * 
	 * @return
	 */
	// min 계정찾기 폼
	@GetMapping("/findAccount")
	public String findAccountForm() {
		return "common/findAccount";
	}
	/**
	 * 
	 * @param email
	 * @return
	 */
	// min 계정찾기 (이메일 전송)
	@PostMapping("/findAccount")
	@ResponseBody
	public Map<String,Object> findAccount(@RequestParam String email) {
		return memberService.findAccount(email);
	}
	/**
	 * 
	 * @param key
	 * @param model
	 * @return
	 */
	// min 계정찾기 (비밀번호 변경 화면 -> accessKey 검증)
	@GetMapping("/changePassword")
	public String changePasswordForm(@RequestParam String key, Model model) {
		if(!memberService.verifyChangePasswordForm(key)) {
			return "common/errorPage";
		}
		
		model.addAttribute("accessKey",key);
		return "common/changePassword";
	}
	/**
	 * 
	 * @param accessKey
	 * @param password
	 * @param passwordVerify
	 * @return
	 */
	// min 계정찾기 (비밀번호 변경 -> accessKey 검증)
	@PostMapping("/changePassword")
	@ResponseBody
	public Map<String,Object> changePassword(@RequestParam String accessKey, @RequestParam String password, @RequestParam String passwordVerify) {
		return memberService.changePassword(accessKey,password,passwordVerify);
	}
	@GetMapping("/checkNickname")
	@ResponseBody
	public Map<String, Object> checkDuplicateNickname(@RequestParam String nickName) {
		return memberService.checkDuplicateNickname(nickName);
	}
	
	@PostMapping("/changeAccountInfo")
	@ResponseBody
	public Map<String, Object> changeAccountInfo(AccountChangeDTO changeDTO,HttpServletRequest req) {
		MemberVO memberVO = SessionUtil.getSession();
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		var result = memberService.changeAccountInfo(changeDTO,findVO);
		
		// 업데이트한 개인정보를 다시 session에 담아준다.
		findVO = memberService.getMemberInfo(findVO);
		req.getSession().setAttribute("member", findVO);
		return result;
	}
	
	@PostMapping("/changePasswordMypage")
	@ResponseBody
	public Map<String, Object> changePasswordMypage(@RequestParam String password, @RequestParam String passwordVerify) {
		MemberVO memberVO = SessionUtil.getSession();
		return memberService.changePasswordFromMyPage(password, passwordVerify, memberVO.getId());
	}
	
	
	@GetMapping("/member/{mno}")
	public String getMemberProfileInfo(@PathVariable("mno") int memberNo, @RequestParam int pg, String tp, Model model) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberNo(memberNo);
		memberVO = memberService.getMemberInfo(memberVO);
		if(memberVO == null || memberVO.getId() == null) {
			return "common/errorPage";
		}

		Map<String, Object> ret = memberService.getOtherMemberInfo(pg, tp, memberVO.getMemberNo());
		model.addAttribute("member", memberVO);
		model.addAttribute("tp", tp);
		model.addAttribute("mapResult", ret);
		return "common/memberProfile";
	}
	/**
	 * 정산요청을 보냄
	 * @param settlementReqPoint
	 * @param vo
	 * @return map
	 */
	@PostMapping("/updateSettlement")
	@ResponseBody
	public Map<String, Object> updateSettlement(SettlementVO vo, Integer settlementReqPoint) {
		MemberVO memberVO = SessionUtil.getSession();
		MemberVO findVO = memberService.getMemberInfo(memberVO);
		Map<String,Object> map = memberService.updateSettlement(vo, findVO, settlementReqPoint);
		map.put("memberInfo2", findVO);
		
		return map;
	}
	
}

