package com.yedamFinal.aco.question;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class QuestionVO {
	//questionBoard
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
	private int replyCnt;
	 
	//member
	private String id;
	private String profileImage;
	private String memberLevel;
	private String nickname;
	private String accumActivityPoint;
	
	private String answerId;
	private String answerNickname;
	private String answerProfileImage;
	private String answerMemberLevel;
	
	//answerBoard
	private int answerMemberNo;
	private int answerBoardNo;
	private String answerTitle;
	private String answerContents;
	private Date answerWriteDate;
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
	private String addAdoptStatus;
	
	
	
	private int pk;
}
