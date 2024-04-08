package com.gaebaljip.exceed.meal.application.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;

@Component
public interface MealFoodPort {
    List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities);
}
