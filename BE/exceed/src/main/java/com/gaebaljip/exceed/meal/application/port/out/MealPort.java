package com.gaebaljip.exceed.meal.application.port.out;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.adapter.out.MealEntity;

@Component
public interface MealPort {
    MealEntity command(MealEntity mealEntity);
}
