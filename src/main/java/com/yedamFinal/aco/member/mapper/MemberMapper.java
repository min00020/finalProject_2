package com.yedamFinal.aco.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yedamFinal.aco.member.MemberVO;

public interface MemberMapper { 
	public MemberVO selectCheckDuplicateId(String id);
	public MemberVO selectCheckDuplicateEmail(String email);
}
