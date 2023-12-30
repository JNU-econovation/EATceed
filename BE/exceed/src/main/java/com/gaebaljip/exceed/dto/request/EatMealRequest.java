package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.meal.domain.MealType;
import lombok.Builder;

import java.util.List;

public record EatMealRequest(
        Double multiple,
        List<Long> foodIds,
        @Enum(enumClass = MealType.class)
        String mealType
) {
    @Builder
    public EatMealRequest {
    }
}
