package com.gaebaljip.exceed.nutritionist.application.port.out;

import com.gaebaljip.exceed.dto.request.MonthlyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;

import java.util.List;

public interface MonthlyMealPort {
    List<Meal> query(MonthlyMeal monthlyMeal);
}
