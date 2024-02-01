package com.yedamFinal.aco.emoticon.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.service.AdminService;

@Controller
public class EmoticonController {
	@Autowired
	AdminService adminService;
	
	@GetMapping("/emoMain")
	public String getEmoMain(Model model) {
		List<AdminEmoVO> list = adminService.getAdEmoList();
		model.addAttribute("emoList",list);
		return "emoticon/emoMain";
	}
	
	@GetMapping("/emoDetail")
	public String getEmoDetail(AdminEmoVO adminEmoVO, Model model) {
		AdminEmoVO emoVO = adminService.getEmoDetail(adminEmoVO);
		model.addAttribute("emoDetail",emoVO);
		return "emoticon/emoDetail";
	}
	
}
