package com.yedamFinal.aco.sideboard;

import java.util.Date;
import lombok.Data;

@Data
public class SideVO {
	private int sideBoardNo;
	private String publishingStatus;
	private String title;
	private String content;
	private String writer;
	private Date writeDate;
	private int recruitPersonnel;
	private int devPeriod;
	private String gitAddress;
	private int viewCnt;
	private int replyCnt;
	private String techOfUse;
	private int memberNo;
	private String projectOutline;
	private String status;
	private int bno;
	private int pk;
	
	
	private String profileImage;
	private String nickname;
	
}
