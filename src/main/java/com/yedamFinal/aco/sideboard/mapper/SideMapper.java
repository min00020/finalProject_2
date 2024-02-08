package com.yedamFinal.aco.sideboard.mapper;

import java.util.List;

import com.yedamFinal.aco.sideboard.SideVO;

public interface SideMapper {
	public List<SideVO> selectRecruitingList(String status);
	public List<SideVO> selectCollaboratingList(String status);
}
