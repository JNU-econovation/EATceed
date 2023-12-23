package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.meal.domain.MealModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface LoadDailyMealPort {
    List<MealModel> queryMealsForDate(Long memberId, LocalDate date);
}
