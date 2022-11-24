package com.dbc.vemser.pokestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableKafka
@EnableScheduling
public class PokestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokestoreApplication.class, args);
	}

}
