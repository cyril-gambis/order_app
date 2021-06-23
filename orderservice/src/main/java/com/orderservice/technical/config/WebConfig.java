package com.orderservice.technical.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    /**
     * CORS management
     *
     * @author Cyril Gambis
     * @date 22 juin 2021
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedMethods(GET.name(), PATCH.name(), POST.name(), PUT.name(), DELETE.name())
                .allowedHeaders(CorsConfiguration.ALL);
    }
}
