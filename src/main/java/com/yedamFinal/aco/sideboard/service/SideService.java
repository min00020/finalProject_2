package com.yedamFinal.aco.sideboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedamFinal.aco.sideboard.SideVO;
@Service
public interface SideService {
	public List<SideVO> getRecruitingList(String status);
	public SideVO getSideInfo(int bno);

}
