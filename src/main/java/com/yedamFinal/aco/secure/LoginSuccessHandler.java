package com.yedamFinal.aco.secure;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.member.service.MemberService;

//form요청이아닌 ajax요청으로 하기위해
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private MemberMapper memberMapper;
    

	@Autowired
	private AdminMapper adminMapper;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        MemberVO vo = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
            vo = userDetails.getMemberVO();
            
            vo = memberMapper.selectMemberInfo(vo);
            String banType = vo.getBanType();
            if(banType != null) {
            	Date banDate = vo.getBanDate();
            	Date today = new Date();
            	long diffSec = (today.getTime() - banDate.getTime()) / 1000; //초 차이
                long diffDays = diffSec / (24*60*60); //일자수 차이
            	
            	if(vo.getBanType().equals("M004")) {
                	if(diffDays >= 7) {
                		memberMapper.updateResetBan(vo.getMemberNo());
                	}
                	else
                		throw new LockedException("제재로 인한 로그인 실패(7일)");
                }
                else if(vo.getBanType().equals("M005")) {
                	if(diffDays >= 30) {
                		memberMapper.updateResetBan(vo.getMemberNo());
                	}
                	else
                		throw new LockedException("제재로 인한 로그인 실패(30일)");
                }
                else if(vo.getBanType().equals("M006")) {
                	throw new LockedException("제재로 인한 로그인 실패(영구정지)");
                }
            }
            
            
            request.getSession().setAttribute("member", vo);
            request.getSession().setAttribute("myEmoList",adminMapper.getMyEmoList(vo.getMemberNo()));
        }
        
        Map<String, Object> ret = new HashMap<>();
        if(!vo.getPermission().equals("ROLE_ADMIN")) {
        	if(vo.getGitToken() != null) {
        		SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

                if (savedRequest != null) {
                    String redirectUrl = savedRequest.getRedirectUrl();
                    // "http://"
                    redirectUrl = redirectUrl.replace("http://", "");
                    redirectUrl = redirectUrl.substring(redirectUrl.indexOf("/"));
                    ret.put("result", redirectUrl);
                } else {
                	ret.put("result", "/");
                }
        	}
        	else {
        		ret.put("result", "/gitLinkPage?id=" + vo.getId());
        	}
            
        }
        else {
        	ret.put("result", "/admin?PageNo=1");
        }

        response.getWriter().write(objectMapper.writeValueAsString(ret));
    }
}