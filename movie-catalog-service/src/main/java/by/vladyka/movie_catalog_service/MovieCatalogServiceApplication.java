package by.vladyka.movie_catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
@EnableCircuitBreaker
//not mandatory
@EnableEurekaClient
@EnableHystrixDashboard
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MovieCatalogServiceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
		app.run(args);
	}

	@Bean
    @LoadBalanced
	public RestTemplate getRestTemplate(){
		//timeout for establishingConnection. If web server isn't available - it's not over case. It is used, when
		//server is available, but it can't process my request because it's already processing max number of requests
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(3000);
		return new RestTemplate(clientHttpRequestFactory);
	}

	@Bean
	public WebClient.Builder getWebClietnBuilder(){
		return WebClient.builder();
	}

}
