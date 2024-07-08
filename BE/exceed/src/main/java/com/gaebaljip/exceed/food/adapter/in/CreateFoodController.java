package com.gaebaljip.exceed.food.adapter.in;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.request.CreateFoodRequest;
import com.gaebaljip.exceed.food.application.port.in.CreateFoodUseCase;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
@Tag(name = "[음식 등록]")
public class CreateFoodController {
    private final CreateFoodUseCase createFoodUseCase;

    @PostMapping("/food")
    public ApiResponse<CustomBody<Void>> createFood(
            @RequestBody @Valid CreateFoodRequest request, @AuthenticationMemberId Long memberId) {
        createFoodUseCase.execute(request, memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
