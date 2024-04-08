package com.gaebaljip.exceed.nutritionist.adapter.in;

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
import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
import com.gaebaljip.exceed.nutritionist.application.port.in.GetAnalysisUsecase;
import com.gaebaljip.exceed.nutritionist.docs.GetAnalysisExceptionDocs;

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

    private final GetAnalysisUsecase getAnalysisUsecase;

    @Operation(summary = "특정 날짜의 분석 조회", description = "특정 날짜의 분석을 조회한다.")
    @GetMapping("/achieve/{date}")
    @ApiErrorExceptionsExample(GetAnalysisExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<GetAnalysisResponse>> getAnalysis(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        GetAnalysisResponse achieveListResponse =
                getAnalysisUsecase.execute(new GetAnalysisRequest(memberId, date));
        return ApiResponseGenerator.success(achieveListResponse, HttpStatus.OK);
    }
}
