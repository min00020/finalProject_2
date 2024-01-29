package com.yedamFinal.aco.member;

import java.util.Date;

public class MemberVO {

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
	
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getTopicHashtag() {
		return topicHashtag;
	}
	public void setTopicHashtag(String topicHashtag) {
		this.topicHashtag = topicHashtag;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public int getAccumActivityPoint() {
		return accumActivityPoint;
	}
	public void setAccumActivityPoint(int accumActivityPoint) {
		this.accumActivityPoint = accumActivityPoint;
	}
	public int getAvailableActivityPoint() {
		return availableActivityPoint;
	}
	public void setAvailableActivityPoint(int availableActivityPoint) {
		this.availableActivityPoint = availableActivityPoint;
	}
	public int getAcoMoney() {
		return acoMoney;
	}
	public void setAcoMoney(int acoMoney) {
		this.acoMoney = acoMoney;
	}
	public int getAcoPoint() {
		return acoPoint;
	}
	public void setAcoPoint(int acoPoint) {
		this.acoPoint = acoPoint;
	}
	public String getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}
	public String getSnsType() {
		return snsType;
	}
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}
	public String getBanType() {
		return banType;
	}
	public void setBanType(String banType) {
		this.banType = banType;
	}
	public String getBanDate() {
		return banDate;
	}
	public void setBanDate(String banDate) {
		this.banDate = banDate;
	}
}
