package com.gaebaljip.exceed.nutritionist.application.port.in;

import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
import org.springframework.stereotype.Component;

@Component
public interface GetAnalysisUsecase {
    GetAnalysisResponse execute(GetAnalysisRequest getAnalysisRequest);
}
