package com.yedamFinal.aco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan(basePackages = "com.yedamFinal.aco.**.mapper")
@PropertySource("classpath:config.properties")
public class AskCodeApplication {

	public static void main(String[] args) {
		//gsdg
		SpringApplication.run(AskCodeApplication.class, args);
	}
}
