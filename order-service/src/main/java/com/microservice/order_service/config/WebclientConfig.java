package com.microservice.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    //we can use this bean to create a WebClient instance to communicate from OrderService
    // to PRODUCT-SERVICE
    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
