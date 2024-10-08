package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDate;

public record GetAnalysisCommand(LocalDate requestDate, Long memberId, LocalDate nowDate) {

    public static GetAnalysisCommand of(LocalDate date, Long memberId, LocalDate nowDate) {
        return new GetAnalysisCommand(date, memberId, nowDate);
    }
}
