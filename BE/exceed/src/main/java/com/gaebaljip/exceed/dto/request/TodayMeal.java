package com.gaebaljip.exceed.dto.request;

import java.time.LocalDate;

import lombok.Builder;

public record TodayMeal(Long memberId, LocalDate date) {

    @Builder
    public TodayMeal {}
}
