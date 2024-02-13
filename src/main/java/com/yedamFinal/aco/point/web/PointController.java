package com.yedamFinal.aco.point.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.service.MemberService;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.PointDetailVO;
import com.yedamFinal.aco.point.UpdateAcoMoneyDTO;
import com.yedamFinal.aco.point.service.PointService;

@Controller
public class PointController {

	@Autowired
	private PointService pointService;
	
	@Autowired
	private MemberService memberService;

	@Value("${nh.bank.Iscd}")
	private String nhIscd;

	@Value("${nh.bank.accessToken}")
	private String nhAccessToken;

	// 포인트 충전
	@GetMapping("/point")
	public String getPointMainForm(@RequestParam String pg, Model model) {
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			
			
			pointService.getPointMainData(Integer.valueOf(pg), model, username.getMemberNo());
			
		}
		//model.addAttribute("getAccountList", pointService.getAccountAll());
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);
		
		return "common/pointCharging";
	}
	//애코머니,포인트내역
	@PostMapping("/updateAmAndInsertPointDetail")
	@ResponseBody
	public Map<String, Object> updateAmAndInsertPointDetail(int acoMoney, PointDetailVO pointDetailVO) {
		
		return pointService.updateAcoMoneyAndInsertPointDetail(acoMoney, pointDetailVO);

	}

	// 계좌 연결 은행 선택
	@GetMapping("/accountConnect")
	public String getConnectAccount(Model model) {
		model.addAttribute("getBankList", pointService.getBankAll());

		return "common/accountConnect";
	}

	// 계좌번호 입력
	@GetMapping("/insertAccountNo")
	public String getInserstAccountNo(@RequestParam String bankCode, @RequestParam String bankName, Model model) {
		model.addAttribute("bankCode", bankCode);
		model.addAttribute("bankName", bankName);
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);

		return "common/insertAccountNo";
	}

	//
	@PostMapping("/registAccount")
	@ResponseBody

	public Map<String, Object> registAccount(@RequestBody AccountVO accountVO, Model model) {

		return pointService.insertAccountInfo(accountVO);

	}
	
	//AcoMoney조회
	@GetMapping("/acoMoneyInquiry")
	public String getAcoMoneyChargeAndUseForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			username = memberService.getMemberInfo(username);
			
			model.addAttribute("latestAcoMoney", username.getAcoMoney());
			pointService.getAcoMoneyChargeAndUse(model, username.getMemberNo());
			
		}
		
		return "common/acoMoneyInquiry";
		
	}
	
	//AcoPoint조회
		@GetMapping("/acoPointInquiry")
		public String getAcoPointAcquireAndUseForm(Model model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
				UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
				MemberVO username = userDetails.getMemberVO();
				
				username = memberService.getMemberInfo(username);
				
				model.addAttribute("latestAcoPoint", username.getAcoPoint());
				pointService.getAcoPointAcquireAndUse(model, username.getMemberNo());
				
			}
			
			return "common/acoPointInquiry";
			
		}
	
	
	



}
