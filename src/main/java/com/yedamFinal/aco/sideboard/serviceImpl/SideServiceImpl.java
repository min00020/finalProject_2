package com.yedamFinal.aco.sideboard.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public List<SideVO> getRecruitingList(String status) {
		return sideMapper.selectRecruitingList(status);
	}
	
	@Override
    public SideVO getSideInfo(int bno) {
        return sideMapper.selectSideInfo(bno);
    }
	//상태변경
	@Override
	public Map<String, Object> updateBoardStatus(int bno, String status, SideVO vo){
		Map<String, Object> map = new HashMap<>();
		vo.setBno(bno);
		vo.setStatus(status);
		int result = sideMapper.updateStatus(bno, status, vo);
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
	
}
