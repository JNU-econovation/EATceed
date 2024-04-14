package com.gaebaljip.exceed.dto.response;

import java.util.List;

import lombok.Builder;

public record GetMealFoodResponse(GetMealResponse getMealResponse, List<MealRecord> mealRecords) {
    @Builder
    public GetMealFoodResponse {}
}
