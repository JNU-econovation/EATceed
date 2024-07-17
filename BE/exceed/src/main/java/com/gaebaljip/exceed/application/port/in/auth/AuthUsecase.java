package com.gaebaljip.exceed.application.port.in.auth;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.LoginResponseDTO;
import com.gaebaljip.exceed.common.dto.request.LoginRequest;

@Component
public interface AuthUsecase {
    LoginResponseDTO execute(LoginRequest request);
}
