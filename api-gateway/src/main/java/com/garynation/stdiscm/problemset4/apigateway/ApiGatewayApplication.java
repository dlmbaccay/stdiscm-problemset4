package com.garynation.stdiscm.problemset4.apigateway;

import com.garynation.stdiscm.problemset4.apigateway.config.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {
        return builder.routes()
                .route("auth-service",r -> r.path("/auth/v3/api-docs")
                        .uri("http://auth-service:8082"))
                .route("auth-service",r -> r.path("/auth/**")
                        .filters(f -> f.prefixPath("/api"))
                        .uri("http://auth-service:8082"))
                .route("course-service",r -> r.path("/courses/v3/api-docs")
                        .uri("http://course-service:8083"))
                .route("course-service",r -> r.path("/courses/**").filters(f -> f.prefixPath("/api").filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://course-service:8083"))
                .route("enrollment-service",r -> r.path("/enrollment/v3/api-docs")
                        .uri("http://enrollment-service:8084"))
                .route("enrollment-service",r -> r.path("/enrollment/**").filters(f -> f.prefixPath("/api").filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://enrollment-service:8084"))
                .route("grade-service",r -> r.path("/grades/v3/api-docs")
                        .uri("http://grade-service:8085"))
                .route("grade-service",r -> r.path("/grades/**").filters(f -> f.prefixPath("/api").filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://grade-service:8085"))
                .route("notification-service",r -> r.path("/notifications/v3/api-docs")
                        .uri("http://notification-service:8086"))
                .route("notification-service",r -> r.path("/notifications/**").filters(f -> f.prefixPath("/api").filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://notification-service:8086"))
                .build();
    }
}
