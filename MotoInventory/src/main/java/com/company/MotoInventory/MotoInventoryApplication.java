package com.company.MotoInventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MotoInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoInventoryApplication.class, args);
	}

}
