package com.yedamFinal.aco.admin;

import java.util.Date;

import lombok.Data;

@Data
public class AdminMemberVO {

	private int memberNo;
	private String id;
	private String password;
	private String email;
	private String phoneNum;
	private String name;
	private String nickName;
	private String profileImage;
	private String topicHashtag;
	private String permission;
	private Date leaveDate;
	private int accumActivityPoint;
	private int availableActivityPoint;
	private int acoMoney;
	private int acoPoint;
	private String memberLevel;
	private String snsType;
	private String banType;
	private String banDate;
}
