package com.yedamFinal.aco.freeboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.sideboard.SideVO;

@Mapper
public interface FreeBoardMapper {
	//게시글 전체조회
	public List<FreeBoardVO> getFreeBoardAll();

	//게시글 단건조회
	public FreeBoardVO getFreeBoard(@Param("fbno") int fbno);
}
