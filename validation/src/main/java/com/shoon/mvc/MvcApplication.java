package com.shoon.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MvcApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}

	// 전역으로 validation 설정
	/* @Override
	public Validator getValidator() {
		return new ItemValidator();
	}*/
}
