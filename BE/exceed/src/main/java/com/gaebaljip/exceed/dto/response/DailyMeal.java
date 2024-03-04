package com.gaebaljip.exceed.dto.response;

import com.gaebaljip.exceed.meal.domain.MealType;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public record DailyMeal(
        LocalTime time,
        MealType mealType,
        String imageUri,
        List<Food> foods
) {

    @Builder
    public DailyMeal {
    }
}
