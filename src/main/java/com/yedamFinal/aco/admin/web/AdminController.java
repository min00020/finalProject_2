package com.yedamFinal.aco.admin.web;
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

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.service.AdminService;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

/**
 * 관리자 페이지 
 * @author 손하랑
 */
@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@Value("${nh.bank.Iscd}")
	private String nhIscd;

	@Value("${nh.bank.accessToken}")
	private String nhAccessToken;
	
	/**
	 * 관리자 메인
	 * @param model
	 * @param pageNo 
	 * @return  layout/admin/adminTemplate
	 */
	@GetMapping("/admin")
	public String getAdminPageForm(Model model, @RequestParam(required = false, defaultValue = "1") int pageNo) {
		//대시보드 현황
		List<AdminMainVO> list = adminService.getAdCntList();
		model.addAttribute("adminCnt", list);
		
		//공지사항
		var ret = adminService.getAdNoticeList(pageNo);
		model.addAttribute("adminNotice", ret.get("noticeList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		
		return "layout/admin/adminTemplate";
	}
	/**
	 * 
	 * @param noticeBoardNo
	 * @return
	 */
	@GetMapping("/deleteNotice")
	public String deleteNoticeProcess(int noticeBoardNo) {
		adminService.deleteNotice(noticeBoardNo);
		return "redirect:admin";
	}
	/**
	 * 회원조회
	 * @param model
	 * @param pageNo
	 * @param leaveStatus
	 * @return
	 */
	@GetMapping("/adminMember")
	public String getAdminMemberPageForm(Model model, int pageNo, String leaveStatus) {
		var ret = adminService.getAdMemberList(pageNo, leaveStatus);
		model.addAttribute("adminMember",ret.get("memberList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("leaveStatus",leaveStatus);
		
		return "layout/admin/adminMember";
	}
	/**
	 * 태그별 게시글, 이모티콘 판매수량 통계 현황 (전체기간) 페이지
	 * @return
	 */
	@GetMapping("/adminStat")
	public String getAdminStatPageForm() {
		return "layout/admin/adminStat";
	}
	
	/**
	 * 태그별 게시글, 이모티콘 판매수량 통계 현황
	 * @param date
	 * @return
	 */
	@ResponseBody
	@GetMapping("/adminStatAjax")
	public Map<String, Object> getAdminStatPageFormAjax(String date) {
		return adminService.getTagEmoList(date);
	}
	
	/**
	 * 기간 태그별 게시글, 이모티콘 판매수량 통계 현황
	 * @param sday
	 * @param eday
	 * @return
	 */
	@ResponseBody
	@GetMapping("/adminStatAjax2")
	public Map<String, Object> getAdminStatPageFormAjax2(String sday, String eday) {
		return adminService.getTagEmoPeriodList(sday, eday);
	}
	
	/**
	 * 신고관리 페이지
	 * @param model
	 * @param pageNo
	 * @param reportStatus
	 * @return
	 */
	@GetMapping("/adminReport")
	public String getAdminReportPageForm(Model model, int pageNo, String reportStatus) {
		var ret = adminService.getAdReportList(pageNo, reportStatus);
		model.addAttribute("adminReport", ret.get("reportList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("reportStatus",reportStatus);
		return "layout/admin/adminReport";
	}
	/**
	 * 
	 * @param model
	 * @param pageNo
	 * @param answerStatus
	 * @return
	 */
	@GetMapping("/adminQna")
	public String getAdminQnaPageForm(Model model, int pageNo, String answerStatus) {
		var ret = adminService.getAdQnaList(pageNo, answerStatus);
		model.addAttribute("adminQna", ret.get("qnaList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("answerStatus",answerStatus);
		return "layout/admin/adminQna";
	}
	/**
	 * 
	 * @param model
	 * @param pageNo
	 * @param processStatus
	 * @return
	 */
	@GetMapping("/adminSettle")
	public String getAdminSettlePageForm(Model model, int pageNo, String processStatus) {
		var ret = adminService.getAdSettleList(pageNo, processStatus);
		model.addAttribute("adminSettle", ret.get("settleList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("processStatus",processStatus);
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);
		var all = adminService.getAllSettleList();
		model.addAttribute("allSettle", all);
		return "layout/admin/adminSettle";
	}
	/**
	 * 
	 * @param model
	 * @param pageNo
	 * @param emoStatus
	 * @return
	 */
	@GetMapping("/adminEmo")
	public String getAdminEmoPageForm(Model model, int pageNo, String emoStatus) {
		var ret = adminService.getAdEmoList(pageNo, emoStatus);
		model.addAttribute("adminEmo", ret.get("emoList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("emoStatus",emoStatus);
		return "layout/admin/adminEmo";
	}
	
	//이모티콘 등록
	/**
	 * 
	 * @param emoName
	 * @param emoPrice
	 * @param emoDesc
	 * @param files
	 * @return
	 */
	@PostMapping("/insertEmo")
	public String insertEmoProcess(AdminEmoVO vo, MultipartFile[] files) {
		adminService.insertEmo(vo, files);
		return "redirect:/adminEmo?pageNo=1";
	}
	
	/**
	 * 이모티콘 판매종료
	 * @param emoNo
	 * @return
	 */
	@GetMapping("/updateEmo")
	public String updateEmoProcess(int emoNo) {
		adminService.updateEmo(emoNo);
		return "redirect:adminEmo?emoStatus=0&pageNo=1";
	}
	
	//이모티콘 판매재개
	/**
	 * 
	 * @param emoNo
	 * @return
	 */
	@GetMapping("/updateEmo2")
	public String updateEmo2Process(int emoNo) {
		adminService.updateEmo2(emoNo);
		return "redirect:adminEmo?emoStatus=1&pageNo=1";
	}
	
	//이모티콘 구매버튼 (포인트 차감)
	/**
	 * 
	 * @param request
	 * @param adminVO
	 * @return
	 */
	@PostMapping("/buyEmo")
	public String buyEmoProcess(HttpServletRequest request, AdminEmoVO adminVO) {
		adminService.buyEmo(adminVO);
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        }
		
		request.getSession().setAttribute("myEmoList", adminService.getMyEmoList(vo.getMemberNo()));
		return "redirect:emoBuyList";
	}
	
	/**
	 * 
	 * @param adminEmoVO
	 * @return
	 */
	@PostMapping("/deleteEmo")
	public String deleteEmoProcess(AdminEmoVO adminEmoVO) {
		adminService.deleteEmo(adminEmoVO);
		return "redirect:emoBuyList";
	}
	
	/*
	 * public String insertEmoProcess(AdminEmoVO adminEmoVO, MultipartFile[] files)
	 * { adminService.insertEmo(adminEmoVO, files); return "redirect:adminEmo"; }
	 */
	
	//공지등록
	/**
	 * 
	 * @param adminMainVO
	 * @return
	 */
	@PostMapping("/insertNotice")
	public String insertNoticeProcess(AdminMainVO adminMainVO) {
		adminService.insertNotice(adminMainVO);
		return "redirect:admin?pageNo=1";
	}
	/**
	 * 개별 정산처리후 상태변경
	 * @param settlementNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updateSettlementStatus")
	public Map<String,Object> updateSettlementStatusProcess(int settlementNo) {
		Map<String,Object> result = new HashMap<String, Object>(); 
		if(adminService.updateSettlementStatus(settlementNo) > 0) {
			result.put("result", "200");
		}
		else {
			result.put("result", "500");
		}
		return result;
	}	
	/**
	 * 일괄 정산처리후 상태변경
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updateAllSettlementStatus")
	public Map<String, Object> updateAllSettlementStatus() {
		Map<String,Object> result = new HashMap<String, Object>(); 
		if(adminService.updateAllSettlementStatus() > 0) {
			result.put("result", "200");
		}
		else {
			result.put("result", "500");
		}
		return result;
	}	
}
