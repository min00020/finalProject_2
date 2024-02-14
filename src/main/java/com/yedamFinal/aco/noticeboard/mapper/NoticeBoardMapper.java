package com.yedamFinal.aco.noticeboard.mapper;

import java.util.List;

import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO;

public interface NoticeBoardMapper { 
	public List<AdminMainVO> getAdNoticeList(int pageNo);
	public int selectAdNoticeCount();
	public NoticeBoardVO getNoticeInfo(NoticeBoardVO noticeBoardVO);
}
