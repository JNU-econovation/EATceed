package com.gaebaljip.exceed.dto.response;

import java.time.LocalTime;
import java.util.List;

import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

public record MealRecord(LocalTime time, MealType mealType, String imageUri, List<Food> foods) {

    @Builder
    public MealRecord {}
}
