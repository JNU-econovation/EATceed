package com.gaebaljip.exceed.achieve.adapter.in;

import com.gaebaljip.exceed.achieve.application.port.in.GetMonthMealUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.response.GetAchieveListResponse;
import com.gaebaljip.exceed.meal.application.GetCurrentMealService;
import com.gaebaljip.exceed.member.application.GetTargetMealService;
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
public class GetAchieveController {

    private final GetMonthMealUsecase getMonthMealUsecase;
    private final GetCurrentMealService getCurrentMealService;
    private final GetTargetMealService getTargetMealService;

    @GetMapping("/achieve/{date}")
    public ApiResponse<?> getAchieves(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        GetAchieveListResponse achieveListResponse = getMonthMealUsecase.execute(1L, date);
        return ApiResponseGenerator.success(achieveListResponse, HttpStatus.OK);
    }

}
