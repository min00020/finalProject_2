package com.yedamFinal.aco.noticeboard.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;
import com.yedamFinal.aco.common.serviceImpl.ReplyServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO;
import com.yedamFinal.aco.noticeboard.mapper.NoticeBoardMapper;
import com.yedamFinal.aco.noticeboard.service.NoticeBoardService;
import com.yedamFinal.aco.qnaBoard.QnABoardVO;
import com.yedamFinal.aco.qnaBoard.mapper.QnABoardMapper;
import com.yedamFinal.aco.qnaBoard.service.QnABoardService;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {
	@Autowired
	NoticeBoardMapper noticeBoardMapper;
	
	@Override
	public  Map<String,Object> getAdNoticeList(int pageNo) {
		Map<String,Object> mp = new HashMap<String, Object>();
		var AdNoticeList = noticeBoardMapper.getAdNoticeList(pageNo);
		PaginationDTO dto = null;
		if(AdNoticeList.size() > 0) {
			dto = new PaginationDTO(noticeBoardMapper.selectAdNoticeCount(), pageNo , 10);
		}
		
		mp.put("noticeList", AdNoticeList);
		mp.put("pageDTO", dto);
		return mp;
	}

	@Override
	public NoticeBoardVO getNoticeInfo(NoticeBoardVO noticeBoardVO) {
		noticeBoardMapper.plusViewCnt(noticeBoardVO.getNoticeBoardNo());
		return noticeBoardMapper.getNoticeInfo(noticeBoardVO);
	}

	@Override
	public Map<String,Object> searchNoticeBoard(int pageNo, String search) {
		Map<String,Object> mp = new HashMap<String, Object>();
		var searchNoticeBoardList = noticeBoardMapper.searchNoticeBoard(pageNo, search);
		PaginationDTO dto = null;
		if(searchNoticeBoardList.size() > 0) {
			dto = new PaginationDTO(noticeBoardMapper.searchNoticeBoardCnt(search), pageNo, 10);
		}
		mp.put("pageDTO", dto);
		mp.put("searchNoticeBoardList", searchNoticeBoardList);
		return mp;
	}
}
