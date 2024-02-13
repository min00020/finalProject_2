package com.yedamFinal.aco.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.ReplyVO;

public interface ReplyMapper {
	public int insertReply(ReplyVO vo);
	public List<ReplyJoinVO> selectReply(@Param(value="type") String boardType, @Param(value="bno") int boardNo);
}