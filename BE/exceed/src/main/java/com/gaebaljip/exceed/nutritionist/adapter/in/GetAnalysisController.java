package com.gaebaljip.exceed.nutritionist.adapter.in;

import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.nutritionist.application.port.in.GetAnalysisUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
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
public class GetAnalysisController {

    private final GetAnalysisUsecase getAnalysisUsecase;

    @GetMapping("/achieve/{date}")
    public ApiResponse<ApiResponse.CustomBody<GetAnalysisResponse>> getAnalysis(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @AuthenticationMemberId Long memberId) {
        GetAnalysisResponse achieveListResponse = getAnalysisUsecase.execute(new GetAnalysisRequest(memberId, date));
        return ApiResponseGenerator.success(achieveListResponse, HttpStatus.OK);
    }
}
