package com.cos.navernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@EnableScheduling
@SpringBootApplication
public class Test20211008Application {

	public static void main(String[] args) {
		SpringApplication.run(Test20211008Application.class, args);
	}

}
