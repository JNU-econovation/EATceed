package com.gaebaljip.exceed.auth.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.LoginRequest;
import com.gaebaljip.exceed.dto.response.LoginResponse;

@Component
public interface AuthUsecase {
    LoginResponse execute(LoginRequest request);
}
