package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.meal.application.port.out.RecordMealFoodPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealFoodPersistenceAdapter implements RecordMealFoodPort {

    private final MealFoodRepository mealFoodRepository;

    @Override
    public List<MealFoodEntity> query(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodRepository.saveAll(mealFoodEntities);
    }
}
