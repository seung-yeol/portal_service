package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	//main메서드가 있다는 것은 jar 를 실행하면 요게 실행이 된다!
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
