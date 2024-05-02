package com.gaebaljip.exceed.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;

public record TodayMeal(Long memberId, LocalDateTime date) {

    @Builder
    public TodayMeal {}
}
