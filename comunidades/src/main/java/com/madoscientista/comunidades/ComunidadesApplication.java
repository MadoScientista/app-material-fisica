package com.madoscientista.comunidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class ComunidadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunidadesApplication.class, args);
	}

}
