package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.meal.application.port.out.MealFoodPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealFoodPersistenceAdapter implements MealFoodPort {

    private final MealFoodRepository mealFoodRepository;

    @Override
    public List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodRepository.saveAll(mealFoodEntities);
    }
}
