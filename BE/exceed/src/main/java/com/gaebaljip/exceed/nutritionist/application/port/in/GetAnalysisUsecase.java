package com.gaebaljip.exceed.nutritionist.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;

@Component
public interface GetAnalysisUsecase {
    GetAnalysisResponse execute(GetAnalysisRequest getAnalysisRequest);
}
