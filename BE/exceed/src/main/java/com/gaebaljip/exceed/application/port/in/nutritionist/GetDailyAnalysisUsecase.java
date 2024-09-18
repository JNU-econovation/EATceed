package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetDailyAnalysisCommand;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;

@Component
public interface GetDailyAnalysisUsecase {
    AllAnalysisDTO executeToAllNutrition(GetDailyAnalysisCommand request);
}
