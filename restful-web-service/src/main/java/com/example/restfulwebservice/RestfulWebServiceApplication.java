package com.example.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() { // 웹 요청과 관련해서 Locale을 추출하고 이 Locale 객체를 이용해서 알맞은 언어의 메시지를 선택
		SessionLocaleResolver localeResolver = new SessionLocaleResolver(); // Session에 Locale 정보를 넣고 이를 통해 다국어를 처리해주는 역할
		localeResolver.setDefaultLocale(Locale.KOREA);
		return localeResolver;
	}

}
