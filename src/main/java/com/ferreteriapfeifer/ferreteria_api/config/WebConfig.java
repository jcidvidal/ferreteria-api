package com.ferreteriapfeifer.ferreteria_api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
	                        .allowedOrigins(
                            "http://localhost:3000",
                            "http://localhost:3001",
                            "http://localhost:3002",
                            "http://localhost:3003",
                            "http://localhost:3004"
                        )

 
                        .allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://localhost:3001")
                        .allowedOrigins("http://localhost:3002")
                        .allowedOrigins("http://localhost:3003")
                        .allowedOrigins("http://localhost:3004")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

