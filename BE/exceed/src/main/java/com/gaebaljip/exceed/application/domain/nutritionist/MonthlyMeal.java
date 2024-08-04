package com.gaebaljip.exceed.application.domain.nutritionist;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.common.exception.nutritionist.MonthDaysSizeMismatchException;
import com.gaebaljip.exceed.common.exception.nutritionist.SameYearAndMonthException;

import lombok.Getter;

/**
 * 한달치 먹은 식사를 나타내는 일급 컬렉션
 *
 * @author hwangdaesun
 * @version 1.0
 * @throws SameYearAndMonthException
 * @throws MonthDaysSizeMismatchException
 */
@Getter
public class MonthlyMeal {
    private Map<LocalDate, DailyMeal> monthlyMeal;

    public MonthlyMeal(Map<LocalDate, DailyMeal> monthlyMeal) {
        validateSameYearMonth(monthlyMeal);
        validateMonthlyMealSize(monthlyMeal);
        this.monthlyMeal = monthlyMeal;
    }

    private void validateSameYearMonth(Map<LocalDate, DailyMeal> monthlyMeal) {
        if (!monthlyMeal.isEmpty()) {
            LocalDate date = monthlyMeal.keySet().stream().findFirst().get();
            int year = date.getYear();
            int month = date.getMonthValue();
            monthlyMeal.keySet().stream()
                    .forEach(
                            day -> {
                                if (day.getYear() != year || day.getMonthValue() != month) {
                                    throw SameYearAndMonthException.EXECPTION;
                                }
                            });
        }
    }

    private void validateMonthlyMealSize(Map<LocalDate, DailyMeal> monthlyMeal) {
        Optional<LocalDate> date = monthlyMeal.keySet().stream().findFirst();
        if (date.isPresent()) {
            if (monthlyMeal.size() != date.get().lengthOfMonth()) {
                throw MonthDaysSizeMismatchException.EXECPTION;
            }
        }
    }
}
