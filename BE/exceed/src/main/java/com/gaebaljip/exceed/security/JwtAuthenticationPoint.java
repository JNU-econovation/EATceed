package com.gaebaljip.exceed.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.isCommitted();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(
                response.getWriter(),
                new ApiResponse.CustomBody<>(false, null, new Error("6666", "JWT 토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED.toString()))
        );
    }

}
