package com.nexters.rezoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableOAuth2Client
public class RezoomApplication {
	public static void main(String[] args) {
		SpringApplication.run(RezoomApplication.class, args);
	}
}
