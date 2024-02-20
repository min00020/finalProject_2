package com.yedamFinal.aco.common;

import java.util.Date;

import lombok.Data;


// 깃 이슈, 깃 커밋 목록의 내용을 가져옴.
@Data
public class GitCommitDTO {
	private String commitMessage;
	private Date commitDate;
	private String commitUrl;
}
