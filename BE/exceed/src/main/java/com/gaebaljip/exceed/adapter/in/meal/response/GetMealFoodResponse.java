package com.gaebaljip.exceed.adapter.in.meal.response;

import java.util.List;

import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.MealRecordDTO;

import lombok.Builder;

public record GetMealFoodResponse(
        GetMealResponse getMealResponse,
        List<MealRecordDTO> mealRecordDTOS,
        AllAnalysisDTO allAnalysisDTO) {
    @Builder
    public GetMealFoodResponse {}
}
