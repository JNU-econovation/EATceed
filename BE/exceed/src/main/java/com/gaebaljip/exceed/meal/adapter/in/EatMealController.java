package com.gaebaljip.exceed.meal.adapter.in;

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
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.dto.response.EatMealResponse;
import com.gaebaljip.exceed.dto.response.UploadImage;
import com.gaebaljip.exceed.meal.application.port.in.EatMealCommand;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;
import com.gaebaljip.exceed.meal.docs.EatMealExceptionDocs;
import com.gaebaljip.exceed.meal.domain.MealType;

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
    @Operation(summary = "식사 등록", description = "식사를 등록한다.")
    @PostMapping("/meal")
    @ApiErrorExceptionsExample(EatMealExceptionDocs.class)
    public ApiResponse<CustomBody<EatMealResponse>> eatMeal(
            @Valid @RequestBody EatMealRequest request,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        EatMealCommand eatMealCommand =
                EatMealCommand.builder()
                        .foodIds(request.foodIds())
                        .mealType(MealType.valueOf(request.mealType()))
                        .multiple(request.multiple())
                        .memberId(memberId)
                        .build();
        Long mealId = eatMealUsecase.execute(eatMealCommand);
        UploadImage uploadImage =
                UploadImage.builder()
                        .mealId(mealId)
                        .memberId(memberId)
                        .fileName(request.fileName())
                        .build();
        String presignedUrl = uploadImageUsecase.execute(uploadImage);
        return ApiResponseGenerator.success(new EatMealResponse(presignedUrl), HttpStatus.CREATED);
    }
}
