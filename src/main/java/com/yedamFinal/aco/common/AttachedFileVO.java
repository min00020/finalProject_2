package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;

@Data
public class AttachedFileVO {
	private int attachedFileNo;
	private String boardType;
	private int boardNo;
	private String originFileName;
	private String serverFileName;
	private String fileExtension;
	private int fileOrder;
	private String filePath;
	private Date registerDate;
}
