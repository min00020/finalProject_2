package com.yedamFinal.aco.point.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
import com.yedamFinal.aco.point.PointDetailVO;
import com.yedamFinal.aco.point.mapper.PointMapper;
import com.yedamFinal.aco.point.service.PointService;

@Service
public class PointServiceImpl implements PointService {
	@Autowired
	private PointMapper pointMapper;
	

	@Override
	public void getPointMainData(Model model, int memberNo) {		
		
		
		model.addAttribute("getAccountList", pointMapper.getAccountNumber());
		model.addAttribute("getAcoMoney", pointMapper.getAcoMoney(memberNo));
		
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

	@Override
	@Transactional
	public Map<String, Object> updateAcoMoneyAndInsertPointDetail(Model model, int acoMoney, int memberNo,
			PointDetailVO pointDetailVO) {
		Map<String,Object> ret = new HashMap<>();
		int updateId = pointMapper.updateAcoMoney(acoMoney, memberNo);
		if(updateId <= 0) {
			ret.put("result","500");
		}else {
			ret.put("result", "200");
			ret.put("acoMoney",acoMoney);
		}
		
		Map<String,Object> ret2 = new HashMap<>();
		int insertId = pointMapper.insertAcoMoneyHistory(pointDetailVO);
		if (insertId <= 0) {
			ret2.put("result", "500");
		} else {
			ret2.put("result", "200");
			ret2.put("pointDetailVO", pointDetailVO);
	}	
		model.addAttribute("updateAcoMoney", pointMapper.updateAcoMoney(acoMoney, memberNo));
		return ret;
		}						
	}


//	@Override
//	public Map<String, Object> updateAcoMoney(Model model, int acoMoney, int memberNo ) {
//		Map<String,Object> ret = new HashMap<>();
//	
//		int updateId = pointMapper.updateAcoMoney(acoMoney, memberNo);
//		if(updateId <= 0) {
//			ret.put("result","500");
//		}else {
//			ret.put("result", "200");
//			ret.put("acoMoney",acoMoney);
//		}
//		
//		model.addAttribute("updateAcoMoney", pointMapper.updateAcoMoney(acoMoney, memberNo));
//		
//		return ret;
//	}
//
//	@Override
//	public Map<String, Object> InsertPointDetail(PointDetailVO pointDetailVO) {
//		return null;
//	}
	
	

	
	


