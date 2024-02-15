package com.yedamFinal.aco.question.web;

import java.util.Date;

import lombok.Data;
@Data
public class QuestionActivityPointVO {
	private int memberNo;
	private String activityPointNo;
	private int accumActivityPoint;
	private int curActivityPoint;
	private String activityPointYype;
	private Date activityPointDate;
	private int incDecActivityPoint;
}
