package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

public record GetMonthlyAnalysisCommand(
        @NotNull(message = "memberId을" + ValidationMessage.NOT_NULL) Long memberId,
        @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDate date) {

    public static GetMonthlyAnalysisCommand of(Long memberId, LocalDate date) {
        return new GetMonthlyAnalysisCommand(memberId, date);
    }
}
