package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MealFoodPort {
    List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities);
}
