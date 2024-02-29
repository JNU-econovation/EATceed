package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.meal.exception.InsufficientMealsException;
import com.gaebaljip.exceed.meal.exception.NotSameDateException;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        LocalDate mealDate = meals.get(0).getMealDateTime().toLocalDate();
        Optional<Meal> incorrectDateMeal = meals.stream().filter(meal -> !meal.getMealDateTime().toLocalDate().isEqual(mealDate))
                .findFirst();
        if(incorrectDateMeal.isPresent()){
            throw new NotSameDateException();
        }
    }

}
