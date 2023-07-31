package com.prueba.authcarvajalmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ AuthCarvajalMicroserviceApplication.MAIN_PACKAGE })
public class AuthCarvajalMicroserviceApplication {
	
	public static final String MAIN_PACKAGE = "com.prueba.authcarvajalmicroservice";

	public static void main(String[] args) {
		SpringApplication.run(AuthCarvajalMicroserviceApplication.class, args);
	}

}
