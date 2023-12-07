package com.gaebaljip.exceed.meal.port.out;

import com.gaebaljip.exceed.meal.domain.MealModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface LoadMealPort {
    MealModel loadMeal(Long memberId, LocalDate date);
}
