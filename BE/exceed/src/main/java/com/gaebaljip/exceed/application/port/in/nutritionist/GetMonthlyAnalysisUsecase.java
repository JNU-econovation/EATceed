package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetMonthlyAnalysisCommand;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;

@Component
public interface GetMonthlyAnalysisUsecase {
    GetMonthlyAnalysisResponse execute(GetMonthlyAnalysisCommand command);
}
