package com.yedamFinal.aco.common.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.member.MemberVO;

public interface ReplyService {
	public Map<String,Object> postReply(String boardType, String boardNo, String replyBody, String isEmoticon, String replyPno, MemberVO vo);
	public List<ReplyJoinVO> getReplyList(String boardType, int boardNo);
	public Map<String, Object> recommendReply(String replyNo);
}
