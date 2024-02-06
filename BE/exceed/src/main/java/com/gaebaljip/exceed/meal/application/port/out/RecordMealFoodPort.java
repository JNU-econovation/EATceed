package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecordMealFoodPort {
    List<MealFoodEntity> query(List<MealFoodEntity> mealFoodEntities);
}
