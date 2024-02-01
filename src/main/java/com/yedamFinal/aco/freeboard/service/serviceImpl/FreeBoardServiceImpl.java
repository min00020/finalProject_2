package com.yedamFinal.aco.freeboard.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.member.service.MemberService;


@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	@Autowired
	private FreeBoardMapper freeBoardMapper;

	@Override
	public List<FreeBoardVO> getFreeBoardAll() {

		return freeBoardMapper.getFreeBoard();
	}



	
}
