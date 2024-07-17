package com.gaebaljip.exceed.application.port.in.food;

import java.util.List;

import com.gaebaljip.exceed.common.dto.response.GetOwnFoodResponse;

public interface GetOwnFoodUseCase {
    List<GetOwnFoodResponse> execute(Long memberId);
}
