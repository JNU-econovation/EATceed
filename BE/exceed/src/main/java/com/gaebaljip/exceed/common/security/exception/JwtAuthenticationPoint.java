package com.gaebaljip.exceed.common.security.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.Error;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        if (request.getAttribute("exception") == null) {
            handleAuthenticationException(response);
        } else {
            resolver.resolveException(
                    request, response, null, (Exception) request.getAttribute("exception"));
        }
    }

    private void handleAuthenticationException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.isCommitted();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(
                response.getWriter(),
                new ApiResponse.CustomBody<>(
                        false,
                        null,
                        new Error(
                                SecurityErrorCode.NEED_AUTHENTICATION.getCode(),
                                SecurityErrorCode.NEED_AUTHENTICATION.getReason(),
                                HttpStatus.BAD_REQUEST.toString())));
    }
}
