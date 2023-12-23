package com.gaebaljip.exceed.meal.domain;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MealsModel {

    private List<MealModel> mealModels;

    public double calculateCurrentCalorie() {
        return mealModels.stream().mapToDouble(MealModel::getCurrentCalorie).sum();
    }

    public double calculateCurrentCarbohydrate() {
        return mealModels.stream().mapToDouble(MealModel::getCurrentCarbohydrate).sum();
    }

    public double calculateCurrentProtein() {
        return mealModels.stream().mapToDouble(MealModel::getCurrentProtein).sum();
    }

    public double calculateCurrentFat() {
        return mealModels.stream().mapToDouble(MealModel::getCurrentFat).sum();
    }

}
