package com.yedamFinal.aco.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.common.TagVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

public interface MemberService {
	public Map<String,Object>  checkDuplicateId(String id);
	public Map<String,Object>  checkDuplicateEmail(String email);
	public Map<String,Object> sendAuthNumberToPhone(String phoneNum);
	public Map<String,Object> verifyAuthNumber(String randNumber, String phoneNum);
	public Map<String,Object> joinMember(MemberVO vo, MultipartFile file);
	public Map<String,Object> loginMember(String userid, String userpw);
	public Map<String,Object> processGitLink(String userid, String tempUserGitCode);
	
	//회원 정보 단건조회
	public MemberVO getMemberInfo(MemberVO memberVO);
	//이모티콘 구매내역
	public List<MyemoticonVO> getMyemoList(MemberVO memberVO);
	//책갈피 목록
	public List<MybookmarkVO> getMybmList(MemberVO memberVO);
	// 책갈피 더보기 목록
	public List<MybookmarkVO> getMyBookList(MemberVO memberVO);
	//내가 작성한 질답글 목록
	public List<MyquestionVO> getMyqList(MemberVO memberVO);
	
	public List<TagVO> getTagList();
}
