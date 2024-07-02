package com.gaebaljip.exceed.auth.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.LoginResponseDTO;
import com.gaebaljip.exceed.dto.request.LoginRequest;

@Component
public interface AuthUsecase {
    LoginResponseDTO execute(LoginRequest request);
}
