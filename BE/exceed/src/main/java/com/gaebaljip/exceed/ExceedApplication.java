package com.gaebaljip.exceed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ExceedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExceedApplication.class, args);
	}

}
