package com.yedamFinal.aco.qnaBoard.service;

import java.util.Map;

import com.yedamFinal.aco.member.MemberVO;

public interface QnABoardService {
	public Map<String, Object> getMyQnaBoardList(int pageNo, MemberVO vo, String orderby);
	public Map<String, Object> getMyQnaBoardListFromSearch(int pageNo, String search, MemberVO vo, String orderby);
}
