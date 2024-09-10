package com.gaebaljip.exceed.application.port.in.food;

public interface DeleteFoodUseCase {
    void deleteFood(Long foodId, Long memberId);
}
