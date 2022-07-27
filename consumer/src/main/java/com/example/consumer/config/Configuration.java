package com.example.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author cph
 * @date 2022/07/17
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplateLoadBalanced() {
        return new RestTemplate();
    }
}
