package com.yedamFinal.aco.freeboard;

import java.util.Date;

import lombok.Data;
@Data
public class MainTotalVO {
	
	public String boards;
	
	private int fboardNo;
	private int notificationNo;
	private int qnaBoardNo;
	private int questionBoardNo;
	
	private int boardNo;
	
	private int memberNo;
	private String title;
	private String nickname;
	private Date writeDate;
	private int viewCnt;
	
	private int accumActivityPoint;
	private int minute;
	private String profileImage;
	

//	private int pk;
}
