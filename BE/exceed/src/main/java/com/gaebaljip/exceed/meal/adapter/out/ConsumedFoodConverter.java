package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.food.adapter.out.FoodConverter;
import com.gaebaljip.exceed.meal.domain.ConsumedFood;
import com.gaebaljip.exceed.meal.domain.Unit;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsumedFoodConverter {

    private final FoodConverter foodConverter;

    public ConsumedFood toConsumedFood(MealFoodEntity mealFoodEntity) {
        return ConsumedFood.builder()
                .food(foodConverter.toModel(mealFoodEntity.getFoodEntity()))
                .unit(
                        Unit.builder()
                                .g(mealFoodEntity.getG())
                                .multiple(mealFoodEntity.getMultiple())
                                .build())
                .build();
    }

    public List<ConsumedFood> toConsumedFoods(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodEntities.stream().map(this::toConsumedFood).toList();
    }
}
