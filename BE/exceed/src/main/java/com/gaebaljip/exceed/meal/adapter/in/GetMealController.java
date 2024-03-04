package com.gaebaljip.exceed.meal.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.response.*;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class GetMealController {

    private final GetMaintainMealUsecase getMaintainMealUsecase;
    private final GetTargetMealUsecase getTargetMealUsecase;
    private final GetCurrentMealQuery getCurrentMealQuery;
    private final GetSpecificMealQuery getSpecificMealQuery;

    /**
     * 오늘 먹은 식사 정보(단,탄,지 및 칼로리) 조회
     */

    @GetMapping("/meal")
    public ApiResponse<ApiResponse.CustomBody<GetMealResponse>> getMeal(@AuthenticationMemberId Long memberId) {
        MaintainMeal maintainMeal = getMaintainMealUsecase.execute(memberId);
        TargetMeal targetMeal = getTargetMealUsecase.execute(memberId);
        CurrentMeal currentMeal = getCurrentMealQuery.execute(memberId);
        return ApiResponseGenerator.success(
                new GetMealResponse(maintainMeal, targetMeal, currentMeal),
                HttpStatus.OK);
    }

    /**
     * 특정 날짜의 식사 정보(단,탄,지 및 칼로지) 조회
     */
    @GetMapping("/meal/{date}")
    public ApiResponse<ApiResponse.CustomBody<GetMealFoodResponse>> getMealFood(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @AuthenticationMemberId Long memberId) {
        MaintainMeal maintainMeal = getMaintainMealUsecase.execute(memberId);
        TargetMeal targetMeal = getTargetMealUsecase.execute(memberId);
        SpecificMeal specificMeal = getSpecificMealQuery.execute(memberId, date);
        GetMealResponse getMealResponse = new GetMealResponse(maintainMeal, targetMeal, specificMeal.currentMeal());
        return ApiResponseGenerator.success(new GetMealFoodResponse(getMealResponse, specificMeal.mealRecords()), HttpStatus.OK);
    }
}
