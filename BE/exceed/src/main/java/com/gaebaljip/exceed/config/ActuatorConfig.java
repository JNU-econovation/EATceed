package com.gaebaljip.exceed.config;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {
    @Bean
    public InMemoryHttpTraceRepository httpExchangeRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
