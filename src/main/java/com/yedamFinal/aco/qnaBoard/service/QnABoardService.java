package com.yedamFinal.aco.qnaBoard.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.qnaBoard.QnABoardVO;

public interface QnABoardService {
	public Map<String, Object> getMyQnaBoardList(int pageNo, MemberVO vo, String orderby);
	public Map<String, Object> getMyQnaBoardListFromSearch(int pageNo, String search, MemberVO vo, String orderby);
}
