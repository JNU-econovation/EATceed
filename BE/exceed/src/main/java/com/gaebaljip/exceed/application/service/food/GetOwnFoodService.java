package com.gaebaljip.exceed.application.service.food;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.out.jpa.food.FoodsPersistenceAdapter;
import com.gaebaljip.exceed.application.port.in.food.GetOwnFoodUseCase;
import com.gaebaljip.exceed.dto.response.GetOwnFoodResponse;

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
