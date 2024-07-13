package com.gaebaljip.exceed.dto.response;

import java.util.List;

import com.gaebaljip.exceed.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.dto.MealRecordDTO;

import lombok.Builder;

public record GetMealFoodResponse(
        GetMealResponse getMealResponse,
        List<MealRecordDTO> mealRecordDTOS,
        AllAnalysisDTO allAnalysisDTO) {
    @Builder
    public GetMealFoodResponse {}
}
