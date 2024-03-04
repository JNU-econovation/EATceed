package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

import java.util.List;

public record GetMealFoodResponse(
        GetMealResponse getMealResponse,
        List<MealRecord> mealRecords
) {
    @Builder
    public GetMealFoodResponse {
    }
}
