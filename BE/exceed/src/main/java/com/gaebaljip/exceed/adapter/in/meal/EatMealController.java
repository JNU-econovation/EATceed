package com.gaebaljip.exceed.adapter.in.meal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.meal.request.EatMealRequest;
import com.gaebaljip.exceed.adapter.in.meal.response.EatMealResponse;
import com.gaebaljip.exceed.application.port.in.meal.EatMealCommand;
import com.gaebaljip.exceed.application.port.in.meal.EatMealUsecase;
import com.gaebaljip.exceed.application.port.in.meal.UploadImageCommand;
import com.gaebaljip.exceed.application.port.in.meal.UploadImageUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.docs.meal.EatMealExceptionDocs;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[식사 등록]")
public class EatMealController {

    private final EatMealUsecase eatMealUsecase;
    private final UploadImageUsecase uploadImageUsecase;

    /** 식사 등록 API */
    @Operation(summary = "식사 등록", description = "[대선] 식사를 등록한다.")
    @PostMapping("/meal")
    @ApiErrorExceptionsExample(EatMealExceptionDocs.class)
    public ApiResponse<CustomBody<EatMealResponse>> eatMeal(
            @Valid @RequestBody EatMealRequest request,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        Long mealId = eatMealUsecase.execute(EatMealCommand.of(request, memberId));
        String presignedUrl =
                uploadImageUsecase.execute(
                        UploadImageCommand.of(memberId, mealId, request.fileName()));
        return ApiResponseGenerator.success(new EatMealResponse(presignedUrl), HttpStatus.CREATED);
    }
}
