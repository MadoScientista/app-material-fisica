package com.madoscientista.logos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogosApplication.class, args);
	}

}
