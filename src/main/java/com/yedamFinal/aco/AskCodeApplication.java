package com.yedamFinal.aco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.yedamFinal.aco.**.mapper")
public class AskCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AskCodeApplication.class, args);
	}
}
