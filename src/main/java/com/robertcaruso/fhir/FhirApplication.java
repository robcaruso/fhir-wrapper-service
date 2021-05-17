package com.robertcaruso.fhir;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FhirApplication {
	@Value("${origins.url}")
	private String[] url;

	public static void main(String[] args) {
		SpringApplication.run(FhirApplication.class, args);
	}

	/**
	 * Cors configurer web mvc configurer.
	 *
	 * @return the web mvc configurer
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(url).allowedMethods("GET", "HEAD");
			}
		};
	}

}
