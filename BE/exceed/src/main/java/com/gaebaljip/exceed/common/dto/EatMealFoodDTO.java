package com.gaebaljip.exceed.common.dto;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

import lombok.Builder;

public record EatMealFoodDTO(
        @NotNull(message = "음식PK를 " + ValidationMessage.NOT_NULL) Long foodId,
        Double multiple,
        Integer g) {

    @Builder
    public EatMealFoodDTO {}
}
