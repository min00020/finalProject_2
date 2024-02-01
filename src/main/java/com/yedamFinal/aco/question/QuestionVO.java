package com.yedamFinal.aco.question;

import java.util.Date;

import lombok.Data;

@Data
public class QuestionVO {
	private int questionBoardNo;
	private int memberNo;
	private String topic;
	private int point;
	private String title;
	private String tag;
	private String contents;
	private Date writeDate;
	private int viewCnt;
	private int recommendCnt;
	private int bookmarkCnt;
	private int answerCnt;
	private String id;
	private String profileImage;
}
