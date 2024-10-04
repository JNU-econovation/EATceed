package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

@Component
public interface GetMonthlyAnalysisUsecase {
    String executeToPast(GetMonthlyAnalysisCommand command);

    String executeToNow(GetMonthlyAnalysisCommand command);
}
