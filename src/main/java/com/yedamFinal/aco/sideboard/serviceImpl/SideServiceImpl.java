package com.yedamFinal.aco.sideboard.serviceImpl;

import java.util.List;

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
    public SideVO getSideInfo(String status, int bno) {
        return sideMapper.selectSideInfo(status, bno);
    }
	
	
}
