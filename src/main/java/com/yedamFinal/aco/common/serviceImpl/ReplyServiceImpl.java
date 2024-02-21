package com.yedamFinal.aco.common.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.ReplyVO;
import com.yedamFinal.aco.common.ReportVO;
import com.yedamFinal.aco.common.mapper.ReplyMapper;
import com.yedamFinal.aco.common.service.ReplyService;
import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.sideboard.mapper.SideMapper;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private SideMapper sideMapper;
	
	@Autowired
	private FreeBoardMapper freeMapper;
	
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
		
		//댓글 수 +1
		if(boardType.equals("N001")) {
			questionMapper.updateReplyCnt(0, Integer.valueOf(boardNo));
		}
		else if(boardType.equals("N006")){
			sideMapper.updateReplyCnt(0, Integer.valueOf(boardNo));
		}
		else if(boardType.equals("N004")) {
			freeMapper.updateReplyCnt(0, Integer.valueOf(boardNo));
		}
		
		return ret;
	}

	@Override
	public List<ReplyJoinVO> getReplyList(String boardType, int boardNo) {
		// TODO Auto-generated method stub
		return replyMapper.selectReply(boardType, boardNo);
	}

	@Override
	public Map<String, Object> deleteReply(String replyNo) {
		// TODO Auto-generated method stub
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("result", "200");
		
		if(replyMapper.updateDeleteDateReply(Integer.valueOf(replyNo)) <= 0) {
			ret.put("result", "500");
		}
		
		ReplyVO replyVO = replyMapper.selectReplyInfo(Integer.valueOf(replyNo));

		//댓글 수 -1
		if(replyVO.getBoardType().equals("N001")) {
			questionMapper.updateReplyCnt(2, Integer.valueOf(replyVO.getBoardNo()));
		}
		else if(replyVO.getBoardType().equals("N006")){
			 sideMapper.updateReplyCnt(2, Integer.valueOf(replyVO.getBoardNo()));
		}
		else if(replyVO.getBoardType().equals("N004")){
			freeMapper.updateReplyCnt(2, Integer.valueOf(replyVO.getBoardNo()));
		
		}
		return ret;
	}

	@Override
	public Map<String, Object> modifyReply(int replyNo, String replyBody, String isEmoticon) {
		// TODO Auto-generated method stub
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("result", "200");
		
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReplyNo(replyNo);
		// 이모티콘 사용 시
		if(isEmoticon.equals("1")) {
			replyVO.setEmoticon(replyBody);
			replyVO.setContents(null);
		}
		else {
			replyVO.setContents(replyBody);
			replyVO.setEmoticon(null);
		}
		
		if(replyMapper.updateCommentReply(replyVO) <= 0) {
			ret.put("result", "500");
		}
		
		return ret;
	}

	@Override
	public int insertReport(ReportVO reportVO, int memberNo) {
		reportVO.setReporter(memberNo);
		return replyMapper.insertReport(reportVO);
	}

}
