package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.dto.DaysMealRecordDTO;

@Component
public interface DailyMealPort {
    DailyMealFoods queryMealFoodsForDay(DailyMealDTO dailyMealDTO);

    DaysMealRecordDTO queryMealFoodsForDays(
            com.gaebaljip.exceed.common.dto.DaysMealDTO daysMealDTO);

    DailyMealFoods queryMealFoods(List<Long> mealIds);

    List<MealEntity> queryMeals(DailyMealDTO dailyMealDTO);
}
