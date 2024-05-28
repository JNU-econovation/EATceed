package com.gaebaljip.exceed.food.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.dto.response.GetOwnFoodResponse;
import com.gaebaljip.exceed.food.adapter.out.FoodsPersistenceAdapter;
import com.gaebaljip.exceed.food.application.port.in.GetOwnFoodUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOwnFoodService implements GetOwnFoodUseCase {
    private final FoodsPersistenceAdapter foodsPersistenceAdapter;

    @Override
    public List<GetOwnFoodResponse> execute(Long memberId) {
        return foodsPersistenceAdapter.findByMemberId(memberId).stream()
                .map(food -> GetOwnFoodResponse.of(food.getId(), food.getName()))
                .toList();
    }
}
