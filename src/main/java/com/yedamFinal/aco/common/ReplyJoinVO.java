package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;


// 댓글/대댓글 select하기 위한 VO
@Data
public class ReplyJoinVO {
	private int parentReplyNo;
	private String parentComment;
	private Date parentWriteDate;
	private int parentMemberNo;
	private String parentNickname;
	private String parentImage;
	private int parentRecCnt;
	private Integer childReplyNo;
	private String childComment;
	private Date childWriteDate;
	private int childMemberNo;
	private String childNickname;
	private String childImage;
	private int childRecCnt;
}
