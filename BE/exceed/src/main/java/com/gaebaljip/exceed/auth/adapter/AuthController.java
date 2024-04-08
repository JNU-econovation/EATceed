package com.gaebaljip.exceed.auth.adapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.auth.application.port.in.AuthUsecase;
import com.gaebaljip.exceed.auth.docs.AuthExceptionDocs;
import com.gaebaljip.exceed.auth.docs.SecurityExceptionDocs;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;
import com.gaebaljip.exceed.dto.request.LoginRequest;
import com.gaebaljip.exceed.dto.response.LoginResponse;
import com.gaebaljip.exceed.security.AuthConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[인증]")
public class AuthController {

    private final AuthUsecase authUsecase;

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/auth/login")
    @ApiErrorExceptionsExample(AuthExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> login(
            @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
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

    @Operation(summary = "인증/인가 공통", description = "인증/인가 공통 예외")
    @ApiErrorExceptionsExample(SecurityExceptionDocs.class)
    @PostMapping("/auth/common")
    public ApiResponse<ApiResponse.CustomBody<Void>> common() {
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
