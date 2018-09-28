package com.itqf.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;



@Configuration
public class KaptchaConfig {

	@Bean
	public DefaultKaptcha producer() {
		Properties config = new Properties();
		config.put("kaptcha.border", "no");
		config.put("kaptcha.textproducer.font.color", "black");
		config.put("kaptcha.textproducer.char.length", "4");//验证码个数
		
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(new Config(config));
		
		return kaptcha;
	}
	
}
