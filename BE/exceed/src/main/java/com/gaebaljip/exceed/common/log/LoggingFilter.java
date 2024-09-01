package com.gaebaljip.exceed.common.log;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private final List<String> excludeUrl =
            List.of(
                    "/actuator/health",
                    "/actuator/prometheus",
                    "/v1/health",
                    "/api-docs/swagger-config",
                    "/api-docs");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (excludeUrl.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        MDC.put(LogKey.KEY, UUID.randomUUID().toString());

        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper httpServletResponse =
                new ContentCachingResponseWrapper(response);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        stopWatch.stop();

        HttpLogMessage httpLogMessage =
                HttpLogMessage.of(
                        httpServletRequest, httpServletResponse, stopWatch.getTotalTimeSeconds());
        log.info("{}", httpLogMessage);

        httpServletResponse.copyBodyToResponse(); // 다시한번 더 바디를 채워준다.

        MDC.remove(LogKey.KEY);
    }
}
