package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDateTime;

public record GetDailyAnalysisCommand(Long memberId, LocalDateTime dateTime) {
    public static GetDailyAnalysisCommand of(Long memberId, LocalDateTime dateTime) {
        return new GetDailyAnalysisCommand(memberId, dateTime);
    }
}
