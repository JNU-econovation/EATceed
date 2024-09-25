package com.gaebaljip.exceed.application.port.in.nutritionist;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetMonthlyAnalysisCommand;

@Component
public interface GetMonthlyAnalysisUsecase {
    String execute(GetMonthlyAnalysisCommand command);
}
