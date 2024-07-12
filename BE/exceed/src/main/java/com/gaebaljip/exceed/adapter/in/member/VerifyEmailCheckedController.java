package com.gaebaljip.exceed.adapter.in.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.application.port.in.member.VerifyEmailCheckedUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[회원가입]")
public class VerifyEmailCheckedController {

    private final VerifyEmailCheckedUsecase verifyEmailCheckedUsecase;

    @Operation(summary = "회원 가입후 이메일 인증 여부 확인", description = "회원 가입후 이메일 인증 여부 확인한다.")
    @GetMapping("/members/email/checked")
    public ApiResponse<ApiResponse.CustomBody<Boolean>> verifyEmailChecked(
            @RequestParam(value = "email") String email) {
        Boolean isChecked = verifyEmailCheckedUsecase.execute(email);
        return ApiResponseGenerator.success(isChecked, HttpStatus.OK);
    }
}
