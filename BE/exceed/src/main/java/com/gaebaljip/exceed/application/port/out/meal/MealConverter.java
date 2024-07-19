package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.jpa.meal.ConsumedFoodConverter;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.common.annotation.Timer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealConverter {

    private final ConsumedFoodConverter converter;

    public Meal toMeal(MealEntity mealEntity) {
        return Meal.builder()
                .id(mealEntity.getId())
                .mealType(mealEntity.getMealType())
                .mealDateTime(mealEntity.getCreatedDate())
                .consumedFoods(converter.toConsumedFoods(mealEntity.getMealFoodEntity()))
                .build();
    }

    @Timer
    public List<Meal> toMeals(List<MealEntity> mealEntities) {
        return mealEntities.stream().map(this::toMeal).toList();
    }
}
