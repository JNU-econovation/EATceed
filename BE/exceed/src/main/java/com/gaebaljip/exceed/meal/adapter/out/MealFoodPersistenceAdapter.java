package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.application.port.out.MealFoodPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealFoodPersistenceAdapter implements MealFoodPort {

    private final MealFoodRepository mealFoodRepository;

    @Override
    public List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodRepository.saveAll(mealFoodEntities);
    }
}
