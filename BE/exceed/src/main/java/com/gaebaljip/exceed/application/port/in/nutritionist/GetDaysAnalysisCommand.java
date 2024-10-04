package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDateTime;

public record GetDaysAnalysisCommand(
        Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    public static GetDaysAnalysisCommand of(
            Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new GetDaysAnalysisCommand(memberId, startDateTime, endDateTime);
    }
}
