package com.yedamFinal.aco.common.service;

import java.util.Map;

public interface GitHubService {
	public Map<String, String> getAccessTokenByGitLink(String tempGitCode);
	public Map<String, Object> getGitHubRepositoryInfo(String userAccessToken, String userRepositoryName);
}
