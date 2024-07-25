package com.gaebaljip.exceed.adapter.in.nutritionist;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetCalorieAnalysisResponse;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetCalorieAnalysisUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.docs.nutritionist.GetAnalysisExceptionDocs;
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
@Tag(name = "[분석 조회]")
public class GetAnalysisController {

    private final GetCalorieAnalysisUsecase getCalorieAnalysisUsecase;

    @Operation(summary = "월별 식사 정보 분석", description = "월별 식사 정보를 분석한다.")
    @GetMapping("/achieve/{date}")
    @ApiErrorExceptionsExample(GetAnalysisExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetCalorieAnalysisResponse>> getAnalysis(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        LocalDateTime localDateTime = date.atStartOfDay();
        GetCalorieAnalysisResponse achieveListResponse =
                getCalorieAnalysisUsecase.execute(
                        new GetCalorieAnalysisRequest(memberId, localDateTime));
        return ApiResponseGenerator.success(achieveListResponse, HttpStatus.OK);
    }
}
