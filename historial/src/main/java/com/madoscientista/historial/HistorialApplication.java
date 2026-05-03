package com.madoscientista.historial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class HistorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistorialApplication.class, args);
	}

}
