package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record CurrentMeal(
        Double currentCalorie,
        Double currentCarbohydrate,
        Double currentProtein,
        Double currentFat) {

    @Builder
    public CurrentMeal {
    }

}
