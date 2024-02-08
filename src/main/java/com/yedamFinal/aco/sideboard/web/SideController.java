package com.yedamFinal.aco.sideboard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yedamFinal.aco.sideboard.SideVO;
import com.yedamFinal.aco.sideboard.serviceImpl.SideServiceImpl;
@Controller
public class SideController {
	@Autowired
	SideServiceImpl sideService;
	
	@GetMapping("/sideProject/{status}")
	public String getsideProjectForm(@PathVariable("status") String status, Model model) {
		List<SideVO> list = sideService.getRecruitingList(status);
		model.addAttribute("recList", list);
		return "sideboard/sideProject";
	}
	

}
