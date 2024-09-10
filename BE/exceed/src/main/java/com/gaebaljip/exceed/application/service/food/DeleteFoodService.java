package com.gaebaljip.exceed.application.service.food;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.port.in.food.DeleteFoodUseCase;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.gaebaljip.exceed.common.exception.food.CannotDeleteOthersFoodException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteFoodService implements DeleteFoodUseCase {
    private final FoodPort foodPort;

    @Override
    @Transactional
    public void deleteFood(Long foodId, Long memberId) {
        validateIsOwnFood(foodId, memberId);
        foodPort.deleteById(foodId);
    }

    private void validateIsOwnFood(Long foodId, Long memberId) {
        if (!foodPort.query(foodId).getMemberEntity().getId().equals(memberId)) {
            throw CannotDeleteOthersFoodException.Exception;
        }
    }
}
