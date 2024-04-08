package com.gaebaljip.exceed.dto.response;

import java.util.List;

import lombok.Builder;

public record SpecificMeal(CurrentMeal currentMeal, List<MealRecord> mealRecords) {

    @Builder
    public SpecificMeal {}
}
