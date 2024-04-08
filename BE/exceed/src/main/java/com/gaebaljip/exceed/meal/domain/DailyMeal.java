package com.gaebaljip.exceed.meal.domain;

import static java.util.stream.Collectors.toSet;

import java.util.List;

import com.gaebaljip.exceed.meal.exception.InsufficientMealsException;
import com.gaebaljip.exceed.meal.exception.NotSameDateException;

import lombok.*;

/**
 * 같은 날짜 즉, 하루에 먹은 식사를 나타내는 클래스 오늘 먹은 식사들을 분석하여 칼로리,단,탄,지를 계산한다.
 *
 * @author hwangdaesun
 * @version 1.0
 * @throws NotSameDateException : 식사들의 날짜가 다를 경우
 * @throws InsufficientMealsException : 식사를 한 번도 하지 않았을 경우
 */
@Getter
@ToString
public class DailyMeal {

    private final List<Meal> meals;

    public DailyMeal(List<Meal> meals) {
        validateSize(meals);
        validateSameMealDate(meals);
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

    private void validateSize(List<Meal> meals) {
        if (meals.isEmpty()) {
            throw InsufficientMealsException.EXECPTION;
        }
    }

    private void validateSameMealDate(List<Meal> meals) {
        if (getDistinctSize(meals) != 1) throw NotSameDateException.EXECPTION;
    }

    private int getDistinctSize(List<Meal> meals) {
        return meals.stream()
                .map(meal -> meal.getMealDateTime().toLocalDate())
                .collect(toSet())
                .size();
    }
}
