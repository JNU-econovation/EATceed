package com.gaebaljip.exceed.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.services.ses.SesAsyncClient;

@Configuration
@Profile("test")
public class MockSesConfig {

    @Bean
    @Primary
    public SesAsyncClient sesAsyncClient() {
        return Mockito.mock(SesAsyncClient.class);
    }
}
