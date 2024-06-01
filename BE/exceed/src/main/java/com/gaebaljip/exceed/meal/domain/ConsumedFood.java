package com.gaebaljip.exceed.meal.domain;

import com.gaebaljip.exceed.food.domain.Food;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsumedFood {

    private Food food;
    private Unit unit;
    private MeasureStrategy measureStrategy;

    private MeasureStrategy getStrategy() {
        if (this.unit.getG() == null) {
            return new MultipleStrategy();
        } else {
            return new GStrategy();
        }
    }

    public double getAdjustedCalorie() {
        return getStrategy().measure(this.food.getCalorie(), unit);
    }

    public double getAdjustedCarbohydrate() {
        return getStrategy().measure(this.food.getCarbohydrate(), unit);
    }

    public double getAdjustedProtein() {
        return getStrategy().measure(this.food.getProtein(), unit);
    }

    public double getAdjustedFat() {
        return getStrategy().measure(this.food.getFat(), unit);
    }
}
