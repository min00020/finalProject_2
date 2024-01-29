package com.yedamFinal.aco.web.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	public List<BoardVO> getSideBoard(BoardVO boardVO);

}
