package com.yedamFinal.aco.activity;

import java.util.Date;

import lombok.Data;
@Data
public class ActivityPointVO {
	private int activityPointNo;
	private int totalActivityPoint;
	private int accumActivityPoint;
	private int curActivityPoint;
	private String activityPointType;
	private Date activityPointDate;
	private int incDecActivityPoint;
	private String historyType;
	private int MINUTE;
}
