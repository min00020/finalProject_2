package com.yedamFinal.aco.sideboard.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedamFinal.aco.sideboard.SideVO;
@Service
public interface SideService {
	public Map<String, Object> getRecruitingList(int pageNo, String status);
	
	public SideVO getSideInfo(int bno);
	public Map<String, Object> insertProject(SideVO vo);
	public Map<String, Object> modifyProject(SideVO vo, int bno);
	public int deleteProject(int bno);
	
	public Map<String, Object> updateBoardStatus(int bno, String status, SideVO sideVO);
	
	public int updateviewCnt(int viewNo);
	
}
