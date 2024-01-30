package com.yedamFinal.aco.member.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.member.service.MemberService;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;


@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//회원 단건조회
	@Override
	public List<MemberVO> getMemberInfo(MemberVO memberVO) {
		return memberMapper.selectMemberInfo(memberVO);
	}
	//이모티콘 구매내역
	@Override
	public List<MyemoticonVO> getMyemoList(MemberVO memberVO){
		return memberMapper.selectMyemoticonList(memberVO);
	}
	//책갈피 목록
	@Override
	public List<MybookmarkVO> getMybmList(MemberVO memberVO) {
		return memberMapper.selectMybmList(memberVO);
	}
	//내가 작성한 질문글 목록
	@Override
	public List<MyquestionVO> getMyqList(MemberVO memberVO) {
		return memberMapper.selectMyqList(memberVO);
	}
	
}
