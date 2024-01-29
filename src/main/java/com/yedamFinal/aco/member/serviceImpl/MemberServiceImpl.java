package com.yedamFinal.aco.member.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.member.service.MemberService;


@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
