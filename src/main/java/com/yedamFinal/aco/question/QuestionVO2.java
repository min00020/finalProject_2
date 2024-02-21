package com.yedamFinal.aco.question;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class QuestionVO2 {
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
	private int replyCnt;
	 
	//member
	private String id;
	private String profileImage;
	private String memberLevel;
	
	private String answerId;
	private String answerProfileImage;
	private String answerMemberLevel;
	
	//answerBoard
	private int answerMemberNo;
	private int answerBoardNo;
	private String answerTitle;
	private String answerContents;
	private String answerWriteDate;
	private int answerViewCnt;
	private int answerRecommendCnt;
	private String answerAdoptStatus;
	private int answerReplyCnt;
	
	//questionAdd
	private int questionAddNo;
	private String addContents;
	private String addWriterType;
	private Date addWriteDate;
	private String addStatus;
	
	private int pk;
	
	//메인페이지용
	private String nickname;
	private int minute;
	private int accumActivityPoint;
}
