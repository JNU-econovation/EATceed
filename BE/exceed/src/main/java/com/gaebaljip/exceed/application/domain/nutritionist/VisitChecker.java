package com.gaebaljip.exceed.application.domain.nutritionist;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.common.annotation.Timer;

/**
 * 방문 여부를 확인
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class VisitChecker {
    private MonthlyMeal monthlyMeal;

    public VisitChecker(MonthlyMeal monthlyMeal) {
        this.monthlyMeal = monthlyMeal;
    }

    @Timer
    public Map<LocalDate, Boolean> check() {
        Map<LocalDate, Boolean> dateVisitStatus = new HashMap<>();
        monthlyMeal.getMonthlyMeal().keySet().stream()
                .forEach(
                        date -> {
                            DailyMeal dailyMeal = monthlyMeal.getMonthlyMeal().get(date);
                            dateVisitStatus.put(date, !dailyMeal.isEmptyMeals());
                        });
        return dateVisitStatus;
    }
}
