package com.gaebaljip.exceed.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class MockS3Config extends S3Config {
    @Bean
    @Primary
    @Override
    public S3Presigner s3Presigner() {
        return Mockito.mock(S3Presigner.class);
    }
}
