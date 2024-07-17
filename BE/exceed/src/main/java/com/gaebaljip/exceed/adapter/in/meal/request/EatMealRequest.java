package com.gaebaljip.exceed.adapter.in.meal.request;

import java.util.List;

import com.gaebaljip.exceed.application.domain.meal.MealType;
import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;

import lombok.Builder;

public record EatMealRequest(
        List<EatMealFoodDTO> eatMealFoodDTOS,
        @Enum(enumClass = MealType.class) String mealType,
        String fileName) {
    @Builder
    public EatMealRequest {}
}
