package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.config.DateConverter;
import com.gaebaljip.exceed.meal.application.port.out.LoadDailyMealPort;
import com.gaebaljip.exceed.meal.application.port.out.RecordMealPort;
import com.gaebaljip.exceed.meal.domain.MealModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyMealPersistenceAdapter implements RecordMealPort, LoadDailyMealPort {

    private final MealRepository mealRepository;
    private final MealConverter mealConverter;

    @Override
    public Long query(MealEntity mealEntity) {
        return mealRepository.save(mealEntity).getId();
    }

    @Override
    public List<MealModel> query(Long memberId, LocalDate date) {
        Timestamp today = DateConverter.toEpochSecond(date);
        Timestamp tomorrow = DateConverter.toEpochSecond(date.plusDays(1));
        List<MealEntity> mealEntities = mealRepository.findAllTodayMeal(today, tomorrow, memberId);
        return mealConverter.toModels(mealEntities);
    }
}
