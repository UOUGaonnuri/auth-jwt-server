package com.gaounuri.authjwtserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthJwtServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthJwtServerApplication.class, args);
	}

}
