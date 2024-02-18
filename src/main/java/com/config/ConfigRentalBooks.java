package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com")
public class ConfigRentalBooks implements WebMvcConfigurer{

	public ConfigRentalBooks() {
		super();
	}

}
