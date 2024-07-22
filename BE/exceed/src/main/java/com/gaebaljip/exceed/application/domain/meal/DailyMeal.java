package com.gaebaljip.exceed.application.domain.meal;

import static java.util.stream.Collectors.toSet;

import java.util.List;

import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;

import lombok.*;

/**
 * 하루에 먹은 식사들을 분석하여 칼로리,단,탄,지를 계산
 *
 * @author hwangdaesun
 * @version 1.0
 * @throws NotSameDateException : 식사들의 날짜가 다를 경우
 */
@Getter
@ToString
public class DailyMeal {

    private final List<Meal> meals;

    public DailyMeal(List<Meal> meals) {
        validateSameDateIfNotEmpty(meals);
        this.meals = meals;
    }

    public double calculateCurrentCalorie() {
        return meals.stream().mapToDouble(Meal::getCurrentCalorie).sum();
    }

    public double calculateCurrentCarbohydrate() {
        return meals.stream().mapToDouble(Meal::getCurrentCarbohydrate).sum();
    }

    public double calculateCurrentProtein() {
        return meals.stream().mapToDouble(Meal::getCurrentProtein).sum();
    }

    public double calculateCurrentFat() {
        return meals.stream().mapToDouble(Meal::getCurrentFat).sum();
    }

    private void validateSameDateIfNotEmpty(List<Meal> meals) {
        if (!meals.isEmpty() && !isSameDate(meals)) {
            throw NotSameDateException.EXECPTION;
        }
    }

    private boolean isSameDate(List<Meal> meals) {
        int size =
                meals.stream()
                        .map(meal -> meal.getMealDateTime().toLocalDate())
                        .collect(toSet())
                        .size();
        if (size != 1) {
            return false;
        }
        return true;
    }
}
