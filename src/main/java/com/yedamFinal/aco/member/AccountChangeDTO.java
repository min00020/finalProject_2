package com.yedamFinal.aco.member;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AccountChangeDTO {
	private String email;
	private String name;
	private String nickname;
	private MultipartFile profileImage;
	private String tag;
	private String profileImageName;
	private int memberNo;
}
