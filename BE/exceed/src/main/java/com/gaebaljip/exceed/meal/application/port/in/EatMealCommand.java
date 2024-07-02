package com.gaebaljip.exceed.meal.application.port.in;

import java.util.List;

import com.gaebaljip.exceed.dto.request.EatMealFood;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

@Builder
public record EatMealCommand(List<EatMealFood> eatMealFoods, Long memberId, MealType mealType) {

    public static EatMealCommand of(EatMealRequest request, Long memberId) {
        return EatMealCommand.builder()
                .eatMealFoods(request.eatMealFoods())
                .mealType(MealType.valueOf(request.mealType()))
                .memberId(memberId)
                .build();
    }
}
