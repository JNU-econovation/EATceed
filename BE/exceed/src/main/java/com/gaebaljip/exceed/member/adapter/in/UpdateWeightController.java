package com.gaebaljip.exceed.member.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.UpdateWeightRequest;
import com.gaebaljip.exceed.dto.UpdateWeightResponse;
import com.gaebaljip.exceed.member.application.UpdateWeightService;
import com.gaebaljip.exceed.member.application.port.in.UpdateWeightCommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[몸무게 수정]")
public class UpdateWeightController {
    private final UpdateWeightService updateWeightService;

    @Operation(summary = "회원 몸무게 및 목표 몸무게 수정", description = "회원 몸무게 및 목표 몸무게를 수정한다.")
    @PatchMapping("/members/weight")
    public ApiResponse<UpdateWeightResponse> updateWeight(
            UpdateWeightRequest request, @AuthenticationMemberId Long memberId) {
        return ApiResponseGenerator.success(
                updateWeightService.execute(
                        UpdateWeightCommand.of(request.weight(), request.targetWeight(), memberId)),
                HttpStatus.OK);
    }
}
