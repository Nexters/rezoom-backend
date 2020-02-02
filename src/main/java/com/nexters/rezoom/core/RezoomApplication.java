package com.nexters.rezoom.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RezoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(RezoomApplication.class, args);
	}
}
