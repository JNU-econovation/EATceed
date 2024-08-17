package com.gaebaljip.exceed.adapter.out.jpa.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.jpa.food.FoodConverter;
import com.gaebaljip.exceed.application.domain.meal.ConsumedFood;
import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.meal.Unit;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsumedFoodConverter {

    private final FoodConverter foodConverter;

    public ConsumedFood toConsumedFood(MealFoodEntity mealFoodEntity) {
        return ConsumedFood.builder()
                .food(foodConverter.toModel(mealFoodEntity.getFoodEntity()))
                .unit(
                        new Unit(
                                mealFoodEntity.getUnit().getG(),
                                mealFoodEntity.getUnit().getMultiple()))
                .build();
    }

    public List<ConsumedFood> toConsumedFoods(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodEntities.stream().map(this::toConsumedFood).toList();
    }
}
