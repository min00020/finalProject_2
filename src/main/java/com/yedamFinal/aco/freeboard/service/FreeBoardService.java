package com.yedamFinal.aco.freeboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.freeboard.FreeBoardVO;

public interface FreeBoardService {
	
	public List<FreeBoardVO> getFreeBoardAll();
	
	public FreeBoardVO getFreeBoard(int fbno, Model model);
	
	//등록
	public Map<String, Object> insertFreeBoard(FreeBoardVO freeBoardVO, MultipartFile[] files);
	
	
	
	
}
