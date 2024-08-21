package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;

public record AllAnalysisDTO(
        Boolean isVisited,
        LocalDate date,
        boolean isCalorieAchieved,
        boolean isProteinAchieved,
        boolean isFatAchieved,
        boolean isCarbohydrateAchieved) {

    public static AllAnalysisDTO of(
            DailyMealFoods dailyMealFoods,
            LocalDate date,
            boolean isCalorieAchieved,
            boolean isProteinAchieved,
            boolean isFatAchieved,
            boolean isCarbohydrateAchieved) {
        return new AllAnalysisDTO(
                dailyMealFoods.getMealFoods().isEmpty() ? false : true,
                date,
                isCalorieAchieved,
                isProteinAchieved,
                isFatAchieved,
                isCarbohydrateAchieved);
    }
}
