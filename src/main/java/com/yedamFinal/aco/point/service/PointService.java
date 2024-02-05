package com.yedamFinal.aco.point.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;

public interface PointService {
	
	public void getPointMainData(Model model, int memberNo);
	public List<BankVO> getBankAll();
	public Map<String, Object> insertAccountInfo(AccountVO accountVO);
	
	
}
