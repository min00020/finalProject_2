package com.yedamFinal.aco.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.common.TagVO;
import com.yedamFinal.aco.member.MemberVO;

public interface MemberMapper { 
	public MemberVO selectCheckDuplicateId(String id);
	public MemberVO selectCheckDuplicateEmail(String email);
	public MemberVO selectLogin(String id);
	public int insertAuthNumber(@Param(value = "authNum") String authNum, @Param(value = "phoneNum") String phoneNum);
	public String selectAuthNumber(String phoneNum);
	public String selectVerifyAuthNumber(@Param(value = "authNum") String authNum, @Param(value = "phoneNum") String phoneNum);
	public int deleteAuthNumber(String phoneNum);
	public int insertMember(MemberVO memberVO);
	public int updateMemberGitToken(@Param(value ="gitToken") String gitToken, @Param(value = "id") String id);
	
	public List<TagVO> selectTagList();
}
