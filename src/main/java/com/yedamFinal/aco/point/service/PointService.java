package com.yedamFinal.aco.point.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
import com.yedamFinal.aco.point.PointDetailVO;

public interface PointService {

	public void getPointMainData( Model model, int memberNo,int pageNo);

	public List<BankVO> getBankAll();

	public Map<String, Object> insertAccountInfo(AccountVO accountVO);

//	//AcoMoneyUpdate
//	public Map<String, Object> updateAcoMoney(Model model, int acoMoney, int memberNo);
//	//InsertPointDetail
//	public Map<String, Object> InsertPointDetail(PointDetailVO pointDetailVO);
//	
	public Map<String, Object> updateAcoMoneyAndInsertPointDetail(int acoMoney, PointDetailVO pointDetailVO);

	// 애코머니 조회
	public void getAcoMoneyChargeAndUse(Model model, int memberNo);

	// 애코포인트 조회
	public void getAcoPointAcquireAndUse(Model model, int memberNo);
	
//	// 계좌중복조회
//	public void getAccDuplicate(Model model, String accountNo, int memberNo);

}
