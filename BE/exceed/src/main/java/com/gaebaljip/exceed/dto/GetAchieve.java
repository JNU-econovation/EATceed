package com.gaebaljip.exceed.dto;

import lombok.Builder;

import java.time.LocalDate;

public record GetAchieve(
        boolean isVisited,
        LocalDate date,
        double calorieRate,
        boolean proteinAchieve,
        boolean fatAchieve,
        boolean carbohydrateAchieve) {

    @Builder
    public GetAchieve {
    }
}
