package com.gaebaljip.exceed.adapter.in.food;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.application.port.in.food.GetOwnFoodUseCase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.response.GetOwnFoodResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
@Tag(name = "[내가 추가한 음식 조회]")
public class GetOwnFoodController {
    private final GetOwnFoodUseCase getOwnFoodUseCase;

    @GetMapping("/foods/own")
    @Operation(summary = "내가 추가한 음식 조회", description = "내가 추가한 음식을 조회한다.")
    public ApiResponse<ApiResponse.CustomBody<List<GetOwnFoodResponse>>> getOwnFoods(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        List<GetOwnFoodResponse> response = getOwnFoodUseCase.execute(memberId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
