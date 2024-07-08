package com.gaebaljip.exceed.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@Profile("test")
public class MockS3Config {
    @Bean
    @Primary
    public S3Presigner s3Presigner() {
        return Mockito.mock(S3Presigner.class);
    }
}
