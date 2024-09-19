package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDate;

public record GetAnalysisCommand(LocalDate date, Long memberId) {

    public static GetAnalysisCommand of(LocalDate date, Long memberId) {
        return new GetAnalysisCommand(date, memberId);
    }
}
