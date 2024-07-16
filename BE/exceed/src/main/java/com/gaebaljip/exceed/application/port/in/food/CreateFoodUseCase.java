package com.gaebaljip.exceed.application.port.in.food;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.CreateFoodRequest;

@Component
public interface CreateFoodUseCase {
    void execute(CreateFoodRequest request, Long memberId);
}
