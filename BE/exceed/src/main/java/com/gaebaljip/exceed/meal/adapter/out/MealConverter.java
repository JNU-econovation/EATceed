package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.food.adapter.out.FoodConverter;
import com.gaebaljip.exceed.meal.domain.Meal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealConverter {

    private final FoodConverter foodConverter;

    public Meal toMeal(MealEntity mealEntity) {
        return Meal.builder()
                .id(mealEntity.getId())
                .mealType(mealEntity.getMealType())
                .mealDateTime(mealEntity.getCreatedDate())
                .foods(
                        mealEntity.getMealFoodEntity().stream()
                                .map(
                                        mealFoodEntity ->
                                                foodConverter.toModel(
                                                        mealFoodEntity.getFoodEntity()))
                                .toList())
                .build();
    }

    public List<Meal> toMeals(List<MealEntity> mealEntities) {
        return mealEntities.stream().map(this::toMeal).toList();
    }
}
