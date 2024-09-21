package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDate;

public record GetAnalysisCommand(LocalDate requestDate, Long memberId) {

    public static GetAnalysisCommand of(LocalDate date, Long memberId) {
        return new GetAnalysisCommand(date, memberId);
    }
}
