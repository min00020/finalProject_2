package com.yedamFinal.aco.bookmark;

import java.util.Date;

import lombok.Data;

@Data
public class MybookmarkVO {
	private int memberNo;
	private int questionBoardNo;
	private Date registDate;
	private String title;
}
