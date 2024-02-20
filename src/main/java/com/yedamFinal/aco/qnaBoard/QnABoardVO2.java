package com.yedamFinal.aco.qnaBoard;

import java.util.Date;

import lombok.Data;

@Data
public class QnABoardVO2 {
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

	
	private int pk;
	
	//메인페이지용
	private int minute;
}
