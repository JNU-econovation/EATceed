package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.achieve.application.port.out.LoadMonthMealPort;
import com.gaebaljip.exceed.achieve.domain.DailyRecord;
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
public class MealPersistenceAdapter implements RecordMealPort, LoadDailyMealPort, LoadMonthMealPort {

    private final MealRepository mealRepository;
    private final MealConverter mealConverter;
    private final int FIRST_DAY = 1;

    @Override
    public Long query(MealEntity mealEntity) {
        return mealRepository.save(mealEntity).getId();
    }

    @Override
    public List<MealModel> queryMealsForDate(Long memberId, LocalDate date) {
        Timestamp today = DateConverter.toEpochSecond(date);
        Timestamp tomorrow = DateConverter.toEpochSecond(date.plusDays(1));
        List<MealEntity> mealEntities = mealRepository.findAllTodayMeal(today, tomorrow, memberId);
        return mealConverter.toModels(mealEntities);
    }

    @Override
    public List<DailyRecord> queryForMonthAchievements(Long memberId, LocalDate date) {
        Timestamp startOfMonth = DateConverter.toEpochSecond(LocalDate.of(date.getYear(), date.getMonth(), FIRST_DAY));
        Timestamp endOfMonth = DateConverter.toEpochSecond(LocalDate.of(date.getYear(), date.getMonth(), getDayOfMonth(date)));
        List<MealEntity> monthMealEntities = mealRepository.findAllMonthMeal(startOfMonth, endOfMonth, memberId);
        return mealConverter.toDailyAchieves(monthMealEntities);
    }

    private int getDayOfMonth(LocalDate date) {
        return date.getMonth().maxLength();
    }
}
