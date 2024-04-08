package com.gaebaljip.exceed.nutritionist.application.port.out;

import java.util.List;

import com.gaebaljip.exceed.dto.request.MonthlyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;

public interface MonthlyMealPort {
    List<Meal> query(MonthlyMeal monthlyMeal);
}
