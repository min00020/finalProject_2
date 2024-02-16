package com.yedamFinal.aco.freeboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.freeboard.FreeBoardVO;

public interface FreeBoardService {
	
	public List<FreeBoardVO> getFreeBoardAll();
	
	public FreeBoardVO getFreeBoard(int fboardNo, Model model);
	
	//등록
	public Map<String, Object> insertFreeBoard(FreeBoardVO freeBoardVO, MultipartFile[] files);
	
	//수정
	public Map<String, Object> modifyFreeBoard(String title, String content, int fboardNo);
	
	//삭제
	public int deleteFreeBoard(int fboardNo);
	
	
}
