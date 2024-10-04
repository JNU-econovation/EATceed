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
import com.gaebaljip.exceed.adapter.in.member.response.GetMealAndWeightResponse;
import com.gaebaljip.exceed.adapter.in.member.response.GetWeightDTO;
import com.gaebaljip.exceed.application.port.in.meal.GetCurrentMealQuery;
import com.gaebaljip.exceed.application.port.in.meal.GetSpecificMealQuery;
import com.gaebaljip.exceed.application.port.in.meal.ValidateBeforeSignUpDateUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetMaintainNutritionUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetTargetNutritionUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetWeightUseCase;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisCommand;
import com.gaebaljip.exceed.application.service.nutritionist.GetDailyAnalysisService;
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

    private final GetMaintainNutritionUsecase getMaintainNutritionUsecase;
    private final GetTargetNutritionUsecase getTargetNutritionUsecase;
    private final GetCurrentMealQuery getCurrentMealQuery;
    private final GetSpecificMealQuery getSpecificMealQuery;
    private final GetDailyAnalysisService getDailyAnalysisService;
    private final ValidateBeforeSignUpDateUsecase validateBeforeSignUpDateUsecase;
    private final GetWeightUseCase getWeightUseCase;

    /** 오늘 먹은 식사 정보(단,탄,지 및 칼로리) 조회 */
    @Operation(summary = "오늘 먹은 식사 정보 조회", description = "오늘 먹은 식사 정보(단,탄,지 및 칼로리)를 조회한다.")
    @GetMapping("/meal")
    @ApiErrorExceptionsExample(GetMealExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetMealAndWeightResponse>> getMeal(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        MaintainMealDTO maintainMealDTO = getMaintainNutritionUsecase.execute(memberId);
        TargetMealDTO targetMealDTO = getTargetNutritionUsecase.execute(memberId);
        GetWeightDTO getWeightDTO = getWeightUseCase.execute(memberId);
        CurrentMealDTO currentMealDTO = getCurrentMealQuery.execute(memberId);
        return ApiResponseGenerator.success(
                GetMealAndWeightResponse.of(
                        maintainMealDTO, targetMealDTO, getWeightDTO, currentMealDTO),
                HttpStatus.OK);
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
        MaintainMealDTO maintainMealDTO =
                getMaintainNutritionUsecase.execute(memberId, localDateTime);
        TargetMealDTO targetMealDTO = getTargetNutritionUsecase.execute(memberId, localDateTime);
        SpecificMealDTO specificMealDTO = getSpecificMealQuery.execute(memberId, localDateTime);
        AllAnalysisDTO allAnalysisDTO =
                getDailyAnalysisService.executeToAllNutrition(
                        GetDailyAnalysisCommand.of(memberId, localDateTime));
        return ApiResponseGenerator.success(
                new GetMealFoodResponse(
                        GetMealResponse.of(
                                maintainMealDTO, targetMealDTO, specificMealDTO.currentMealDTO()),
                        specificMealDTO.mealRecordDTOS(),
                        allAnalysisDTO),
                HttpStatus.OK);
    }
}
