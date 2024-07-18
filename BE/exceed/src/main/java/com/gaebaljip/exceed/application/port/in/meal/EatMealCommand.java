package com.gaebaljip.exceed.application.port.in.meal;

import java.util.List;

import com.gaebaljip.exceed.adapter.in.meal.request.EatMealRequest;
import com.gaebaljip.exceed.application.domain.meal.MealType;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.common.exception.meal.InvalidGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleAndGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;

import lombok.Builder;

@Builder
public record EatMealCommand(
        List<EatMealFoodDTO> eatMealFoodDTOS, Long memberId, MealType mealType) {

    public EatMealCommand {
        eatMealFoodDTOS.forEach(
                dto -> {
                    if ((dto.multiple() != null && dto.g() != null)
                            || (dto.multiple() == null && dto.g() == null)) {
                        throw InvalidMultipleAndGException.EXCEPTION;
                    }
                    if (dto.multiple() != null) {
                        validateMultiple(dto.multiple());
                    }
                    if (dto.g() != null) {
                        validateG(dto.g());
                    }
                });
    }

    private void validateMultiple(double multiple) {
        if (multiple <= 0 || multiple > 100) {
            throw InvalidMultipleException.EXECPTION;
        }
    }

    private void validateG(int g) {
        if (g <= 0) {
            throw InvalidGException.EXCEPTION;
        }
    }

    public static EatMealCommand of(EatMealRequest request, Long memberId) {
        return EatMealCommand.builder()
                .eatMealFoodDTOS(request.eatMealFoodDTOS())
                .mealType(MealType.valueOf(request.mealType()))
                .memberId(memberId)
                .build();
    }
}
