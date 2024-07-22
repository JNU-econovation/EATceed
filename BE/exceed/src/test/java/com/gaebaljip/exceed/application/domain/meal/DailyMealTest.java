package com.gaebaljip.exceed.application.domain.meal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;
import com.gaebaljip.exceed.common.factory.MealsFixtureFactory;

class DailyMealTest {

    @Test
    void when_mealSize_0_expected_return0() {
        List<Meal> meals = MealsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 0);
        DailyMeal dailyMeal = new DailyMeal(meals);
        double calorie = dailyMeal.calculateCurrentCalorie();
        assertEquals(calorie, 0.0);
    }

    @Test
    void when_mealSize_over0_and_sameDate_expected_return_success() {
        List<Meal> meals = MealsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 3);
        assertDoesNotThrow(() -> new DailyMeal(meals));
    }

    @Test
    void when_mealSize_over0_and_NotSameDate_expected_return_exception() {
        List<Meal> meals =
                MealsFixtureFactory.create(LocalDate.now(), LocalDate.now().plusDays(2), 3);
        assertThrows(NotSameDateException.class, () -> new DailyMeal(meals));
    }
}
