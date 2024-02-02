package com.yedamFinal.aco.answer;

import lombok.Data;

@Data
public class AnswerVO {
	//answerBoard
	private int answerBoardNo;
	private int questionBoardNo;
	private int memberNo;
	private String title;
	private String tag;
	private String contents;
	private String writeDate;
	private int viewCnt;
	private int recommendCnt;
	private String adoptStatus;
	
	//member
	private String id;
	private String profileImage;
	private String memberLevel;
}
