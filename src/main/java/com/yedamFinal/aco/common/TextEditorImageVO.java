package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;

@Data
public class TextEditorImageVO {
	private int imageNo;
	private String boardType;
	private int boardNo;
	private String originFileName;
	private String serverFileName;
	private String fileExtension;
	private Date registerDate;
	private String filePath;
	
	private int pk;
}
