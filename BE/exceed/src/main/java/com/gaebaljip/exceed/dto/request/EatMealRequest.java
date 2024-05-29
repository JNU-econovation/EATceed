package com.gaebaljip.exceed.dto.request;

import java.util.List;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

public record EatMealRequest(
        List<EatMealFood> eatMealFoods,
        @Enum(enumClass = MealType.class) String mealType,
        String fileName) {
    @Builder
    public EatMealRequest {}
}
