package com.yedamFinal.aco.common.service;

public interface GitHubService {
	public void getAccessTokenByGitLink(String tempGitCode);
	public void getGitHubUserInfo(String userAccessToken);
}
