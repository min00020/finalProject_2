package com.yedamFinal.aco.myemoticon;

import java.util.Date;

import lombok.Data;
@Data
public class MyemoticonVO {
	 private int memberNo;
	   private int emoNo;
	   private Date emoBuyDate;
	   private int emoUseCnt;
	    private String emoName;    
}
