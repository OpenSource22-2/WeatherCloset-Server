package com.opensource.weathercloset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WeatherclosetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherclosetServerApplication.class, args);
	}

}
