package com.gaebaljip.exceed.meal.application.port.in;

import java.util.List;

import com.gaebaljip.exceed.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

@Builder
public record EatMealCommand(
        List<EatMealFoodDTO> eatMealFoodDTOS, Long memberId, MealType mealType) {

    public static EatMealCommand of(EatMealRequest request, Long memberId) {
        return EatMealCommand.builder()
                .eatMealFoodDTOS(request.eatMealFoodDTOS())
                .mealType(MealType.valueOf(request.mealType()))
                .memberId(memberId)
                .build();
    }
}
