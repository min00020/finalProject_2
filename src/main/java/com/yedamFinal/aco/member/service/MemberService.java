package com.yedamFinal.aco.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.TagVO;
import com.yedamFinal.aco.member.MemberVO;

public interface MemberService {
	public Map<String,Object>  checkDuplicateId(String id);
	public Map<String,Object>  checkDuplicateEmail(String email);
	public Map<String,Object> sendAuthNumberToPhone(String phoneNum);
	public Map<String,Object> verifyAuthNumber(String randNumber, String phoneNum);
	public Map<String,Object> joinMember(MemberVO vo, MultipartFile file);
	public Map<String,Object> loginMember(String userid, String userpw);
	public Map<String,Object> processGitLink(String userid, String tempUserGitCode);
	public Map<String,Object> findAccount(String email);
	public boolean verifyChangePasswordForm(String accessKey);
	public Map<String,Object> changePassword(String accessKey, String pwd, String pwdVerify);
	
	public List<TagVO> getTagList();
}
