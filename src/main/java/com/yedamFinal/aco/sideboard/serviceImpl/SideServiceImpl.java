package com.yedamFinal.aco.sideboard.serviceImpl;

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
import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.sideboard.SideVO;
import com.yedamFinal.aco.sideboard.mapper.SideMapper;
import com.yedamFinal.aco.sideboard.service.SideService;

@Service
public class SideServiceImpl implements SideService{
	
	@Autowired
	SideMapper sideMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	ReplyMapper replyMapper;
	
	@Override
	public Map<String, Object> getRecruitingList(int pageNo, String status) {
		Map<String, Object> map = new HashMap<>();
		var RecruitingList = sideMapper.selectRecruitingList(pageNo, status);
		PaginationDTO dto = null;
		if(RecruitingList.size() > 0) {
			dto = new PaginationDTO(sideMapper.selectProjectListCnt(status), pageNo, 10);
		}
		map.put("sideList", RecruitingList);
		map.put("pageDTO", dto);
		return map;
	}
	
	@Override
    public SideVO getSideInfo(int bno) {
        return sideMapper.selectSideInfo(bno);
    }
	@Override
	public void getSideInfoAndReplyList(int bno, Model model) {
		var list = replyMapper.selectReply("N006", bno);
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
		sideMapper.updatereviewCnt(bno);
		model.addAttribute("sideInfo", sideMapper.selectSideInfo(bno));
	}
	//상태변경
	@Override
	public Map<String, Object> updateBoardStatus(SideVO vo){
		
		Map<String, Object> map = new HashMap<>();
		
		int result = sideMapper.updateStatus(vo);
		if (result <= 0) {
			map.put("result", "500");
		} else {
			map.put("result", "200");
			map.put("vo", vo);
		}
		return map;
	}
	//새글 작성
	@Override
	public Map<String, Object> insertProject (SideVO sideVO){

			Map<String, Object> map = new HashMap<>();
			int result = sideMapper.insertSideProject(sideVO);
			int pk = sideVO.getPk();
			if( result <= 0) {
				map.put("result", "500");
			} else {
				map.put("result", "200");
				map.put("vo", sideVO);
			}
			return map;
	}
	@Override
	public Map<String, Object> modifyProject(SideVO sideVO, int bno){
		Map<String, Object> map = new HashMap<>();
		sideVO.setBno(bno);
		int result = sideMapper.updateSide(sideVO);
		int pk = sideVO.getPk();
		if( result <= 0) {
			map.put("result", "500");
		} else {
			map.put("result", "200");
			map.put("vo", sideVO);
		}
		return map;
	}
	
	@Override
	public int deleteProject(int bno){
		return sideMapper.deleteSide(bno);
	}

	@Override 
	public List<SideVO> getParticipateList(int memberNo) {
		return sideMapper.selectParticipateList(memberNo);
	}
	 
}
