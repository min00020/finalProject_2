package com.yedamFinal.aco.noticeboard;

import java.sql.Date;

import lombok.Data;

@Data
public class NoticeBoardVO2 {
	private int noticeBoardNo;
	private int memberNo;
	private String title;
	private String content;
	private Date writeDate;
	private int viewCnt;
	private int recommendCnt;
	private Date showStartDate;
	private Date showEndDate;
	
	//메인페이지용
	private String nickname;
	private String profileImage;
	private int minute;

}
