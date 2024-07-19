package com.gaebaljip.exceed.adapter.in.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.member.response.GetWeightResponse;
import com.gaebaljip.exceed.application.port.in.member.GetWeightUseCase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[몸무게 조회]")
public class GetWeightController {
    private final GetWeightUseCase getWeightUseCase;

    @GetMapping("/members/weight")
    @Operation(summary = "몸무게 조회", description = "회원의 몸무게와 목표 몸무게를 조회한다.")
    public ApiResponse<ApiResponse.CustomBody<GetWeightResponse>> getWeight(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        GetWeightResponse response = getWeightUseCase.execute(memberId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
