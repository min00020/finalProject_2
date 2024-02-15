package com.yedamFinal.aco.admin;

import java.sql.Date;

import lombok.Data;

@Data
public class AdminMainVO {

	private String reportState;
	private String answerState;
	private String processStatus;
	
	private int scount;
	
	private int noticeBoardNo;
	private int memberNo;
	private String title;
	private String content;
	private Date writeDate;
	private int viewCnt;
	private int recommendCnt;
	private Date showStartDate;
	private Date showEndDate;
	
}
