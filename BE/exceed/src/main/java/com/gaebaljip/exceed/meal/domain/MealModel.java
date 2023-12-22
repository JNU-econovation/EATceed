package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.food.domain.FoodModel;
import com.gaebaljip.exceed.member.domain.MemberModel;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MealModel {

    private MealType mealType;
    private List<FoodModel> foodModels;

    public double getCurentCalorie() {
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
