package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.common.dto.response.GetCalorieAnalysisResponse;

@Component
public interface GetCalorieAnalysisUsecase {
    GetCalorieAnalysisResponse execute(GetCalorieAnalysisRequest getCalorieAnalysisRequest);
}
