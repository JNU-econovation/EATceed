package com.gaebaljip.exceed.food.application.port.in;

import java.util.List;

import com.gaebaljip.exceed.dto.response.GetOwnFoodResponse;

public interface GetOwnFoodUseCase {
    List<GetOwnFoodResponse> execute(Long memberId);
}
