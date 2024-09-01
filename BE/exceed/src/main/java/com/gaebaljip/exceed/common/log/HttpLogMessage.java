package com.gaebaljip.exceed.common.log;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.Builder;

@Builder
public record HttpLogMessage(
        String httpMethod,
        String requestUri,
        String requestBody,
        String httpStatus,
        String responseBody,
        Double elapsedTime) {
    public static HttpLogMessage of(
            ContentCachingRequestWrapper requestWrapper,
            ContentCachingResponseWrapper responseWrapper,
            Double elapsedTime) {
        return HttpLogMessage.builder()
                .httpMethod(requestWrapper.getMethod())
                .requestUri(requestWrapper.getRequestURI())
                .requestBody(new String(requestWrapper.getContentAsByteArray()))
                .httpStatus(String.valueOf(responseWrapper.getStatus()))
                .responseBody(new String(responseWrapper.getContentAsByteArray()))
                .elapsedTime(elapsedTime)
                .build();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\n")
                .append("\"httpMethod\": \"")
                .append(httpMethod)
                .append("\",\n")
                .append("\"requestUri\": \"")
                .append(requestUri)
                .append("\",\n")
                .append("\"requestBody\": ")
                .append(requestBody)
                .append(",\n")
                .append("\"httpStatus\": \"")
                .append(httpStatus)
                .append("\",\n")
                .append("\"responseBody\": ")
                .append(responseBody)
                .append(",\n")
                .append("\"elapsedTime\": ")
                .append(elapsedTime)
                .append("\n")
                .toString();
    }
}
