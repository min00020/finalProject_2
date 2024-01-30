package com.yedamFinal.aco.member.service;

import java.util.List;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

public interface MemberService {
	//회원 정보 단건조회
	public List<MemberVO> getMemberInfo(MemberVO memberVO);
	//이모티콘 구매내역
	public List<MyemoticonVO> getMyemoList(MemberVO memberVO);
	//책갈피 목록
	public List<MybookmarkVO> getMybmList(MemberVO memberVO);
	//내가 작성한 질답글 목록
	public List<MyquestionVO> getMyqList(MemberVO memberVO);
	
}
