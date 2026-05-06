package com.madoscientista.notificador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NotificadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificadorApplication.class, args);
	}

}
