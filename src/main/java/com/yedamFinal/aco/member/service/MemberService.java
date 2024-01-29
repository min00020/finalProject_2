package com.yedamFinal.aco.member.service;

import java.util.Map;

import com.yedamFinal.aco.member.MemberVO;

public interface MemberService {
	public Map<String,Object>  checkDuplicateId(String id);
	public Map<String,Object>  checkDuplicateEmail(String email);
	public Map<String,Object> sendAuthNumberToPhone(String phoneNum);
}
