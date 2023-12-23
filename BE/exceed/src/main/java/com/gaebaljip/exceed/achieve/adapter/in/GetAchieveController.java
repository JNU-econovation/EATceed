package com.gaebaljip.exceed.achieve.adapter.in;

import com.gaebaljip.exceed.achieve.application.port.in.GetAchieveUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.response.GetAchieveListResponse;
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

    private final GetAchieveUsecase getAchieveUsecase;

    @GetMapping("/achieve/{date}")
    public ApiResponse<ApiResponse.CustomBody<GetAchieveListResponse>> getAchieves(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        GetAchieveListResponse achieveListResponse = getAchieveUsecase.execute(1L, date);
        return ApiResponseGenerator.success(achieveListResponse, HttpStatus.OK);
    }
}
