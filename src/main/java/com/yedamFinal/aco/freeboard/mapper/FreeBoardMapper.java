package com.yedamFinal.aco.freeboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedamFinal.aco.freeboard.FreeBoardVO;

@Mapper
public interface FreeBoardMapper {

	public List<FreeBoardVO> getFreeBoard();

}
