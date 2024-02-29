package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyMealPort;
import com.gaebaljip.exceed.config.DateConverter;
import com.gaebaljip.exceed.dto.request.DailyMeal;
import com.gaebaljip.exceed.dto.request.MonthlyMeal;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.application.port.out.MealPort;
import com.gaebaljip.exceed.meal.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MealPersistenceAdapter implements MealPort, DailyMealPort, MonthlyMealPort {

    private final MealRepository mealRepository;
    private final MealConverter mealConverter;
    private final int FIRST_DAY = 1;

    @Override
    public MealEntity command(MealEntity mealEntity) {
        return mealRepository.save(mealEntity);
    }

    @Override
    public List<Meal> query(DailyMeal dailyMeal) {
        Timestamp today = DateConverter.toEpochSecond(dailyMeal.date());
        Timestamp tomorrow = DateConverter.toEpochSecond(dailyMeal.date().plusDays(1));
        List<MealEntity> mealEntities = mealRepository.findAllTodayMeal(today, tomorrow, dailyMeal.memberId());
        return mealConverter.toMeals(mealEntities);
    }

    @Override
    public List<Meal> query(MonthlyMeal monthlyMeal) {
        Timestamp startOfMonth = DateConverter.toEpochSecond(LocalDate.of(monthlyMeal.date().getYear(), monthlyMeal.date().getMonth(), FIRST_DAY));
        Timestamp endOfMonth = DateConverter.toEpochSecond(LocalDate.of(monthlyMeal.date().getYear(), monthlyMeal.date().getMonth(), getDayOfMonth(monthlyMeal.date())));
        List<MealEntity> mealEntities = mealRepository.findMealsByMemberAndMonth(startOfMonth, endOfMonth, monthlyMeal.memberId());
        return mealConverter.toMeals(mealEntities);
    }

    private int getDayOfMonth(LocalDate date) {
        return date.getMonth().maxLength();
    }
}
