package com.yedamFinal.aco.secure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private List<String> permitAllUrl = null;
	
	public WebSecurityConfig() {
		// 로그인하지않아도 허용되는 경로들 삽입.
		permitAllUrl = new ArrayList<String>();
		
		// 각 이름별 함수에다 Url넣어주세요.
		this.insertPermitAllUrlByMin(); //민교
		this.insertPermitAllUrlByChae(); // 채민
		this.insertPermitAllUrlByHa(); // 하랑
		this.insertPermitAllUrlByKyung(); // 경석
		this.insertPermitAllUrlByTae(); // 태경
	}
			
	// 패스워드 암호화 설정
	@Bean 
	public BCryptPasswordEncoder bcryptEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.antMatchers(permitAllUrl.toArray(new String[permitAllUrl.size()])).permitAll() // 해당 경로의 페이지는 모두 접속허용
				.antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER")
				.anyRequest().authenticated() // 그 외는 모두 로그인해야만 접근이 가능
			)
			.formLogin((form) -> form
				.loginPage("/loginForm")
				.usernameParameter("userid")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}
	
	// 전민교
	private void insertPermitAllUrlByMin() {
		// 정적리소스도 시큐리티에 걸러지기때문에 꼭 추가해야함.
		permitAllUrl.add("/commonResource/**");
		permitAllUrl.add("/css/**");
		permitAllUrl.add("/js/**");
		permitAllUrl.add("/");
		permitAllUrl.add("/createAccountForm");
	}
	
	private void insertPermitAllUrlByChae() {
		
	}
	
	private void insertPermitAllUrlByHa() {
		
	}
	
	private void insertPermitAllUrlByKyung() {
		
	}
	
	private void insertPermitAllUrlByTae() {
		
	}
}
