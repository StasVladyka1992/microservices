package by.vladyka.movie_info_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class MovieInfoServiceApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MovieInfoServiceApplication.class);
		application.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
		application.run(args);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

}
