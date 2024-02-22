package com.yedamFinal.aco.noticeboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO;

public interface NoticeBoardMapper { 
	public List<AdminMainVO> getAdNoticeList(int pageNo);
	public int selectAdNoticeCount();
	public NoticeBoardVO getNoticeInfo(NoticeBoardVO noticeBoardVO);
	
	//조회수
	public int plusViewCnt(int boardNo);
	//덧글수 +1 -1
	public int updateReplyCnt(@Param(value="value") int value, @Param(value="noticeBoardNo")  int boardNo);
	//검색
	public List<AdminMainVO> searchNoticeBoard(@Param("pageNo") int pageNo, @Param("search") String search);
	public int searchNoticeBoardCnt(String search);
}
