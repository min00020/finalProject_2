package com.yedamFinal.aco.noticeboard;

import java.sql.Date;

import lombok.Data;

@Data
public class NoticeBoardVO {
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
