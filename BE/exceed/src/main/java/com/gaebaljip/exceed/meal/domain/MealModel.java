package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.food.domain.FoodModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MealModel {

    private Long id;
    private MealType mealType;
    private LocalDateTime mealDateTime;
    private List<FoodModel> foodModels;

    public double getCurrentCalorie() {
        return foodModels.stream().mapToDouble(FoodModel::getCalorie).sum();
    }

    public double getCurrentCarbohydrate() {
        return foodModels.stream().mapToDouble(FoodModel::getCarbohydrate).sum();
    }

    public double getCurrentProtein() {
        return foodModels.stream().mapToDouble(FoodModel::getProtein).sum();
    }

    public double getCurrentFat() {
        return foodModels.stream().mapToDouble(FoodModel::getFat).sum();
    }

}
