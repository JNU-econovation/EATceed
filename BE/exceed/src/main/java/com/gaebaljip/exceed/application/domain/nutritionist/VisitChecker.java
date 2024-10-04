package com.gaebaljip.exceed.application.domain.nutritionist;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.common.annotation.Timer;

/**
 * 특정 기간의 식사 여부를 확인
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class VisitChecker {
    private Map<LocalDate, DailyMealFoods> mealFoodsByDate;

    public VisitChecker(Map<LocalDate, DailyMealFoods> mealFoodsByDate) {
        this.mealFoodsByDate = mealFoodsByDate;
    }

    @Timer
    public Map<LocalDate, Boolean> check() {
        Map<LocalDate, Boolean> dateVisitStatus = new HashMap<>();
        mealFoodsByDate.keySet().stream()
                .forEach(
                        date -> {
                            DailyMealFoods dailyMealFoods = mealFoodsByDate.get(date);
                            dateVisitStatus.put(date, !dailyMealFoods.isEmptyMeals());
                        });
        return dateVisitStatus;
    }
}
