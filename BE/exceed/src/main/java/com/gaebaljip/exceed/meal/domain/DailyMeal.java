package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.meal.exception.InsufficientMealsException;
import com.gaebaljip.exceed.meal.exception.NotSameDateException;
import lombok.*;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * 같은 날짜 즉, 하루에 먹은 식사를 나타내는 클래스
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

    private void validateSize(List<Meal> meals){
        if(meals.isEmpty()){
            throw new InsufficientMealsException();
        }
    }

    private void validateSameMealDate(List<Meal> meals){
        if(getDistinctSize(meals) != 1) throw new NotSameDateException();
    }


    private int getDistinctSize(List<Meal> meals) {
        return meals.stream()
                .map(meal -> meal.getMealDateTime().toLocalDate())
                .collect(toSet())
                .size();
    }

}
