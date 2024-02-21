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
	public int updateSide(SideVO sideVO);
	public int deleteSide(@Param(value="bno") int bno);
	
	public int updateStatus(SideVO sideVO);
	
	public int updatereviewCnt(@Param(value="bno") int bno);
	public List<SideVO> selectParticipateList(int memberNo);
	
	public List<SideVO> selectListAllByMember(@Param(value="pageNo")int pageNo, @Param(value="memNo") int memberNo);
	public int selectListAllCntByMember(int memberNo);
	
	public int updateReplyCnt(@Param(value="value") int value, @Param(value="bno")  int bno);
}
