package by.vladyka.rating_info_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class RatingInfoServiceApplication {

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(RatingInfoServiceApplication.class);
	    app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
	    app.run(args);
	}

}
