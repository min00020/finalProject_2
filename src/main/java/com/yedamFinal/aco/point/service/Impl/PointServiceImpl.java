package com.yedamFinal.aco.point.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.mapper.PointMapper;
import com.yedamFinal.aco.point.service.PointService;

@Service
public class PointServiceImpl implements PointService{
	@Autowired
	private PointMapper pointMapper;

	@Override
	public List<AccountVO> getAccountAll() {
	
		return pointMapper.getAccountNumber();
	}
}
