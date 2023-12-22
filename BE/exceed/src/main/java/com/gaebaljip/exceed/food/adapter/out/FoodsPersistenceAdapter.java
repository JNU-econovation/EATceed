package com.gaebaljip.exceed.food.adapter.out;

import com.gaebaljip.exceed.food.application.out.LoadFoodPort;
import com.gaebaljip.exceed.food.domain.FoodModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodsPersistenceAdapter implements LoadFoodPort {

    private final FoodRepository foodRepository;

    @Override
    public List<FoodModel> query(Long memberId, LocalDate date) {
        return null;
    }

    @Override
    public List<FoodEntity> query(List<Long> foodIds) {
        return foodRepository.findAllById(foodIds);
    }


}
