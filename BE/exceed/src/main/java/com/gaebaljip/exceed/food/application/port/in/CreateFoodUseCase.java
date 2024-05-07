package com.gaebaljip.exceed.food.application.port.in;

import com.gaebaljip.exceed.dto.request.CreateFoodRequest;
import org.springframework.stereotype.Component;

@Component
public interface CreateFoodUseCase {
    void execute(CreateFoodRequest request, Long memberId);
}
