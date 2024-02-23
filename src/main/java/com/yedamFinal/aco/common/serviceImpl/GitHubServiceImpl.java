package com.yedamFinal.aco.common.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.yedamFinal.aco.common.GitCommitDTO;
import com.yedamFinal.aco.common.GitIssueDTO;
import com.yedamFinal.aco.common.service.GitHubService;

@Service
public class GitHubServiceImpl implements GitHubService {
	@Value("${github.oauth.client.id}")
	private String gitClientId;
	
	@Value("${github.oauth.client.secret.id}")
	private String gitClientSecretId;
	
	private String gitRedirectUrl = "http://askcode.shop/gitLinkPage";
	
	// Github OAuth는 refreshToken발급이 안돼서 DB에 저장된 accessToken 사용에 실패한 경우 git연동 Link로 redirect를 유도해야함.
	
	
	private WebClient webClient;
	
	public GitHubServiceImpl() {
		webClient = WebClient.create();
	}
	
	// 쿼리스트링 파싱
	private Map<String, String> getQueryMap(String query)
    {    	
    	if (query==null) return null;
    	
    	int pos1 = query.indexOf("?");
    	if (pos1 >= 0) {
    		query=query.substring(pos1+1);
    	}
    	
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
        	String[] paramSplit = param.split("=");
        	// key value 한쌍인거만 체크.
        	if(paramSplit.length == 2) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
        	}
        }
        return map;
    }



	@Override
	public Map<String, String> getAccessTokenByGitLink(String tempGitCode) {
		Map<String,String> reqBodyContent = new HashMap<String,String>();
		reqBodyContent.put("client_id", gitClientId);
		reqBodyContent.put("client_secret", gitClientSecretId);
		reqBodyContent.put("code", tempGitCode);
		reqBodyContent.put("redirect_uri", gitRedirectUrl); 
		
		// TODO Auto-generated method stub
		String apiUrl = "https://github.com/login/oauth/access_token";
		String response = webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(reqBodyContent))
                .retrieve()
                .bodyToMono(String.class).block();
		
		return getQueryMap(response); 
	}

	@Override
	public Map<String,Object> getGitHubRepositoryInfo(String userAccessToken, String userRepositoryName) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
            GitHub github = new GitHubBuilder().withOAuthToken(userAccessToken).build();  // GitHub 인증 토큰으로 GitHub 객체
            GHRepository repository = github.getRepository(userRepositoryName);  // 리포지토리의 정보
            List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);  // 모든 이슈 정보
            List<GHCommit> commitList = repository.listCommits().toList();
            
            List<GitIssueDTO> myGitIssueDTOList = new ArrayList<GitIssueDTO>();
            for(GHIssue issue : issues) {
            	GitIssueDTO issueDTO = new GitIssueDTO();
            	issueDTO.setIssueTitle(issue.getTitle());
            	issueDTO.setIssueDate(issue.getCreatedAt());
            	issueDTO.setCommentCnt(issue.getCommentsCount());
            	issueDTO.setIssueUrl(issue.getHtmlUrl().toString());
            	
            	myGitIssueDTOList.add(issueDTO);
            }
            
            List<GitCommitDTO> myGitCommitDTOList = new ArrayList<GitCommitDTO>();
            for(GHCommit commit : commitList) {
            	GitCommitDTO commitDTO = new GitCommitDTO();
            	
            	commitDTO.setCommitMessage(commit.getCommitShortInfo().getMessage());
            	commitDTO.setCommitDate(commit.getCommitDate());
            	commitDTO.setCommitUrl(commit.getHtmlUrl().toString());
            	
            	myGitCommitDTOList.add(commitDTO);
            }
            
            result.put("issueList", myGitIssueDTOList);
            result.put("commitList", myGitCommitDTOList);
            
        } catch (IOException e) {
        }	
		
		return result;
	}

	public Map<String, Object> insertGitIssue(String userAccessToken, String userRepositoryName,String title, String body) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			GitHub github = new GitHubBuilder().withOAuthToken(userAccessToken).build();
			GHRepository repository = github.getRepository(userRepositoryName);
			
			repository.createIssue(title)
            .body(body)
            .create();
			
			map.put("result", "200");
			
			} catch (IOException e) {
				System.out.println(e);
				map.put("result", "500");
			}
			return map;
	}

	@Override
	public boolean checkExpireGitAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		try {
			GitHub github = new GitHubBuilder().withOAuthToken(accessToken).build();
			github.getMyself();
		}
		catch(Exception e) {
			return true;
		}
		return false;
	}
}