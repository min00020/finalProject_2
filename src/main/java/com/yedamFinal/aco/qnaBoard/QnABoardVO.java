package com.yedamFinal.aco.qnaBoard;

import java.util.Date;

import lombok.Data;

@Data
public class QnABoardVO {
	private int qnaBoardNo;
	private int memberNo;
	private String title;
	private String content;
	private String qnaAnswerContent;
	private Date writeDate;
	private Date changeDate;
	private String answerState;
	private int viewCnt;
	private String nickname;
	
	// pk property
	private int pk;
}
