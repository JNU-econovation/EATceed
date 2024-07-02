package com.gaebaljip.exceed.dto.response;

import java.time.LocalDate;

import lombok.Builder;

public record AnalysisDTO(Boolean isVisited, LocalDate date, boolean isCalorieAchieved) {

    @Builder
    public AnalysisDTO {}
}
