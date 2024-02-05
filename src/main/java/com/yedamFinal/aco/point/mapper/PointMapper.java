package com.yedamFinal.aco.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.BankVO;
@Mapper
public interface PointMapper {
	public List<AccountVO> getAccountNumber();
	public List<BankVO> getBank();
	public int registAccountInfo(AccountVO accountVO);
	public int UpdateAcoMoney();
	public int getAcoMoney();
}
