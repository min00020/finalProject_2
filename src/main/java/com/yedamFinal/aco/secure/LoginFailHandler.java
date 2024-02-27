package com.yedamFinal.aco.secure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(exception);
        
        Map<String, Object> ret = new HashMap<>();
        // 제재된 계정인 경우.
        if(exception instanceof LockedException) {
        	ret.put("error", exception.getMessage());
        }
        else 
        	ret.put("error", "아이디 및 비밀번호를 확인해주세요.");
        
        response.getWriter().write(objectMapper.writeValueAsString(ret));
    }
}