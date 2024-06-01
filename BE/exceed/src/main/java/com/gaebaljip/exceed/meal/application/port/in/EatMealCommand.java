package com.gaebaljip.exceed.meal.application.port.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

public record EatMealCommand(
        @NotNull(message = "multiple must not be null") Double multiple,
        List<Long> foodIds,
        Long memberId,
        MealType mealType) {

    @Builder
    public EatMealCommand {}
}
