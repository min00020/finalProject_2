package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;

@Data
public class GitIssueDTO {
	private String issueTitle;
	private Date issueDate;
	private int commentCnt;
	private String issueUrl;
}
