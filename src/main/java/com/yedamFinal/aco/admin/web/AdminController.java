package com.yedamFinal.aco.admin.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String getAdminPageForm(Model model) {
		List<AdminMainVO> list = adminService.getAdCntList();
		List<AdminMainVO> list1 = adminService.getAdNoticeList();
		model.addAttribute("adminCnt", list);
		model.addAttribute("adminNotice", list1);
		return "layout/admin/adminTemplate";
	}
	@GetMapping("/adminMember")
	public String getAdminMemberPageForm(Model model) {
		List<AdminMemberVO> list = adminService.getAdMemberList();
		model.addAttribute("adminMember",list);
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
	//공지등록
	@PostMapping("insertNotice")
	public String insertNoticeProcess(AdminMainVO adminMainVO) {
		adminService.insertNotice(adminMainVO);
		return "redirect:insertNotice";
	}
}
