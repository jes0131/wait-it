package com.jes.waitit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WaitItApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaitItApplication.class, args);
	}

}
