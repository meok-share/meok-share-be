package com.example.mapsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MapSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapSearchApplication.class, args);
	}

}
