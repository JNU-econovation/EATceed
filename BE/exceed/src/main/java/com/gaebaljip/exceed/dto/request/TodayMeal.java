package com.gaebaljip.exceed.dto.request;

import lombok.Builder;

import java.time.LocalDate;

public record TodayMeal(
        Long memberId,
        LocalDate date) {

    @Builder
    public TodayMeal {
    }
}
