package com.gaebaljip.exceed.food.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.CreateFoodRequest;

@Component
public interface CreateFoodUseCase {
    void execute(CreateFoodRequest request, Long memberId);
}
