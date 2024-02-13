package com.yedamFinal.aco.qnaBoard;

import java.util.Date;

import lombok.Data;

@Data
public class QnABoardJoinVO {
	private int qnaBoardNo;
	private int memberNo;
	private String title;
	private String content;
	private String qnaAnswerContent;
	private Date writeDate;
	private Date changeDate;
	private String answerState;
	private String state;
	private int viewCnt;
	private int attachedFileNo;
	private String nickname;
	private String serverFileName;
	private String originFileName;
	private String filePath;
	private Date registerDate;
}
