package com.itqf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan("com.itqf.mapper")
public class RzApplication {
	public static void main(String[] args) {
		SpringApplication.run(RzApplication.class, args);
	}
}
