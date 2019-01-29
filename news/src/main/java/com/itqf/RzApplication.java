package com.itqf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages={"com.itqf"})
@MapperScan("com.itqf.mapper")
//@ServletComponentScan(basePackages= {"com.itqf.service","com.zyc"})
public class RzApplication {
	public static void main(String[] args) {
		SpringApplication.run(RzApplication.class, args);
	}
}
