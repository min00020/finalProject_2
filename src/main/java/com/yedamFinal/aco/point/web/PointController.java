package com.yedamFinal.aco.point.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedamFinal.aco.point.service.PointService;

@Controller
public class PointController {

	@Autowired 
	private PointService pointService;
	
	@Value("${nh.bank.Iscd}")
	private String nhIscd;
	
	@Value("${nh.bank.accessToken}")
	private String nhAccessToken;
	
	//포인트 충전
	@GetMapping("/point")
	public String getPointMainForm(Model model) {
		
		model.addAttribute("getAccountList", pointService.getAccountAll());
		
		return "common/pointCharging";
	}
	//계좌 연결 은행 선택
	@GetMapping("/accountConnect")
	public String getConnectAccount(Model model) {
		model.addAttribute("getBankList", pointService.getBankAll());
		
		
		return "common/accountConnect";
	}
	//계좌번호 입력
	@GetMapping("/insertAccountNo")
	public String getInserstAccountNo(@RequestParam String bankCode, @RequestParam String bankName, Model model) {
		model.addAttribute("bankCode",bankCode);
		model.addAttribute("bankName",bankName);
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);
	
		return "common/insertAccountNo";
	}
	//
	
	
	
	
}
