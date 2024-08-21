package com.gaebaljip.exceed.application.domain.meal;

import static java.util.stream.Collectors.toSet;

import java.util.List;

import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;

import lombok.*;

/**
 * 하루에 먹은 식사 음식들의 칼로리,단,탄,지를 계산
 *
 * @author hwangdaesun
 * @version 1.0
 * @throws NotSameDateException : 식사들의 날짜가 다를 경우
 */
@Getter
@ToString
public class DailyMealFoods {

    private final List<MealFoodEntity> mealFoods;

    public DailyMealFoods(List<MealFoodEntity> mealFoods) {
        validateSameDateIfNotEmpty(mealFoods);
        this.mealFoods = mealFoods;
    }

    public double calculateCurrentCalorie() {
        return mealFoods.stream().mapToDouble(MealFoodEntity::getAdjustedCalorie).sum();
    }

    public double calculateCurrentCarbohydrate() {
        return mealFoods.stream().mapToDouble(MealFoodEntity::getAdjustedCarbohydrate).sum();
    }

    public double calculateCurrentProtein() {
        return mealFoods.stream().mapToDouble(MealFoodEntity::getAdjustedProtein).sum();
    }

    public double calculateCurrentFat() {
        return mealFoods.stream().mapToDouble(MealFoodEntity::getAdjustedFat).sum();
    }

    private void validateSameDateIfNotEmpty(List<MealFoodEntity> mealFoods) {
        if (!mealFoods.isEmpty() && !isSameDate(mealFoods)) {
            throw NotSameDateException.EXECPTION;
        }
    }

    private boolean isSameDate(List<MealFoodEntity> mealFoods) {
        int size =
                mealFoods.stream()
                        .map(mealFood -> mealFood.getCreatedDate().toLocalDate())
                        .collect(toSet())
                        .size();
        if (size != 1) {
            return false;
        }
        return true;
    }

    public boolean isEmptyMeals() {
        return this.mealFoods.isEmpty();
    }
}
