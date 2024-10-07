package com.gaebaljip.exceed.application.port.in.auth;

import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.ReissueTokenDTO;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.common.dto.LoginResponseDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public interface AuthUsecase {
    LoginResponseDTO execute(LoginRequest request);
    ReissueTokenDTO reIssueToken(String accessToken, String refreshToken, HttpRequestDTO requestDTO);

}
