package com.yedamFinal.aco.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
@Mapper
public interface PointMapper {
	public List<AccountVO> getAccountNumber();
	public List<BankVO> getBank();
	public int registAccountInfo(AccountVO accountVO);
	public int updateAcoMoney(@Param(value="acoMoney") int acoMoney, @Param(value="memberNo") int memberNo);
	public int getAcoMoney(int memberNo);
}
