package com.gameshop.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.gameshop.ecommerce.utils.Constants.DEPLOY_STORE;
import static com.gameshop.ecommerce.utils.Constants.LOCALHOST;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        final var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(Arrays.asList(LOCALHOST, DEPLOY_STORE));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return urlBasedCorsConfigurationSource;
    }
}
