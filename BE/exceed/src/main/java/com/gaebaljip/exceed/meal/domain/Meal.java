package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.food.domain.Food;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Meal {

    private Long id;
    private MealType mealType;
    private LocalDateTime mealDateTime;
    private List<Food> foods;

    public double getCurrentCalorie() {
        return foods.stream().mapToDouble(Food::getCalorie).sum();
    }

    public double getCurrentCarbohydrate() {
        return foods.stream().mapToDouble(Food::getCarbohydrate).sum();
    }

    public double getCurrentProtein() {
        return foods.stream().mapToDouble(Food::getProtein).sum();
    }

    public double getCurrentFat() {
        return foods.stream().mapToDouble(Food::getFat).sum();
    }

}
