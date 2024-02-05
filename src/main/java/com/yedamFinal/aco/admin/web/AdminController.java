package com.yedamFinal.aco.admin.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;
import com.yedamFinal.aco.admin.service.AdminService;


@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	
	
	@GetMapping("/admin")
	public String getAdminPageForm(Model model, int pageNo) {
		List<AdminMainVO> list = adminService.getAdCntList();
		model.addAttribute("adminCnt", list);
		
		var ret = adminService.getAdNoticeList(pageNo);
		model.addAttribute("adminNotice", ret.get("noticeList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		
		return "layout/admin/adminTemplate";
	}
	@GetMapping("/deleteNotice")
	public String deleteNoticeProcess(int noticeBoardNo) {
		adminService.deleteNotice(noticeBoardNo);
		return "redirect:admin?pageNo=1";
	}
	@GetMapping("/adminMember")
	public String getAdminMemberPageForm(Model model, int pageNo, String leaveStatus) {
		var ret = adminService.getAdMemberList(pageNo, leaveStatus);
		model.addAttribute("adminMember",ret.get("memberList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		model.addAttribute("leaveStatus",leaveStatus);
		
		return "layout/admin/adminMember";
	}
	@GetMapping("/adminStat")
	public String getAdminStatPageForm() {
		return "layout/admin/adminStat";
	}
	@GetMapping("/adminReport")
	public String getAdminReportPageForm(Model model) {
		List<AdminReportVO> list = adminService.getAdReportList();
		model.addAttribute("adminReport", list);
		return "layout/admin/adminReport";
	}
	@GetMapping("/adminQna")
	public String getAdminQnaPageForm(Model model) {
		List<AdminQnaVO> list = adminService.getAdQnaList();
		model.addAttribute("adminQna", list);
		return "layout/admin/adminQna";
	}
	@GetMapping("/adminSettle")
	public String getAdminSettlePageForm(Model model) {
		List<AdminSettleVO> list = adminService.getAdSettleList();
		model.addAttribute("adminSettle", list);
		return "layout/admin/adminSettle";
	}
	@GetMapping("/adminEmo")
	public String getAdminEmoPageForm(Model model) {
		List<AdminEmoVO> list = adminService.getAdEmoList();
		model.addAttribute("adminEmo", list);
		return "layout/admin/adminEmo";
	}
	
	//이모티콘 등록
	@PostMapping("/insertEmo")
	public String insertEmoProcess(AdminEmoVO adminEmoVO) {
		adminService.insertEmo(adminEmoVO);
		return "redirect:adminEmo";
	}
	//이모티콘 판매종료
	@GetMapping("/updateEmo")
	public String updateEmoProcess(int emoNo) {
		adminService.updateEmo(emoNo);
		return "redirect:adminEmo";
	}
	//이모티콘 판매재개
	@GetMapping("/updateEmo2")
	public String updateEmo2Process(int emoNo) {
		adminService.updateEmo2(emoNo);
		return "redirect:adminEmo";
	}
	
	/*
	 * public String insertEmoProcess(AdminEmoVO adminEmoVO, MultipartFile[] files)
	 * { adminService.insertEmo(adminEmoVO, files); return "redirect:adminEmo"; }
	 */
	
	//공지등록
	@PostMapping("/insertNotice")
	public String insertNoticeProcess(AdminMainVO adminMainVO) {
		adminService.insertNotice(adminMainVO);
		return "redirect:insertNotice";
	}
	// 이모티콘 드롭박스
	@GetMapping("/adminSaleEmo")
	public String getSaleAdminEmoPageForm(Model model, String emoState) {
		List<AdminEmoVO> list = adminService.getSaleAdEmoList(emoState);
		model.addAttribute("adminSaleEmo", list);
		return "layout/admin/adminSaleEmo";
	}
}
