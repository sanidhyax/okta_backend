package com.assesment.extenddummyjson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories("com.*")
@SpringBootApplication
@EntityScan("com.*")
@ComponentScan(basePackages = "com.assesment.extenddummyjson")
public class ExtenddummyjsonApplication {

	public static void main(String[] args) {

		SpringApplication.run(ExtenddummyjsonApplication.class, args);
	}

}
