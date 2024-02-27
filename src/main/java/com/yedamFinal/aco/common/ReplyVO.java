package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	private int replyNo;
	private String boardType;
	private int boardNo;
	private int memberNo;
	private String nickName;
	private String contents;
	private Date writeDate;
	private String emoticon;
	private int recommendCnt;
	private String profileImage;
	private Integer replyPno;
}
