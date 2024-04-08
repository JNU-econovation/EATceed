package com.gaebaljip.exceed.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import software.amazon.awssdk.services.ses.SesAsyncClient;

@Configuration
public class MockSesConfig extends SesConfig {

    @Bean
    @Primary
    @Override
    public SesAsyncClient sesAsyncClient() {
        return Mockito.mock(SesAsyncClient.class);
    }
}
