package com.xische.xischebilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XischebillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(XischebillingApplication.class, args);
	}

}
