package com.gaebaljip.exceed.dto;

import com.gaebaljip.exceed.meal.domain.MealType;
import lombok.Builder;

import java.time.LocalTime;

public record GetMeal(
        LocalTime time,
        MealType mealType
) {

    @Builder
    public GetMeal {
    }
}
