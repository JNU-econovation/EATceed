package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.food.adapter.out.FoodConverter;
import com.gaebaljip.exceed.meal.domain.MealModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealConverter {

    private final FoodConverter FoodConverter;

    public MealModel toModel(MealEntity mealEntity) {
        return MealModel.builder()
                .mealType(mealEntity.getMealType())
                .foodModels(mealEntity.getMealFoodEntity().stream()
                        .map(mealFoodEntity -> FoodConverter.toModel(mealFoodEntity.getFoodEntity()))
                        .toList())
                .build();
    }

    public List<MealModel> toModels(List<MealEntity> mealEntities) {
        return mealEntities.stream()
                .map(this::toModel)
                .toList();
    }
}
