package com.tomateritmo.arqemergente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ArqemergenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArqemergenteApplication.class, args);
	}

}
