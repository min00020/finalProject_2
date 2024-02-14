package com.yedamFinal.aco.point.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.yedamFinal.aco.common.PaginationDTO;
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
	public void getPointMainData(Model model, int memberNo,int pageNo) {
		var accountList = pointMapper.getAccountNumber(memberNo,pageNo);
		PaginationDTO dto = null;
		if(accountList.size() > 0) {
			dto = new PaginationDTO(pointMapper.getAccountNumberCount(memberNo), pageNo, 5);
		}
		
		model.addAttribute("pageDTO", dto);
	    model.addAttribute("getAccountList", accountList);

//		model.addAttribute("getAccountList", pointMapper.getAccountNumber(memberNo,pageNo));
		model.addAttribute("getAcoMoney", pointMapper.getAcoMoney(memberNo));
		model.addAttribute("getAcoPoint", pointMapper.getAcoPoint(memberNo));
		return;
	}

	@Override
	public List<BankVO> getBankAll() {

		return pointMapper.getBank();
	}

	@Override
	public Map<String, Object> insertAccountInfo(AccountVO accountVO) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String result = pointMapper.accInquiry(accountVO.getAccountNo(),accountVO.getMemberNo());
		if(result != null && !result.equals("")) {
			ret.put("result", "409");
			return ret;
		}

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
	public Map<String, Object> updateAcoMoneyAndInsertPointDetail(int acoMoney,
			PointDetailVO pointDetailVO) {
		Map<String, Object> ret = new HashMap<>();
		int updateId = pointMapper.updateAcoMoney(acoMoney, pointDetailVO.getMemberNo());
		if (updateId <= 0) {
			ret.put("result", "500");
		} else {
			ret.put("result", "200");
			ret.put("acoMoney", acoMoney);
		}

		int insertId = pointMapper.insertAcoMoneyHistory(pointDetailVO);
		if (insertId <= 0) {
			ret.put("result", "500");
		} else {
			ret.put("result", "200");
			ret.put("pointDetailVO", pointDetailVO);
		}
		return ret;
	}

	@Override
	public void getAcoMoneyChargeAndUse(Model model, int memberNo) {
		model.addAttribute("acoMoneyChargeInquiry", pointMapper.acoMoneyChargeInquiry(memberNo));
		model.addAttribute("acoMoneyUseInquiry", pointMapper.acoMoneyUseInquiry(memberNo));
		return;
		
	}

	@Override
	public void getAcoPointAcquireAndUse(Model model, int memberNo) {
		model.addAttribute("acoPointAcquireInquiry", pointMapper.acoPointAcquireInquiry(memberNo));
		model.addAttribute("acoPointUseInquiry", pointMapper.acoPointUseInquiry(memberNo));
		return;		
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
