package com.yedamFinal.aco.question;

import lombok.Data;

@Data
public class QuestionVO {
	//questionBoard
	private int questionBoardNo;
	private int memberNo;
	private String topic;
	private int point;
	private String title;
	private String tag;
	private String contents;
	private String writeDate;
	private int viewCnt;
	private int recommendCnt;
	private int bookmarkCnt;
	private int answerCnt;
	
	//member
	private String id;
	private String profileImage;
	private String memberLevel;
	
	//answerBoard
	private int answerBoardNo;
	private String answerTitle;
	private String answerTag;
	private String answerContents;
	private String answerWriteDate;
	private int answerViewCnt;
	private int answerRecommendCnt;
	private String answerAdoptStatus;
	
	//questionAdd
	private int questionAddNo;
	private String addContents;
	private String addWriterType;
	private String addWriteDate;
}
