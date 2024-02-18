package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentalBooksApplication {

	public RentalBooksApplication() {
		super();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RentalBooksApplication.class, args);
	}

}