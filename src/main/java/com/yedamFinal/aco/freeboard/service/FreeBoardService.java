package com.yedamFinal.aco.freeboard.service;

import java.util.List;

import com.yedamFinal.aco.freeboard.FreeBoardVO;

public interface FreeBoardService {
	
	public List<FreeBoardVO> getFreeBoardAll();
	
	public FreeBoardVO getFreeBoard(int fbno);
	
	
	
	
}
