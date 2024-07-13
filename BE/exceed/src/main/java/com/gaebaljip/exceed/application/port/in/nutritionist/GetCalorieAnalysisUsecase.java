package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.dto.response.GetCalorieAnalysisResponse;

@Component
public interface GetCalorieAnalysisUsecase {
    GetCalorieAnalysisResponse execute(GetCalorieAnalysisRequest getCalorieAnalysisRequest);
}
