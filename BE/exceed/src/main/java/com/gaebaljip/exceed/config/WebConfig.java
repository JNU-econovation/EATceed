package com.gaebaljip.exceed.config;

import com.gaebaljip.exceed.common.annotation.AuthenticationMemberIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final AuthenticationMemberIdArgumentResolver authenticationMemberIdArgumentResolver;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationMemberIdArgumentResolver);
    }
}
