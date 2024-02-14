package com.yedamFinal.aco.admin;

import lombok.Data;

@Data
public class AdminSettleVO {

	private int settlementNo;
	private int memberNo;
	private int settlementReqPoint;
	private int reqAccountNumber;
	private String reqAccountName;
	private int reqAccountBankcode;
	private String reqBankname;
	private String processState;
	
	private String settleName;
	private String settleState;	
}
