package com.yedamFinal.aco.secure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedamFinal.aco.common.serviceImpl.GitHubServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

//form요청이아닌 ajax요청으로 하기위해
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private GitHubServiceImpl gitService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
            MemberVO vo = userDetails.getMemberVO();
            
            // 깃 api 날려서 accessToken 최신화처리하기도 추가예정.
            
            request.getSession().setAttribute("member", vo);
        }

        Map<String, Object> ret = new HashMap<>();
        ret.put("result", true);

        response.getWriter().write(objectMapper.writeValueAsString(ret));
    }
}