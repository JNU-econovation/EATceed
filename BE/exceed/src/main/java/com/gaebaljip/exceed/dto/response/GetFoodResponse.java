package com.gaebaljip.exceed.dto.response;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;

public record GetFoodResponse(
        Long foodId,
        Double sugars,
        Double dietaryFiber,
        Double sodium,
        String name,
        Double calorie,
        Double carbohydrate,
        Double protein,
        Double fat,
        Double servingSize) {
    public static GetFoodResponse of(FoodEntity foodEntity) {
        return new GetFoodResponse(foodEntity.getId(), foodEntity.getSugars(), foodEntity.getDietaryFiber(),
                foodEntity.getSodium(), foodEntity.getName(), foodEntity.getCalorie(), foodEntity.getCarbohydrate(),
                foodEntity.getProtein(), foodEntity.getFat(), foodEntity.getServingSize());
    }
}
