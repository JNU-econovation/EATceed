package com.gaebaljip.exceed.adapter.in.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.application.port.in.auth.AuthUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.docs.auth.LoginExceptionDocs;
import com.gaebaljip.exceed.common.docs.auth.ReissueTokenExceptionDocs;
import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.TokenDTO;
import com.gaebaljip.exceed.common.dto.ReissueTokenDTO;
import com.gaebaljip.exceed.common.exception.auth.NotFoundRefreshTokenException;
import com.gaebaljip.exceed.common.security.AuthConstants;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;

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
    @ApiErrorExceptionsExample(LoginExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> login(
            @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        TokenDTO tokenDTO = authUsecase.execute(loginRequest);
        response.setHeader(AuthConstants.AUTH_HEADER.getValue(), tokenDTO.accessToken());
        setCookie(response, tokenDTO.refreshToken());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 한다.")
    @PostMapping("/auth/refresh")
    @ApiErrorExceptionsExample(ReissueTokenExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> refresh(
            HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader(AuthConstants.AUTH_HEADER.getValue());
        String refreshToken = getCookie(request.getCookies()).getValue();
        HttpRequestDTO httpRequestDTO =
                HttpRequestDTO.of(request.getRequestURL().toString(), request.getMethod());
        ReissueTokenDTO reissueTokenDTO =
                authUsecase.reIssueToken(accessToken, refreshToken, httpRequestDTO);
        response.setHeader(AuthConstants.AUTH_HEADER.getValue(), reissueTokenDTO.accessToken());
        setCookie(response, reissueTokenDTO.refreshToken());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    private void setCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private Cookie getCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie;
            }
        }
        throw NotFoundRefreshTokenException.EXECPTION;
    }
}
