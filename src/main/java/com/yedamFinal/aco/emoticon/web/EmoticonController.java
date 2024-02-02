package com.yedamFinal.aco.emoticon.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.service.AdminService;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

@Controller
public class EmoticonController {
	@Autowired
	AdminService adminService;
	
	@GetMapping("/emoMain")
	public String getEmoMain(Model model) {
		List<AdminEmoVO> list = adminService.getMainEmoList();
		model.addAttribute("emoList",list);
		return "emoticon/emoMain";
	}
	
	//이모티콘 상세
	@GetMapping("/emoDetail")
	public String getEmoDetail(AdminEmoVO adminEmoVO, Model model) {
		AdminEmoVO emoVO = adminService.getEmoDetail(adminEmoVO);
		model.addAttribute("emoDetail",emoVO);
		return "emoticon/emoDetail";
	}
	
	//내 이모티콘 구매목록
	@GetMapping("/emoBuyList")
	public String getEmoBuyList(Model model) {
		// MemberVO 꺼내오기.
		int memberNo = -1;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			MemberVO username = userDetails.getMemberVO();
			memberNo = username.getMemberNo();
		}
		List<AdminEmoVO> list = adminService.getEmoBuyList(memberNo);
		model.addAttribute("emoBuyList", list);
		return "emoticon/emoBuyList";
	}
	
}
