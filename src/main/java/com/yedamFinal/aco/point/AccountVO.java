package com.yedamFinal.aco.point;

import lombok.Data;

@Data
public class AccountVO {

	private int accountNo;
	private int memberNo;
	private String accountHolder;
	private String bankCode;
	private String bankName;
}