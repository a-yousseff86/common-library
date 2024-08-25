package com.devops.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonLibraryApplication extends SpringBootApplicationBase {

	public static void main(String[] args) {
		SpringBootApplicationBase.main(args, CommonLibraryApplication.class);
	}

}
