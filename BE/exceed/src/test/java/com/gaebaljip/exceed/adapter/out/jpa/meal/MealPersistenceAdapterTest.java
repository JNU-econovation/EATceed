package com.gaebaljip.exceed.adapter.out.jpa.meal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.common.DatabaseTest;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.dto.MonthlyMealDTO;
import com.gaebaljip.exceed.common.dto.MonthlyMealRecordDTO;

class MealPersistenceAdapterTest extends DatabaseTest {

    @Autowired private MealPersistenceAdapter mealPersistenceAdapter;

    @Test
    void when_queryForDaily_expected_success() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 10, 11, 11);
        DailyMealFoods dailyMealFoods =
                mealPersistenceAdapter.queryMealFoodsForDay(new DailyMealDTO(1L, dateTime));
        assertAll(() -> assertEquals(2, dailyMealFoods.getMealFoods().size()));
    }

    @Test
    void when_queryForMonth_expected_success() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 20, 23, 22);
        LocalDateTime plusDateTime = dateTime.plusDays(1);
        MonthlyMealDTO monthlyMealDTO =
                new com.gaebaljip.exceed.common.dto.MonthlyMealDTO(1L, dateTime.toLocalDate());
        MonthlyMealRecordDTO monthlyMeal = mealPersistenceAdapter.query(monthlyMealDTO);

        assertAll(
                () -> assertEquals(30, monthlyMeal.mealFoodsByDate().size()),
                () ->
                        assertEquals(
                                3,
                                monthlyMeal.mealFoodsByDate().values().stream()
                                        .filter(dailyMeal -> dailyMeal.isEmptyMeals())
                                        .toList()
                                        .size()),
                () ->
                        assertEquals(
                                2,
                                monthlyMeal
                                        .mealFoodsByDate()
                                        .get(dateTime.toLocalDate())
                                        .getMealFoods()
                                        .size()),
                () ->
                        assertEquals(
                                2,
                                monthlyMeal
                                        .mealFoodsByDate()
                                        .get(plusDateTime.toLocalDate())
                                        .getMealFoods()
                                        .size()));
    }
}
