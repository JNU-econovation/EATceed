package com.gaebaljip.exceed.dto;

import lombok.Builder;

public record TargetMeal(
        Double targetCalorie,
        Double targetCarbohydrate,
        Double targetProtein,
        Double targetFat
) {
        @Builder
        public TargetMeal {
        }
}
