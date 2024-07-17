package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.request.GetAllAnalysisRequest;

@Component
public interface GetAllAnalysisUsecase {
    AllAnalysisDTO execute(GetAllAnalysisRequest request);
}
