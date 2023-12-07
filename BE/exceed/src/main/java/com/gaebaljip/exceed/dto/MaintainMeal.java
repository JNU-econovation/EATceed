package com.gaebaljip.exceed.dto;

import lombok.Builder;

public record MaintainMeal(
        Double maintainCalorie,
        Double maintainCarbohydrate,
        Double maintainProtein,
        Double maintainFat) {

    @Builder
    public MaintainMeal {
    }
}
