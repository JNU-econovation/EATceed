package com.gaebaljip.exceed.dto.response;

import java.util.List;

import lombok.Builder;

public record GetMealFoodResponse(
        GetMealResponse getMealResponse, List<MealRecordDTO> mealRecordDTOS) {
    @Builder
    public GetMealFoodResponse {}
}
