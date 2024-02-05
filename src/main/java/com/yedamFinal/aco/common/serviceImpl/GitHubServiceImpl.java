package com.yedamFinal.aco.common.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.yedamFinal.aco.common.service.GitHubService;

@Service
public class GitHubServiceImpl implements GitHubService {
	@Value("${github.oauth.client.id}")
	private String gitClientId;
	
	@Value("${github.oauth.client.secret.id}")
	private String gitClientSecretId;
	
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
		reqBodyContent.put("redirect_uri", "http://localhost/gitLinkPage"); // 이거 나중에 고쳐야함.
		
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
	public void getGitHubUserInfo(String userAccessToken) {
		// TODO Auto-generated method stub

	}

}
