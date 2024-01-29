package com.yedamFinal.aco.member.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.common.TagVO;

public interface MemberService {
	public Map<String,Object>  checkDuplicateId(String id);
	public Map<String,Object>  checkDuplicateEmail(String email);
	public Map<String,Object> sendAuthNumberToPhone(String phoneNum);
	public Map<String,Object> verifyAuthNumber(String randNumber, String phoneNum);
	
	public List<TagVO> getTagList();
}
