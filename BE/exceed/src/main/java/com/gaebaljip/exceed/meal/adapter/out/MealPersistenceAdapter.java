package com.gaebaljip.exceed.meal.adapter.out;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.dto.request.MonthlyMealDTO;
import com.gaebaljip.exceed.dto.request.TodayMealDTO;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.application.port.out.MealPort;
import com.gaebaljip.exceed.meal.domain.Meal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyMealPort;

import lombok.RequiredArgsConstructor;

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
    public List<Meal> query(TodayMealDTO todayMealDTO) {
        LocalDateTime today = todayMealDTO.date().toLocalDate().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);
        List<MealEntity> mealEntities =
                mealRepository.findAllTodayMeal(today, tomorrow, todayMealDTO.memberId());
        return mealConverter.toMeals(mealEntities);
    }

    @Override
    @Timer
    public List<Meal> query(MonthlyMealDTO monthlyMealDTO) {
        LocalDateTime date = monthlyMealDTO.date().toLocalDate().atStartOfDay();
        LocalDateTime startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfMonth = date.with(TemporalAdjusters.firstDayOfNextMonth());

        List<MealEntity> mealEntities =
                mealRepository.findMealsByMemberAndMonth(
                        startOfMonth, endOfMonth, monthlyMealDTO.memberId());
        return mealConverter.toMeals(mealEntities);
    }

    @Override
    public List<MealEntity> findByMemberEntity(MemberEntity memberEntity) {
        return mealRepository.findByMemberEntity(memberEntity);
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        mealRepository.deleteByAllByIdInQuery(ids);
    }
}
