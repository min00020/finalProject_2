package com.yedamFinal.aco.sideboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedamFinal.aco.sideboard.SideVO;
@Service
public interface SideService {
	public List<SideVO> getRecruitingList(String status);
	public SideVO getSideInfo(int bno);
	public Map<String, Object> updateBoardStatus(int bno, String status, SideVO sideVO);
	public Map<String, Object> insertProject(SideVO vo);
}
