package com.gaebaljip.exceed.application.domain.meal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;
import com.gaebaljip.exceed.common.factory.DailyMealFoodsFixtureFactory;

class DailyMealFoodsTest {

    @Test
    void when_mealSize_0_expected_return0() {
        DailyMealFoods dailyMealFoods =
                DailyMealFoodsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 0);
        double calorie = dailyMealFoods.calculateCurrentCalorie();
        assertEquals(calorie, 0.0);
    }

    @Test
    void when_mealSize_over0_and_sameDate_expected_return_success() {
        DailyMealFoods dailyMealFoods =
                DailyMealFoodsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 3);
        assertDoesNotThrow(() -> dailyMealFoods);
    }

    @Test
    void when_mealSize_over0_and_NotSameDate_expected_return_exception() {
        assertThrows(
                NotSameDateException.class,
                () ->
                        DailyMealFoodsFixtureFactory.create(
                                LocalDate.now(), LocalDate.now().plusDays(2), 3));
    }
}
