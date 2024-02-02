package com.yedamFinal.aco.point.service;

import java.util.List;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;

public interface PointService {
	
	public List<AccountVO> getAccountAll();
	public List<BankVO> getBankAll();
	public int insertAccountInfo(AccountVO accountVO);
}
