package com.gaebaljip.exceed.adapter.out.jpa.meal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyMeal;
import com.gaebaljip.exceed.common.DatabaseTest;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.dto.MonthlyMealDTO;

class MealPersistenceAdapterTest extends DatabaseTest {

    @Autowired private MealPersistenceAdapter mealPersistenceAdapter;

    @Test
    void when_queryForDaily_expected_success() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 10, 11, 11);
        List<Meal> meals = mealPersistenceAdapter.query(new DailyMealDTO(1L, dateTime));
        assertAll(
                () -> assertEquals(2, meals.size()),
                () -> assertEquals(1, meals.get(0).getConsumedFoods().size()),
                () -> assertEquals(1, meals.get(1).getConsumedFoods().size()));
    }

    @Test
    void when_queryForMonth_expected_success() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 20, 23, 22);
        LocalDateTime plusDateTime = dateTime.plusDays(1);
        MonthlyMealDTO monthlyMealDTO = new MonthlyMealDTO(1L, dateTime);
        MonthlyMeal monthlyMeal = mealPersistenceAdapter.query(monthlyMealDTO);

        assertAll(
                () -> assertEquals(30, monthlyMeal.getMonthlyMeal().size()),
                () ->
                        assertEquals(
                                3,
                                monthlyMeal.getMonthlyMeal().values().stream()
                                        .filter(dailyMeal -> dailyMeal.isEmptyMeals())
                                        .toList()
                                        .size()),
                () ->
                        assertEquals(
                                2,
                                monthlyMeal
                                        .getMonthlyMeal()
                                        .get(dateTime.toLocalDate())
                                        .getMeals()
                                        .size()),
                () ->
                        assertEquals(
                                2,
                                monthlyMeal
                                        .getMonthlyMeal()
                                        .get(plusDateTime.toLocalDate())
                                        .getMeals()
                                        .size()));
    }
}
