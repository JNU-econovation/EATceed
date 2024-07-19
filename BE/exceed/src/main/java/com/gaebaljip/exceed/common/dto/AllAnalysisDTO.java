package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;

public record AllAnalysisDTO(
        Boolean isVisited,
        LocalDate date,
        boolean isCalorieAchieved,
        boolean isProteinAchieved,
        boolean isFatAchieved,
        boolean isCarbohydrateAchieved) {

    public static AllAnalysisDTO of(
            Boolean isVisited,
            LocalDate date,
            boolean isCalorieAchieved,
            boolean isProteinAchieved,
            boolean isFatAchieved,
            boolean isCarbohydrateAchieved) {
        return new AllAnalysisDTO(
                isVisited,
                date,
                isCalorieAchieved,
                isProteinAchieved,
                isFatAchieved,
                isCarbohydrateAchieved);
    }
}
