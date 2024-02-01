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

	@Autowired
	private QnABoardMapper qnaMapper;
	
	@Override
	public Map<String, Object> getMyQnaBoardList(int pageNo, MemberVO vo) {
		var qnaList = qnaMapper.selectMyQnaBoardList(pageNo,vo.getMemberNo());
		PaginationDTO dto = null;
		if(qnaList.size() > 0) {
			dto = new PaginationDTO(qnaMapper.selectMyQnaBoardCount(vo.getMemberNo()), pageNo, 5);
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("qnaList", qnaList);
		ret.put("pageDTO", dto);
		
		// TODO Auto-generated method stub
		return ret;
	}
	
}
