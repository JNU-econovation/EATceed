package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;

import lombok.Builder;

public record CalorieAnalysisDTO(Boolean isVisited, LocalDate date, boolean isCalorieAchieved) {

    @Builder
    public CalorieAnalysisDTO {}

    public static CalorieAnalysisDTO of(
            DailyMealFoods dailyMealFoods, LocalDate date, boolean isCalorieAchieved) {
        return new CalorieAnalysisDTO(
                dailyMealFoods.getMealFoods().isEmpty() ? false : true, date, isCalorieAchieved);
    }

    public static CalorieAnalysisDTO from(LocalDate date) {
        return CalorieAnalysisDTO.builder()
                .isCalorieAchieved(false)
                .date(date)
                .isVisited(false)
                .build();
    }
}
