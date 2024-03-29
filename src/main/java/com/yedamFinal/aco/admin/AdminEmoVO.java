package com.yedamFinal.aco.admin;

import java.sql.Date;

import lombok.Data;

@Data
public class AdminEmoVO {

	private int emoNo;
	private String emoName;
	private int emoPrice;
	private String emoDesc;
	private String emoFirstimg;
	private String emoInnerimg;
	private String emoPath;
	private String emoState;
	
	private String emoticonState;	
	
	private Date emoBuydate;
	private int cnt;
	
	private String emoUseimg;
	
	private int pk;
	
	private int memberNo;
	private int acoPoint;
	private int acoMoney;
	
}
