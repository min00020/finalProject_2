package com.yedamFinal.aco.admin;

import java.util.Date;

import lombok.Data;

@Data
public class AdminQnaVO {

	private int qnaBoardNo;
	private int memberNo;
	private String title;
	private String content;
	private Date writeDate;
	private Date changeDate;
	private String answerState;
	private int viewCnt;
	
	private String qnaName;
	private String qnaState;
}
