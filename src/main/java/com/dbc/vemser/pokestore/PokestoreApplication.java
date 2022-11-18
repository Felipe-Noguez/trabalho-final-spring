package com.dbc.vemser.pokestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PokestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokestoreApplication.class, args);
	}

}
