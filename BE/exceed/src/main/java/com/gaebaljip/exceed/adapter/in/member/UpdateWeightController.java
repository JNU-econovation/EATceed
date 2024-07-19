package com.gaebaljip.exceed.adapter.in.member;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.member.request.UpdateWeightRequest;
import com.gaebaljip.exceed.adapter.in.member.response.UpdateWeightResponse;
import com.gaebaljip.exceed.application.port.in.member.UpdateWeightCommand;
import com.gaebaljip.exceed.application.service.member.UpdateWeightService;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.common.event.UpdateWeightEvent;

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
    private final UpdateWeightUsecase updateWeightUsecase;

    @Operation(summary = "회원 몸무게 및 목표 몸무게 수정", description = "회원 몸무게 및 목표 몸무게를 수정한다.")
    @PatchMapping("/members/weight")
    public ApiResponse<UpdateWeightResponse> updateWeight(
            HttpServletRequest servletRequest,
            @RequestBody UpdateWeightRequest request,
            @AuthenticationMemberId Long memberId) {
        UpdateWeightResponse response =
                updateWeightUsecase.execute(
                        UpdateWeightCommand.of(request.weight(), request.targetWeight(), memberId));
        Events.raise(
                UpdateWeightEvent.from(
                        memberId, servletRequest.getRequestURI(), LocalDateTime.now()));
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
