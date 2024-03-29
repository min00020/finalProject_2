package com.yedamFinal.aco.point;

import java.util.Date;

import lombok.Data;

@Data
public class PointDetailVO {
	private int pointDetailNo;
    private int memberNo;
    private int latestTotalPoints;
    private int latestAcoMoney;
    private int latestAcoPoint;
    private String historyType;
    private Date historyDate;
    private int incDecPoint;
    private String pointType;
    private String accountNo;
    private String bankName;
    
}
