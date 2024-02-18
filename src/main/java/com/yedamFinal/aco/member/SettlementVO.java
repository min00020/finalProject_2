package com.yedamFinal.aco.member;

import lombok.Data;

@Data
public class SettlementVO {
	private int memberNo;
	private int settlementReqPoint;
	private int reqAccount;
	private int reqAccountName;
	private int reqAccountBankcode;
	private String reqBankname;
	private String processStatus;
}
