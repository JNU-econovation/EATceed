package com.gaebaljip.exceed.dto;

import lombok.Builder;

public record EatMealFoodDTO(Long foodId, Double multiple, Integer g) {

    @Builder
    public EatMealFoodDTO {}
}
