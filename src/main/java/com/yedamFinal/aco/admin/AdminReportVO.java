package com.yedamFinal.aco.admin;

import java.util.Date;

import lombok.Data;

@Data
public class AdminReportVO {

	private int reportNo;
	private String reportType;
	private String reportContent;
	private int reporter;
	private Date reportDate;
	private String reportState;
	private String boardType;
	private int boardNo;
	private int reportee;
	
	private String reporterName;
	private String reporteeName;
}
