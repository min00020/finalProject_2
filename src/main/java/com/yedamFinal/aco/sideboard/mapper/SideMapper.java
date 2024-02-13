package com.yedamFinal.aco.sideboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.sideboard.SideVO;
@Mapper
public interface SideMapper {
	public List<SideVO> selectRecruitingList(@Param(value="pageNo")int pageNo, String status);
	public int selectProjectListCnt(@Param(value="status") String status);
	
	public SideVO selectSideInfo(@Param("bno") int bno);
	public int insertSideProject(SideVO sideVO);
	public int modifyProject(@Param(value="bno") int bno, SideVO sideVO);
	public int deleteSide(@Param(value="bno") int bno);
	
	public int updateStatus(@Param(value="bno") int bno, @Param(value="status") String status, SideVO sideVO);
	
	public int updatereviewCnt(@Param(value="viewNo") int viewNo);
}
