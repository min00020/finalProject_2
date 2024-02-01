package com.yedamFinal.aco.point.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.point.service.PointService;

@Controller
public class PointController {

	@Autowired 
	private PointService pointService;
	
	
	@GetMapping("/point")
	public String getPointMainForm(Model model) {
		
		model.addAttribute("getAccountList", pointService.getAccountAll());
		
		return "common/pointCharging";
	}
	
	@GetMapping("/AccountConnect")
	public String getConnectAccount(Model model) {
		
		
		
		return "common/accountConnect";
	}
	
	@GetMapping("/InsertAccoountNo")
	public String getInserstAccountNo(Model model) {

		return "common/insertAccountNo";
	}
	
}
