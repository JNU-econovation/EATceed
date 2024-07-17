package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record EatMealFoodDTO(Long foodId, Double multiple, Integer g) {

    @Builder
    public EatMealFoodDTO {}
}
