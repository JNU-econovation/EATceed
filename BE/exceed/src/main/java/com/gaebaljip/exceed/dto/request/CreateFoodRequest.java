package com.gaebaljip.exceed.dto.request;

import lombok.Builder;

public record CreateFoodRequest(
        String name,
        Double sugars,
        Double dietaryFiber,
        Double sodium,
        Double calorie,
        Double carbohydrate,
        Double protein,
        Double fat,
        Double servingSize) {
    @Builder
    public CreateFoodRequest {}
}
