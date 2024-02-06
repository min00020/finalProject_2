package com.yedamFinal.aco.qnaBoard.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.qnaBoard.mapper.QnABoardMapper;
import com.yedamFinal.aco.qnaBoard.service.QnABoardService;

@Service
public class QnABoardServiceImpl implements QnABoardService {
	
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
}
