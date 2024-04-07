package com.gaebaljip.exceed.auth.adapter;

import com.gaebaljip.exceed.auth.application.port.in.AuthUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.request.LoginRequest;
import com.gaebaljip.exceed.dto.response.LoginResponse;
import com.gaebaljip.exceed.security.AuthConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthController {

    private final AuthUsecase authUsecase;

    @PostMapping("/auth/login")
    public ApiResponse<ApiResponse.CustomBody<Void>> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authUsecase.execute(loginRequest);
        response.setHeader(AuthConstants.AUTH_HEADER.getValue(), loginResponse.accessToken());
        setCookie(response, loginResponse.refreshToken());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    private void setCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
