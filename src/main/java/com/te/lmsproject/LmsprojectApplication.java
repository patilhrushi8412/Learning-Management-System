package com.te.lmsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/* localhost:9090/swagger-ui/index.html */
@SpringBootApplication 
@OpenAPIDefinition
public class LmsprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsprojectApplication.class, args);
	}
}