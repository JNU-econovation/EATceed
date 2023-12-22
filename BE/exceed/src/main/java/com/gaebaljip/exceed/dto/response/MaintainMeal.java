package com.gaebaljip.exceed.dto.response;

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
