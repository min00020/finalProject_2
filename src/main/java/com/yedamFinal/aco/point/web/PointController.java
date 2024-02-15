package com.yedamFinal.aco.point.web;

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
import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.PointDetailVO;
import com.yedamFinal.aco.point.service.PointService;
/**
* 애코머니 충전, 애코머니 관리, 애코포인트 관리, 계좌 등록, 계좌이체 
* @author 박경석
* @since 2024.02.15
* @version 1.0
* @see
* 
* <pre>
* << 개정이력(Modification Information) >>
*  
*  *   수정일     수정자          수정내용
*  -------    --------    ---------------------------
*  2024.02.01   박경석          최초 생성
*  </pre>
* 
* 
**/
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

	/**
	 * 포인트 충전 메인화면
	 * @param pg
	 * @param model
	 * @return common/pointCharging
	 */
	@GetMapping("/point")
	public String getPointMainForm(@RequestParam(value = "pg", required = false, defaultValue = "1") int pg, Model model) {
		// MemberVO 꺼내오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			
			
			pointService.getPointMainData(model,  username.getMemberNo(),pg);
			
		}
		//model.addAttribute("getAccountList", pointService.getAccountAll());
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);
		
		return "common/pointCharging";
	}

	/**
	 * 애코머니 업데이트 => 포인트 내역 생성(트랜잭션)
	 * @param acoMoney
	 * @param pointDetailVO
	 * @return common/accountConnect
	 */
	@PostMapping("/updateAmAndInsertPointDetail")
	@ResponseBody
	public Map<String, Object> updateAmAndInsertPointDetail(int acoMoney, PointDetailVO pointDetailVO) {
		
		return pointService.updateAcoMoneyAndInsertPointDetail(acoMoney, pointDetailVO);

	}

	
	/**
	 * 계좌 연결 은행 선택
	 * @param model
	 * @return common/accountConnect
	 */
	@GetMapping("/accountConnect")
	public String getConnectAccount(Model model) {
		model.addAttribute("getBankList", pointService.getBankAll());

		return "common/accountConnect";
	}


	/**
	 * 은행선택 후 계좌번호 입력 화면
	 * @param bankCode
	 * @param bankName
	 * @param model
	 * @return common/insertAccountNo
	 */
	@GetMapping("/insertAccountNo")
	public String getInserstAccountNo(@RequestParam String bankCode, @RequestParam String bankName, Model model) {
		model.addAttribute("bankCode", bankCode);
		model.addAttribute("bankName", bankName);
		model.addAttribute("Iscd", nhIscd);
		model.addAttribute("nhAccessToken", nhAccessToken);

		return "common/insertAccountNo";
	}

	/**
	 * 계좌 등록
	 * @param accountVO
	 * @param model
	 * @return pointService.insertAccountInfo(accountVO);
	 */
	@PostMapping("/registAccount")
	@ResponseBody
	public Map<String, Object> registAccount(@RequestBody AccountVO accountVO, Model model) {

		return pointService.insertAccountInfo(accountVO);

	}
	
	/**
	 * 애코머니 충전 및 사용내역 조회
	 * @param cp (충전페이지번호)
	 * @param up (사용페이지번호)
	 * @param model
	 * @return common/acoMoneyInquiry
	 */
	@GetMapping("/acoMoneyInquiry")
	public String getAcoMoneyChargeAndUseForm(@RequestParam int cp, @RequestParam int up, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			username = memberService.getMemberInfo(username);
			
			model.addAttribute("latestAcoMoney", username.getAcoMoney());
			pointService.getAcoMoneyChargeAndUse(model, username.getMemberNo(),cp,up);
			
		}
		
		return "common/acoMoneyInquiry";
		
	}
	
	/**
	 * 애코포인트 획득, 사용내역 조회
	 * @param ap
	 * @param up
	 * @param model
	 * @return common/acoPointInquiry
	 */
	@GetMapping("/acoPointInquiry")
	public String getAcoPointAcquireAndUseForm(@RequestParam int ap, @RequestParam int up, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();	
			MemberVO username = userDetails.getMemberVO();
			
			username = memberService.getMemberInfo(username);
			
			model.addAttribute("latestAcoPoint", username.getAcoPoint());
			pointService.getAcoPointAcquireAndUse(model, username.getMemberNo(),ap,up);
			
		}
		
		return "common/acoPointInquiry";
		
	}
	
}
