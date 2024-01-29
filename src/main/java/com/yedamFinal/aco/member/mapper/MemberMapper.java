package com.yedamFinal.aco.member.mapper;

import java.util.HashMap;
import java.util.List;

import com.yedamFinal.aco.member.MemberVO;

public interface MemberMapper {
	// 회원 정보 단건조회
	public List<MemberVO> selectMemberInfo(MemberVO memberVO);
}
