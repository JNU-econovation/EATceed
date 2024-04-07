package com.gaebaljip.exceed.auth.application.port.in;

import com.gaebaljip.exceed.dto.request.LoginRequest;
import com.gaebaljip.exceed.dto.response.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthUsecase {
    LoginResponse execute(LoginRequest request);
}
