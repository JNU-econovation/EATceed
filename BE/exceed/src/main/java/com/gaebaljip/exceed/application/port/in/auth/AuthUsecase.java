package com.gaebaljip.exceed.application.port.in.auth;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.TokenDTO;

@Component
public interface AuthUsecase {
    TokenDTO login(LoginRequest request);

    TokenDTO reIssueToken(String accessToken, String refreshToken, HttpRequestDTO requestDTO);

    void logout(String memberId);
}
