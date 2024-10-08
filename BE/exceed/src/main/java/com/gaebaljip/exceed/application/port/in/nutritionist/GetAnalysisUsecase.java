package com.gaebaljip.exceed.application.port.in.nutritionist;

import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface GetAnalysisUsecase {
    GetMonthlyAnalysisResponse execute(GetAnalysisCommand command);
}
