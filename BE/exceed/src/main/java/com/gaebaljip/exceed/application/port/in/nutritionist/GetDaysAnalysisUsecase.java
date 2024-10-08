package com.gaebaljip.exceed.application.port.in.nutritionist;

import com.gaebaljip.exceed.common.annotation.UseCase;
import com.gaebaljip.exceed.common.dto.GetDaysAnalysisDTO;

@UseCase
public interface GetDaysAnalysisUsecase {
    GetDaysAnalysisDTO execute(GetDaysAnalysisCommand command);
}
