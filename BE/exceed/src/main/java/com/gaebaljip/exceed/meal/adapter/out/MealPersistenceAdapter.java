package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.meal.application.port.out.LoadMealPort;
import com.gaebaljip.exceed.meal.domain.MealModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class MealPersistenceAdapter implements LoadMealPort {
    @Override
    public MealModel query(Long memberId, LocalDate date) {
        return null;
    }
}
