package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.meal.application.port.out.LoadMealPort;
import com.gaebaljip.exceed.meal.application.port.out.RecordMealPort;
import com.gaebaljip.exceed.meal.domain.MealModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MealPersistenceAdapter implements RecordMealPort, LoadMealPort {

    private final MealRepository mealRepository;

    @Override
    public Long query(MealEntity mealEntity) {
        return mealRepository.save(mealEntity).getId();
    }

    @Override
    public MealModel query(Long memberId, LocalDate date) {
        return null;
    }
}
