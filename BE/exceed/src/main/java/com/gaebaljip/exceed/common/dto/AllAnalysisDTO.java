package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;
import java.util.List;

import com.gaebaljip.exceed.application.domain.meal.Meal;

public record AllAnalysisDTO(
        Boolean isVisited,
        LocalDate date,
        boolean isCalorieAchieved,
        boolean isProteinAchieved,
        boolean isFatAchieved,
        boolean isCarbohydrateAchieved) {

    public static AllAnalysisDTO of(
            List<Meal> meals,
            LocalDate date,
            boolean isCalorieAchieved,
            boolean isProteinAchieved,
            boolean isFatAchieved,
            boolean isCarbohydrateAchieved) {
        return new AllAnalysisDTO(
                meals.isEmpty() ? false : true,
                date,
                isCalorieAchieved,
                isProteinAchieved,
                isFatAchieved,
                isCarbohydrateAchieved);
    }
}
