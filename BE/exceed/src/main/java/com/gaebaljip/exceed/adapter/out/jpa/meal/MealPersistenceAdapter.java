package com.gaebaljip.exceed.adapter.out.jpa.meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.jpa.nutritionist.MonthlyMealPort;
import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.meal.MealPort;
import com.gaebaljip.exceed.common.dto.*;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealPersistenceAdapter implements MealPort, DailyMealPort, MonthlyMealPort {

    private final MealRepository mealRepository;
    private final MealFoodRepository mealFoodRepository;

    @Override
    public MealEntity command(MealEntity mealEntity) {
        return mealRepository.save(mealEntity);
    }

    @Override
    public DailyMealFoods queryMealFoodsForDay(DailyMealDTO dailyMealDTO) {
        LocalDateTime today = dailyMealDTO.date().toLocalDate().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);
        List<Long> mealIds =
                mealRepository.findMealIdsByMemberAndDaily(
                        today, tomorrow, dailyMealDTO.memberId());
        List<MealFoodEntity> mealFoodEntities = mealFoodRepository.findMFTByIdInQuery(mealIds);
        return new DailyMealFoods(mealFoodEntities);
    }

    @Override
    public DaysMealRecordDTO queryMealFoodsForDays(DaysMealDTO daysMealDTO) {
        List<Long> mealIds =
                mealRepository.findMealIdsByMemberAndMonth(
                        daysMealDTO.startDateTime(),
                        daysMealDTO.endDateTime(),
                        daysMealDTO.memberId());
        List<MealFoodEntity> mealFoodEntities = mealFoodRepository.findMFTByIdInQuery(mealIds);
        Map<LocalDate, DailyMealFoods> daysMeal =
                mealFoodEntities.stream()
                        .collect(
                                Collectors.groupingBy(
                                        mealFood -> mealFood.getCreatedDate().toLocalDate(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(), DailyMealFoods::new)));
        List<MealFoodEntity> emptyMealFood = new ArrayList<>();
        daysMealDTO
                .startDateTime()
                .toLocalDate()
                .datesUntil(daysMealDTO.endDateTime().toLocalDate())
                .forEach(day -> daysMeal.putIfAbsent(day, new DailyMealFoods(emptyMealFood)));
        return new DaysMealRecordDTO(daysMeal);
    }

    @Override
    public List<MealEntity> queryMeals(DailyMealDTO dailyMealDTO) {
        LocalDateTime today = dailyMealDTO.date().toLocalDate().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);
        List<MealEntity> mealEntities =
                mealRepository.findMealsByMemberAndDaily(today, tomorrow, dailyMealDTO.memberId());
        return mealEntities;
    }

    @Override
    public DailyMealFoods queryMealFoods(List<Long> mealIds) {
        List<MealFoodEntity> mealFoodEntities = mealFoodRepository.findMFTByIdInQuery(mealIds);
        return new DailyMealFoods(mealFoodEntities);
    }

    @Override
    public MonthlyMealRecordDTO query(MonthlyMealDTO monthlyMealDTO) {
        LocalDateTime date = monthlyMealDTO.date().atStartOfDay();
        LocalDateTime startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfMonth = date.with(TemporalAdjusters.firstDayOfNextMonth());

        List<Long> mealIds =
                mealRepository.findMealIdsByMemberAndMonth(
                        startOfMonth, endOfMonth, monthlyMealDTO.memberId());
        List<MealFoodEntity> mealFoodEntities = mealFoodRepository.findMFTByIdInQuery(mealIds);
        Map<LocalDate, DailyMealFoods> monthlyMeal =
                mealFoodEntities.stream()
                        .collect(
                                Collectors.groupingBy(
                                        mealFood -> mealFood.getCreatedDate().toLocalDate(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(), DailyMealFoods::new)));
        List<MealFoodEntity> emptyMealFood = new ArrayList<>();
        startOfMonth
                .toLocalDate()
                .datesUntil(endOfMonth.toLocalDate())
                .forEach(day -> monthlyMeal.putIfAbsent(day, new DailyMealFoods(emptyMealFood)));
        return new MonthlyMealRecordDTO(monthlyMeal);
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
