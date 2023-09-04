package com.chat.chatcalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class ChatCalcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatCalcApplication.class, args);
	}

}
