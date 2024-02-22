package com.yedamFinal.aco.common.service;

import java.util.Map;

public interface GitHubService {
	public Map<String, String> getAccessTokenByGitLink(String tempGitCode);
	public Map<String, Object> getGitHubRepositoryInfo(String userAccessToken, String userRepositoryName);
	public Map<String, Object> insertGitIssue(String usertAccessToken, String userRepositoryName, String title, String body);
	public boolean checkExpireGitAccessToken(String accessToken);
}
