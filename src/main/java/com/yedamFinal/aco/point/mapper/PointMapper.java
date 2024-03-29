package com.yedamFinal.aco.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
import com.yedamFinal.aco.point.PointDetailVO;
@Mapper
public interface PointMapper {
	
	public List<AccountVO> getAccountNumber(int memberNo, int pageNo);
	public int getAccountNumberCount(int memberNo);
	
	public List<BankVO> getBank();
	public int registAccountInfo(AccountVO accountVO);
	public int getAcoMoney(int memberNo);
	public int getAcoPoint(int memberNo);
	public int updateAcoMoney(@Param(value="acoMoney") int acoMoney, @Param(value="memberNo") int memberNo);
	public int insertAcoMoneyHistory(PointDetailVO pointDetailVO);
	
	public List<PointDetailVO> acoMoneyChargeInquiry(int memberNo, int cp);
	public int acoMoneyChargeInquiryCnt(int memberNo);
	
	public List<PointDetailVO> acoMoneyUseInquiry(int memberNo, int up);
	public int acoMoneyUseInquiryCnt(int memberNo);
	
	public List<PointDetailVO> acoPointAcquireInquiry(int memberNo, int ap);
	public int acoPointAcquireInquiryCnt(int memberNo);
	
	public List<PointDetailVO> acoPointUseInquiry(int memberNo, int up);
	public int acoPointUseInquiryCnt(int memberNo);
	
	public String accInquiry(String accountNo, int memberNo);
}
