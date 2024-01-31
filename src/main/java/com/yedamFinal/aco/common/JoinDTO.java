package com.yedamFinal.aco.common;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.member.MemberVO;

import lombok.Data;

@Data
public class JoinDTO  {
	private MemberVO member;
	private MultipartFile file;
}
