package com.yedamFinal.aco.qnaBoard.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.qnaBoard.QnABoardJoinVO;

public interface QnABoardService {
	public Map<String, Object> getMyQnaBoardList(int pageNo, MemberVO vo, String orderby);
	public Map<String, Object> getMyQnaBoardListFromSearch(int pageNo, String search, MemberVO vo, String orderby);
	public Map<String, Object> insertQnaBoard(int memberNo, String title, String content, MultipartFile[] files);
	public List<QnABoardJoinVO> getQnaBoardDetailInfo(int qnaBoardNo);
	public Map<String, Object> postQnAAnswer(int qnaBoardNo, String answer);
	public Map<String, Object> changeQnAState(int qnaBoardNo, String state);
}
