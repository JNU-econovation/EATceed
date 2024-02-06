package com.gaebaljip.exceed.dto.response;

import com.gaebaljip.exceed.meal.domain.MealType;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public record GetMeal(
        CurrentMeal currentMeal,
        List<DailyMeal> dailyMeals
) {

    @Builder
    public GetMeal {
    }
}
