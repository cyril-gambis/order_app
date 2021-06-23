package com.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
