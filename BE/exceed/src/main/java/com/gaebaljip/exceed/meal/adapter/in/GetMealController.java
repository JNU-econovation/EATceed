package com.gaebaljip.exceed.meal.adapter.in;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;
import com.gaebaljip.exceed.dto.response.*;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.meal.docs.GetMealExceptionDocs;
import com.gaebaljip.exceed.meal.docs.GetMealFoodExceptionDocs;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[식사 조회]")
public class GetMealController {

    private final GetMaintainMealUsecase getMaintainMealUsecase;
    private final GetTargetMealUsecase getTargetMealUsecase;
    private final GetCurrentMealQuery getCurrentMealQuery;
    private final GetSpecificMealQuery getSpecificMealQuery;

    /** 오늘 먹은 식사 정보(단,탄,지 및 칼로리) 조회 */
    @Operation(summary = "오늘 먹은 식사 정보 조회", description = "오늘 먹은 식사 정보(단,탄,지 및 칼로리)를 조회한다.")
    @GetMapping("/meal")
    @ApiErrorExceptionsExample(GetMealExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetMealResponse>> getMeal(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        MaintainMeal maintainMeal = getMaintainMealUsecase.execute(memberId);
        TargetMeal targetMeal = getTargetMealUsecase.execute(memberId);
        CurrentMeal currentMeal = getCurrentMealQuery.execute(memberId);
        return ApiResponseGenerator.success(
                new GetMealResponse(maintainMeal, targetMeal, currentMeal), HttpStatus.OK);
    }

    /** 특정 날짜의 식사 정보(단,탄,지 및 칼로지) 조회 */
    @Operation(summary = "특정 날짜의 식사 정보 조회", description = "특정 날짜의 식사 정보(단,탄,지 및 칼로리)를 조회한다.")
    @GetMapping("/meal/{date}")
    @ApiErrorExceptionsExample(GetMealFoodExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetMealFoodResponse>> getMealFood(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        MaintainMeal maintainMeal = getMaintainMealUsecase.execute(memberId);
        TargetMeal targetMeal = getTargetMealUsecase.execute(memberId);
        SpecificMeal specificMeal = getSpecificMealQuery.execute(memberId, date);
        GetMealResponse getMealResponse =
                new GetMealResponse(maintainMeal, targetMeal, specificMeal.currentMeal());
        return ApiResponseGenerator.success(
                new GetMealFoodResponse(getMealResponse, specificMeal.mealRecords()),
                HttpStatus.OK);
    }
}
