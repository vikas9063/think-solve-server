package com.vikky.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ThinkSolveServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThinkSolveServerApplication.class, args);
		log.info("{ApplicationStatus : {}}","App Started ...");
	}

}
