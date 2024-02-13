package com.yedamFinal.aco.common.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.ReplyVO;
import com.yedamFinal.aco.common.mapper.ReplyMapper;
import com.yedamFinal.aco.common.service.ReplyService;
import com.yedamFinal.aco.member.MemberVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	
	@Override
	public Map<String, Object> postReply(String boardType, String boardNo, String replyBody, String isEmoticon, String replyPno, MemberVO memVo) {
		// TODO Auto-generated method stub
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("result", "200");
		
		ReplyVO vo = new ReplyVO();
		
		vo.setBoardNo(Integer.valueOf(boardNo));
		vo.setBoardType(boardType);
		vo.setMemberNo(memVo.getMemberNo());
		vo.setNickName(memVo.getNickname());
		// 이모티콘 사용 시
		if(isEmoticon.equals("1")) {
			vo.setEmoticon(replyBody);
		}
		else {
			vo.setContents(replyBody);
		}
		vo.setRecommendCnt(0);
		vo.setProfileImage(memVo.getProfileImage());
		if(replyPno != null && !replyPno.equals("")) {
			vo.setReplyPno(Integer.valueOf(replyPno));
		}
		else {
			vo.setReplyPno(null);
		}
		
		if(replyMapper.insertReply(vo) <= 0) {
			ret.put("result", "500");
		}
		
		return ret;
	}

	@Override
	public List<ReplyJoinVO> getReplyList(String boardType, int boardNo) {
		// TODO Auto-generated method stub
		return replyMapper.selectReply(boardType, boardNo);
	}

	// 같은 회원이 중복 추천하는거 막아야하는데.. 시간상 넘김
	@Override
	public Map<String, Object> recommendReply(String replyNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
