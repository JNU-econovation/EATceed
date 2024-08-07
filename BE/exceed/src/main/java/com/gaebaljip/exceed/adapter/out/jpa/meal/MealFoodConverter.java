package com.gaebaljip.exceed.adapter.out.jpa.meal;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.common.annotation.Timer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealFoodConverter {

    private final ConsumedFoodConverter converter;

    @Timer
    public List<Meal> toMeals(List<MealFoodEntity> mealFoodEntities) {
        Map<MealEntity, List<MealFoodEntity>> mealEntityListMap =
                mealFoodEntities.stream().collect(groupingBy(MealFoodEntity::getMealEntity));
        return mealEntityListMap.keySet().stream()
                .map(mealEntity -> toMeal(mealEntityListMap.get(mealEntity), mealEntity.getId()))
                .toList();
    }

    private Meal toMeal(List<MealFoodEntity> mealFoodEntities, Long mealId) {
        return Meal.builder()
                .id(mealId)
                .mealDateTime(mealFoodEntities.get(0).getCreatedDate())
                .consumedFoods(converter.toConsumedFoods(mealFoodEntities))
                .build();
    }
}
