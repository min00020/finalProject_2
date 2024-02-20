package com.yedamFinal.aco.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.ReplyVO;
import com.yedamFinal.aco.common.ReportVO;

public interface ReplyMapper {
	public int insertReply(ReplyVO vo);
	public List<ReplyJoinVO> selectReply(@Param(value="type") String boardType, @Param(value="bno") int boardNo);
	public int updateDeleteDateReply(int replyNo);
	public int updateCommentReply(ReplyVO vo);
	
	//harang
	public int insertReport(ReportVO reportVO);
}
