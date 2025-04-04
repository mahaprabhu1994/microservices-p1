package com.microservice.api_gateway_service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product-service", r -> r.path("/product/**")
                .filters(f -> f.rewritePath("/product/(?<segment>.*)", "/${segment}"))
                .uri("lb://PRODUCT-SERVICE"))
            .route("order-service", r -> r.path("/order/**")
                .filters(f -> f.rewritePath("/order/(?<segment>.*)", "/${segment}"))
                .uri("lb://ORDER-SERVICE"))
            .build();
    }
}