package com.amrat.HospitalManagementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HospitalManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementAppApplication.class, args);
	}

}
