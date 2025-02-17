package com.StudentCourseRegistrationSystem.SCRS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ScrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrsApplication.class, args);
	}

}

