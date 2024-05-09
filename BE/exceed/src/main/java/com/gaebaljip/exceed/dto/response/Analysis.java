package com.gaebaljip.exceed.dto.response;

import java.time.LocalDate;

import lombok.Builder;

public record Analysis(Boolean isVisited, LocalDate date, boolean isCalorieAchieved) {

    @Builder
    public Analysis {}
}
