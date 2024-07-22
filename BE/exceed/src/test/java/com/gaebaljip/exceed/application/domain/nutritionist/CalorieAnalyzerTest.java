package com.gaebaljip.exceed.application.domain.nutritionist;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.factory.MealsFixtureFactory;
import com.gaebaljip.exceed.common.factory.MemberFixtureFactory;

class CalorieAnalyzerTest {
    @Test
    void when_meals_size_0_expected_false() {
        List<Meal> meals = MealsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 0);
        Member member = MemberFixtureFactory.create(1);
        CalorieAnalyzerFactory calorieAnalyzerFactory = CalorieAnalyzerFactory.getInstance();
        CalorieAnalyzer calorieAnalyzer =
                calorieAnalyzerFactory.createAnalyzer(new DailyMeal(meals), member);
        boolean analyze = calorieAnalyzer.analyze();
        assertEquals(analyze, false);
    }
}
