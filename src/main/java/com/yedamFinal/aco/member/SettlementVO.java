package com.yedamFinal.aco.member;

import lombok.Data;

@Data
public class SettlementVO {
	private int settlementNo;
	private int memberNo;
	private int settlementReqPoint;
	private String reqAccount;
	private String reqAccountName;
	private String reqAccountBankcode;
	private String reqBankname;
	private String processStatus;
}
