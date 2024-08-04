package com.gaebaljip.exceed.adapter.in.meal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.meal.response.GetMealFoodResponse;
import com.gaebaljip.exceed.adapter.in.meal.response.GetMealResponse;
import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetAllAnalysisRequest;
import com.gaebaljip.exceed.application.port.in.meal.GetCurrentMealQuery;
import com.gaebaljip.exceed.application.port.in.meal.GetSpecificMealQuery;
import com.gaebaljip.exceed.application.port.in.meal.ValidateBeforeSignUpDateUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetMaintainMealUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetTargetMealUsecase;
import com.gaebaljip.exceed.application.service.nutritionist.GetAllCalorieAnalysisService;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.docs.meal.GetMealExceptionDocs;
import com.gaebaljip.exceed.common.docs.meal.GetMealFoodExceptionDocs;
import com.gaebaljip.exceed.common.dto.*;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;

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
    private final GetAllCalorieAnalysisService getAllCalorieAnalysisService;
    private final ValidateBeforeSignUpDateUsecase validateBeforeSignUpDateUsecase;

    /** 오늘 먹은 식사 정보(단,탄,지 및 칼로리) 조회 */
    @Operation(summary = "오늘 먹은 식사 정보 조회", description = "오늘 먹은 식사 정보(단,탄,지 및 칼로리)를 조회한다.")
    @GetMapping("/meal")
    @ApiErrorExceptionsExample(GetMealExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetMealResponse>> getMeal(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        MaintainMealDTO maintainMealDTO = getMaintainMealUsecase.execute(memberId);
        TargetMealDTO targetMealDTO = getTargetMealUsecase.execute(memberId);
        CurrentMealDTO currentMealDTO = getCurrentMealQuery.execute(memberId);
        return ApiResponseGenerator.success(
                new GetMealResponse(maintainMealDTO, targetMealDTO, currentMealDTO), HttpStatus.OK);
    }

    /** 특정 날짜의 식사 정보(단,탄,지 및 칼로지) 조회 */
    @Operation(summary = "특정 날짜의 식사 정보 조회", description = "특정 날짜의 식사 정보(단,탄,지 및 칼로리)를 조회한다.")
    @GetMapping("/meal/{date}")
    @ApiErrorExceptionsExample(GetMealFoodExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetMealFoodResponse>> getMealFood(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        LocalDateTime localDateTime = date.atStartOfDay();
        validateBeforeSignUpDateUsecase.execute(memberId, localDateTime);
        MaintainMealDTO maintainMealDTO = getMaintainMealUsecase.execute(memberId, localDateTime);
        TargetMealDTO targetMealDTO = getTargetMealUsecase.execute(memberId, localDateTime);
        SpecificMealDTO specificMealDTO = getSpecificMealQuery.execute(memberId, localDateTime);

        AllAnalysisDTO allAnalysisDTO =
                getAllCalorieAnalysisService.execute(
                        GetAllAnalysisRequest.of(memberId, localDateTime));
        return ApiResponseGenerator.success(
                new GetMealFoodResponse(
                        GetMealResponse.of(
                                maintainMealDTO, targetMealDTO, specificMealDTO.currentMealDTO()),
                        specificMealDTO.mealRecordDTOS(),
                        allAnalysisDTO),
                HttpStatus.OK);
    }
}
