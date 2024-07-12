package com.gaebaljip.exceed.adapter.out.jpa.food;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.food.Food;

@Component
public class FoodConverter {

    public Food toModel(FoodEntity foodEntity) {
        return Food.builder()
                .id(foodEntity.getId())
                .name(foodEntity.getName())
                .calorie(foodEntity.getCalorie())
                .carbohydrate(foodEntity.getCarbohydrate())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .build();
    }

    public List<Food> toModels(List<FoodEntity> foodEntities) {
        return foodEntities.stream().map(this::toModel).toList();
    }
}
