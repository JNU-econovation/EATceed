package com.gaebaljip.exceed.dto.response;

import lombok.Builder;
import java.util.List;

public record SpecificMeal(
        CurrentMeal currentMeal,
        List<DailyMeal> dailyMeals
) {

    @Builder
    public SpecificMeal {
    }
}
