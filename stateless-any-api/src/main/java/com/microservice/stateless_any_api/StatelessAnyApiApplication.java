package com.microservice.stateless_any_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StatelessAnyApiApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(StatelessAnyApiApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);

	}

}
