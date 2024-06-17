package com.biopark.notifications.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class NotificationsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsApiApplication.class, args);
	}

}
