package com.gaebaljip.exceed.dto.request;

import lombok.Builder;

public record EatMealFood(Long foodId, Double multiple, Integer g) {

    @Builder
    public EatMealFood {}
}
