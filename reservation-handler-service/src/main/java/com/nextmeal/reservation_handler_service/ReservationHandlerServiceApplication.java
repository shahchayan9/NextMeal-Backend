package com.nextmeal.reservation_handler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservationHandlerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservationHandlerServiceApplication.class, args);
	}
}