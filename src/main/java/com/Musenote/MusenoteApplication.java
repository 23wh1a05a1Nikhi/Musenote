package com.Musenote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.model")
@EnableJpaRepositories(basePackages = "com.dao")
@SpringBootApplication(scanBasePackages = "com")
public class MusenoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusenoteApplication.class, args);
	}

}
