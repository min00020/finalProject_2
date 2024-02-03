package com.yedamFinal.aco.common;


import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class NaverMailSender {
	
	private String host = "smtp.naver.com";
	@Value("${naver.email.smtp.from.id}")
	private String userName;
	@Value("${naver.email.smtp.from.pw}")
	private String userPw;
	@Value("${naver.email.smtp.from.port}")
	private int port;
	
	private JavaMailSenderImpl sender;
	
	@PostConstruct
    public void init() {
		sender = new JavaMailSenderImpl();
		
		sender.setHost(host);
		sender.setUsername(userName);
		sender.setPassword(userPw);//asd
		sender.setPort(port);
		
		Properties properties = new Properties();
	    properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
	    properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
	    properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
	    properties.setProperty("mail.debug", "true"); // 디버그 사용
	    properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); // ssl 인증 서버 (smtp 서버명)
	    properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용
	    
	    sender.setJavaMailProperties(properties);
    }
	
	
	public boolean sendEmail(String sendMailTitle, String sendMailBody) {
	    MimeMessage m = sender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m,"UTF-8");
        try {
        	h.setFrom(userName);
            h.setTo("c11286@naver.com");
            h.setSubject(sendMailTitle);
            h.setText(sendMailBody);
            sender.send(m);
        }
        catch(Exception e) {
        	System.out.println(e);
        	return false;
        }
        
		return true;
	}
	
}
