package com.gaebaljip.exceed.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.isCommitted();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(
                response.getWriter(),
                new ApiResponse.CustomBody<>(false, null, new Error("5555", "권한이 없습니다.", HttpStatus.FORBIDDEN.toString()))
        );
    }
}
