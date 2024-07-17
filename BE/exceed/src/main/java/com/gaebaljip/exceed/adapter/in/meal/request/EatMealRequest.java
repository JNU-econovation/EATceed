package com.gaebaljip.exceed.adapter.in.meal.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.gaebaljip.exceed.application.domain.meal.MealType;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;

import lombok.Builder;

public record EatMealRequest(
        @Valid List<EatMealFoodDTO> eatMealFoodDTOS,
        @Enum(enumClass = MealType.class) String mealType,
        @NotBlank(message = "파일명을 " + ValidationMessage.NOT_BLANK) String fileName) {
    @Builder
    public EatMealRequest {}
}
