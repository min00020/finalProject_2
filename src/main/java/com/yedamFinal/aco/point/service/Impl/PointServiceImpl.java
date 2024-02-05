package com.yedamFinal.aco.point.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
import com.yedamFinal.aco.point.mapper.PointMapper;
import com.yedamFinal.aco.point.service.PointService;

@Service
public class PointServiceImpl implements PointService {
	@Autowired
	private PointMapper pointMapper;

	@Override
	public void getPointMainData(Model model, int memberNo) {
		model.addAttribute("getAccountList", pointMapper.getAccountNumber());
		return;
	}

	@Override
	public List<BankVO> getBankAll() {

		return pointMapper.getBank();
	}

	@Override
	public Map<String, Object> insertAccountInfo(AccountVO accountVO) {
		Map<String, Object> ret = new HashMap<String, Object>();

		int insertId = pointMapper.registAccountInfo(accountVO);
		if (insertId <= 0) {
			ret.put("result", "500");
		} else {
			ret.put("result", "200");
			ret.put("accountVO", accountVO);
		}
		return ret;

	}

}
