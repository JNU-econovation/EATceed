package com.gaebaljip.exceed.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

import lombok.Builder;

public record DailyMealDTO(
        @NotNull(message = "memberId을 " + ValidationMessage.NOT_NULL) Long memberId,
        @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDateTime date) {

    @Builder
    public DailyMealDTO {}
}
