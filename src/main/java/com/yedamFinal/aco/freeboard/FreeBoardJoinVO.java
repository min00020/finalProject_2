package com.yedamFinal.aco.freeboard;

import java.util.Date;

import lombok.Data;
@Data
public class FreeBoardJoinVO {
	
	private int fboardNo;
	private int memberNo;
	private String title;
	private String content;
	private String nickname;
	private Date writeDate;
	private int viewCnt;
	private int recommendCnt;
	private int replyCnt;
	private int accumActivityPoint;
	private int minute;
	private String profileImage;
	
	private int attachedFileNo;
	private String serverFileName;
	private String originFileName;
	private String filePath;
	private Date registerDate;
}
