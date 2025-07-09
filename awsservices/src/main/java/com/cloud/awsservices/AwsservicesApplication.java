package com.cloud.awsservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsservicesApplication.class, args);
	}

}
