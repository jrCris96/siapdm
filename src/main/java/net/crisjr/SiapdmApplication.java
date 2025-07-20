package net.crisjr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SiapdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiapdmApplication.class, args);
	}

}
