package com.gaebaljip.exceed.application.domain.meal;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Meal {

    private Long id;
    private MealType mealType;
    private LocalDateTime mealDateTime;
    private List<ConsumedFood> consumedFoods;

    public double getCurrentCalorie() {
        return consumedFoods.stream().mapToDouble(ConsumedFood::getAdjustedCalorie).sum();
    }

    public double getCurrentCarbohydrate() {
        return consumedFoods.stream().mapToDouble(ConsumedFood::getAdjustedCarbohydrate).sum();
    }

    public double getCurrentProtein() {
        return consumedFoods.stream().mapToDouble(ConsumedFood::getAdjustedProtein).sum();
    }

    public double getCurrentFat() {
        return consumedFoods.stream().mapToDouble(ConsumedFood::getAdjustedFat).sum();
    }
}
