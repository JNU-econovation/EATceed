package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

import java.util.List;

public record GetMealFoodResponse(
        GetMealResponse getMealResponse,
        List<DailyMeal> dailyMeals
) {
    @Builder
    public GetMealFoodResponse {
    }
}
