package com.yedamFinal.aco.point.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;

public interface PointService {
	
	public List<AccountVO> getAccountAll();
	public List<BankVO> getBankAll();
	public Map<String, Object> insertAccountInfo(AccountVO accountVO);
}
