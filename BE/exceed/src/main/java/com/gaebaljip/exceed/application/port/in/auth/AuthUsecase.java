package com.gaebaljip.exceed.application.port.in.auth;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.TokenDTO;
import com.gaebaljip.exceed.common.dto.ReissueTokenDTO;

@Component
public interface AuthUsecase {
    TokenDTO execute(LoginRequest request);

    ReissueTokenDTO reIssueToken(
            String accessToken, String refreshToken, HttpRequestDTO requestDTO);
}
