package com.gaebaljip.exceed.dto.request;

import java.time.LocalDateTime;

import com.gaebaljip.exceed.common.ValidationMessage;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record TodayMeal(@NotNull(message = "memberId을 " + ValidationMessage.NOT_NULL) Long memberId,
                        @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDateTime date) {

    @Builder
    public TodayMeal {}
}
