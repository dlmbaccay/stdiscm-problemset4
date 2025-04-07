package com.garynation.stdiscm.problemset4.apigatewaybroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayBrokerApplication.class, args);
	}

}
