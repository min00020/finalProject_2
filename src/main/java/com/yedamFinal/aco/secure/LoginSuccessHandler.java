package com.yedamFinal.aco.secure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

//form요청이아닌 ajax요청으로 하기위해
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> ret = new HashMap<>();
        ret.put("result", true);

        response.getWriter().write(objectMapper.writeValueAsString(ret));
    }
}