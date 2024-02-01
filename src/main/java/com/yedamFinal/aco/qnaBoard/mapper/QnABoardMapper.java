package com.yedamFinal.aco.qnaBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.qnaBoard.QnABoardVO;

public interface QnABoardMapper { 
	public List<QnABoardVO> selectMyQnaBoardList(@Param(value="pageNo") int pageNo, @Param(value="memberNo") int memberNo);
	public int selectMyQnaBoardCount(int memberNo);
}
