package com.kakaotech.team14backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Team14BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team14BackendApplication.class, args);
	}

}
