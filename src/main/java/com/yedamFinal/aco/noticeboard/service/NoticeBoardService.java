package com.yedamFinal.aco.noticeboard.service;

import java.util.Map;

import org.springframework.ui.Model;

import com.yedamFinal.aco.noticeboard.NoticeBoardVO;

public interface NoticeBoardService {
	public Map<String,Object> getAdNoticeList(int pageNo);
	public NoticeBoardVO getNoticeInfo(NoticeBoardVO noticeBoardVO, Model model);
	//검색
	public Map<String,Object> searchNoticeBoard(int pageNo, String search);
}
