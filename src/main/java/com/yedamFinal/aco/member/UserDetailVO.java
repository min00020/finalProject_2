package com.yedamFinal.aco.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//
public class UserDetailVO implements UserDetails {
	private MemberVO memberVO;
	
	public UserDetailVO() {}
	
	public UserDetailVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	// memberVo를 수정시켜줄 set메소드
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	// 페이지 접근권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> list = new ArrayList<>();
		
		// 권한은 ROLE_ 로 시작해야함.
		list.add(new SimpleGrantedAuthority(memberVO.getPermission()));
		return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return memberVO.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return memberVO.getId();
	}

	// 로그인제한같은거 가능함.
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
