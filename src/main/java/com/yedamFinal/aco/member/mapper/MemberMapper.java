package com.yedamFinal.aco.member.mapper;

import java.util.List;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

public interface MemberMapper {
	// 회원 정보 단건조회
	public List<MemberVO> selectMemberInfo(MemberVO memberVO);
	// 이모티콘 구매내역
	public List<MyemoticonVO> selectMyemoticonList(MemberVO memberVO);
	// 책갈피 목록
	public List<MybookmarkVO> selectMybmList(MemberVO memberVO);
	//작성한 질문글 리스트
	public List<MyquestionVO> selectMyqList(MemberVO memberVO);
}
