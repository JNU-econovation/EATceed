package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.dto.request.DailyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DailyMealPort {
    List<Meal> query(DailyMeal dailyMeal);
}
