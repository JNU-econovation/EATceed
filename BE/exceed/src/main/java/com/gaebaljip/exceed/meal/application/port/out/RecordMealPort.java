package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.meal.adapter.out.MealEntity;
import org.springframework.stereotype.Component;

@Component
public interface RecordMealPort {
    MealEntity query(MealEntity mealEntity);
}
