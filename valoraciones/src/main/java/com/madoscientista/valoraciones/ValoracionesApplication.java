package com.madoscientista.valoraciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ValoracionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValoracionesApplication.class, args);
	}

}
