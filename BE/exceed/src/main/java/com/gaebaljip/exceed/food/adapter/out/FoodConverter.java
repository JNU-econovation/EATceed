package com.gaebaljip.exceed.food.adapter.out;

import com.gaebaljip.exceed.food.domain.FoodModel;;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodConverter {

    public FoodModel toModel(FoodEntity foodEntity) {
        return FoodModel.builder()
                .id(foodEntity.getId())
                .name(foodEntity.getName())
                .calorie(foodEntity.getCalorie())
                .carbohydrate(foodEntity.getCarbohydrate())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .build();
    }

    public List<FoodModel> toModels(List<FoodEntity> foodEntities) {
        return foodEntities.stream()
                .map(this::toModel)
                .toList();
    }
}
