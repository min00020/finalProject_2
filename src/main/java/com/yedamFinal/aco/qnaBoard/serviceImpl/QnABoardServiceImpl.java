package com.yedamFinal.aco.qnaBoard.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.qnaBoard.QnABoardJoinVO;
import com.yedamFinal.aco.qnaBoard.QnABoardVO;
import com.yedamFinal.aco.qnaBoard.mapper.QnABoardMapper;
import com.yedamFinal.aco.qnaBoard.service.QnABoardService;

@Service
public class QnABoardServiceImpl implements QnABoardService {
	
	@Autowired
	private FileServiceImpl fileService;
	
	private Map<String,String> orderbyByReqOb = new HashMap<String,String>();
	
	public QnABoardServiceImpl() {
		// 정렬기준
		orderbyByReqOb.put("0", "Latest");
		orderbyByReqOb.put("1", "View");
	}
	
	@Autowired
	private QnABoardMapper qnaMapper;
	
	@Override
	public Map<String, Object> getMyQnaBoardList(int pageNo, MemberVO vo, String ob) {
		// 적용할 orderby value
		String orderby = orderbyByReqOb.get(ob);
		if(orderby == null) {
			orderby = "Latest";
		}
		
		var qnaList = qnaMapper.selectMyQnaBoardList(pageNo,vo.getMemberNo(),orderby);
		PaginationDTO dto = null;
		if(qnaList.size() > 0) {
			dto = new PaginationDTO(qnaMapper.selectMyQnaBoardCount(vo.getMemberNo()), pageNo, 5);
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("qnaList", qnaList);
		ret.put("pageDTO", dto);
		ret.put("orderby", ob); 
		// 타임리프에 값을 전달할 때 Map에 해당하는 key의 value가 null이면 orderby자체가 존재하지않음.
		ret.put("isExistOb", ob != null);
		
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public Map<String, Object> getMyQnaBoardListFromSearch(int pageNo, String search, MemberVO vo, String ob) {
		String orderby = orderbyByReqOb.get(ob);
		if(orderby == null) {
			orderby = "Latest";
		}
		
		var qnaList = qnaMapper.selectMyQnaBoardListFromSearch(pageNo,search,vo.getMemberNo(),orderby);
		PaginationDTO dto = null;
		if(qnaList.size() > 0) {
			dto = new PaginationDTO(qnaMapper.selectMyQnaBoardCountFromSearch(vo.getMemberNo(), search), pageNo, 5);
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("qnaList", qnaList);
		ret.put("pageDTO", dto);
		ret.put("orderby", ob); 
		// 타임리프에 값을 전달할 때 Map에 해당하는 key의 value가 null이면 orderby자체가 존재하지않음.
		ret.put("isExistOb", ob != null);
		
		return ret;
	}

	@Override
	public Map<String, Object> insertQnaBoard(int memberNo, String title, String content, MultipartFile[] files) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String,Object>();
		ret.put("result", "200");
		
		QnABoardVO vo = new QnABoardVO();
		vo.setMemberNo(memberNo);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setAnswerState("P001");
		if(qnaMapper.insertQnaBoard(vo) <= 0) {
			ret.put("result", "500");
			return ret;
		}
		int boardNo = vo.getPk();
		if(files != null && files.length > 0) {
			if(!fileService.uploadAttachFiles(files, memberNo, new String("N005"), boardNo)) {
				ret.put("result", "500");
				return ret;
			}
		}
		
		return ret;
	}
	
	@Override
	public List<QnABoardJoinVO> getQnaBoardDetailInfo(int qnaBoardNo) {
		qnaMapper.updateQnABoardViewCnt(qnaBoardNo);
		return qnaMapper.selectQnaBoardDetail(qnaBoardNo);
	}

	@Override
	public Map<String, Object> postQnAAnswer(int qnaBoardNo, String answer) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = qnaMapper.updateQnAAnswer(qnaBoardNo, answer);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}

	@Override
	public Map<String, Object> changeQnAState(int qnaBoardNo, String state) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = qnaMapper.updateQnAState(qnaBoardNo, state);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}
}
