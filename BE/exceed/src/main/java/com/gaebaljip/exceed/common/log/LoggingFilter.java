package com.gaebaljip.exceed.common.log;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private final List<String> excludeUrl =
            List.of("/actuator/health", "/actuator/prometheus", "/v1/health");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (excludeUrl.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        MDC.put("traceId", UUID.randomUUID().toString());

        ContentCachingRequestWrapper httpServletRequest =
                new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse =
                new ContentCachingResponseWrapper((HttpServletResponse) response);
        // 이렇게 한다고 해도 Byte의 길이만 설정해 두고 컨탠츠들은 복사하지 않는다 그래서 doFilter 다음에 실행시킨다.

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        String url = httpServletRequest.getRequestURI();
        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        log.info("request url : {}, request body : {}", url, reqContent);

        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();
        // Response도 마찬가지로 한번 읽으면 사라진다. 그래서 다시 채워줘야한다.ㅉ
        httpServletResponse.copyBodyToResponse(); // 다시한번 더 바디를 채워준다.

        log.info("response status : {}, responseBody : {}", httpStatus, resContent);
        MDC.clear();
    }
}
