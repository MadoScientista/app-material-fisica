package com.madoscientista.generador_ejercicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GeneradorEjerciciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneradorEjerciciosApplication.class, args);
	}

}
