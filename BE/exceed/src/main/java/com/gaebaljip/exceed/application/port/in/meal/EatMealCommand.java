package com.gaebaljip.exceed.application.port.in.meal;

import java.util.List;

import com.gaebaljip.exceed.application.domain.meal.MealType;
import com.gaebaljip.exceed.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.dto.request.EatMealRequest;

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
