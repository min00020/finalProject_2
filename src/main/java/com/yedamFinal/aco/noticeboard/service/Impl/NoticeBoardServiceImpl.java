package com.yedamFinal.aco.noticeboard.service.Impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.mapper.ReplyMapper;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO;
import com.yedamFinal.aco.noticeboard.mapper.NoticeBoardMapper;
import com.yedamFinal.aco.noticeboard.service.NoticeBoardService;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {
	@Autowired
	NoticeBoardMapper noticeBoardMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
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
	public NoticeBoardVO getNoticeInfo(NoticeBoardVO noticeBoardVO, Model model) {
		noticeBoardMapper.plusViewCnt(noticeBoardVO.getNoticeBoardNo());
		int nBoardNo = noticeBoardVO.getNoticeBoardNo();
		List<ReplyJoinVO> list = replyMapper.selectReply("N003", nBoardNo);
		Map<Integer, List<ReplyJoinVO>> groupByData = list.stream().collect(Collectors.groupingBy(ReplyJoinVO::getParentReplyNo));
		groupByData = groupByData.entrySet().stream()
		        .sorted(Map.Entry.comparingByKey())
		        .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a, b) -> { throw new AssertionError(); },
		                LinkedHashMap::new
		        ));

		model.addAttribute("replyList", groupByData);
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
