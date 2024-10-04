package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

@Component
public interface GetDailyAnalysisUsecase {
    AllAnalysisDTO executeToAllNutrition(GetDailyAnalysisCommand request);

    CalorieAnalysisDTO executeToCalorie(GetDailyAnalysisCommand request);
}
