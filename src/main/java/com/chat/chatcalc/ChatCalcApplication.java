package com.chat.chatcalc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class ChatCalcApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatCalcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatCalcApplication.class, args);
		LOGGER.info("*******************VESÃO 1 *******************************************");
	}



}
