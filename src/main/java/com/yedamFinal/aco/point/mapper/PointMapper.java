package com.yedamFinal.aco.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedamFinal.aco.point.AccountVO;
@Mapper
public interface PointMapper {
	public List<AccountVO> getAccountNumber();
}
