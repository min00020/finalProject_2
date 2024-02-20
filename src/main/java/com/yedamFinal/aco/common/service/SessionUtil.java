package com.yedamFinal.aco.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

public class SessionUtil {
	public static MemberVO getSession() {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}
		return memberVO;
	}
}
