package com.yedamFinal.aco.member;

import java.util.Date;

import lombok.Data;

@Data
public class FindAccountEmailLinkVO {
	private String accessKey;
	private String memberId;
	private Date expireDate;
}
