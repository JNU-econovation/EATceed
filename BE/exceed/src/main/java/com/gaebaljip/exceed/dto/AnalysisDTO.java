package com.gaebaljip.exceed.dto;

import java.time.LocalDate;

import lombok.Builder;

public record AnalysisDTO(Boolean isVisited, LocalDate date, boolean isCalorieAchieved) {

    @Builder
    public AnalysisDTO {}
}
