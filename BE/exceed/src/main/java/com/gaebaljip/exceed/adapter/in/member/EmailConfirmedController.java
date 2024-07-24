package com.gaebaljip.exceed.adapter.in.member;

import com.gaebaljip.exceed.application.port.in.member.EmailConfirmedUseCase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[회원가입]")
public class EmailConfirmedController {
    private final EmailConfirmedUseCase emailConfirmedUseCase;
    @PutMapping("/members/email/confirmed")
    @Operation(summary = "이메일 인증을 true로 바꾼다", description = "이메일 인증을 true바꾼다.")
    public ApiResponse<ApiResponse.CustomBody<Void>> emailConfirmed(String email) {
        emailConfirmedUseCase.execute(email);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
