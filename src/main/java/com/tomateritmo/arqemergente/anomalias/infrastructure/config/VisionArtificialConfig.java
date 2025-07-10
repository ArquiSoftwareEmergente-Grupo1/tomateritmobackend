package com.tomateritmo.arqemergente.anomalias.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VisionArtificialConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
