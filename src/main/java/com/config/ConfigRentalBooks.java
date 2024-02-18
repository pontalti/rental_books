package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com")
public class ConfigRentalBooks {

	public ConfigRentalBooks() {
		super();
	}

}
