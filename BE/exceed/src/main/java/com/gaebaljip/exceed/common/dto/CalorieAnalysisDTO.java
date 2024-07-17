package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;

import lombok.Builder;

public record CalorieAnalysisDTO(Boolean isVisited, LocalDate date, boolean isCalorieAchieved) {

    @Builder
    public CalorieAnalysisDTO {}
}
