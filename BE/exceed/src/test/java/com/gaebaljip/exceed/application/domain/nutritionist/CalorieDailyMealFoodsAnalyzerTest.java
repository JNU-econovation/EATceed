package com.gaebaljip.exceed.application.domain.nutritionist;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.factory.DailyMealFoodsFixtureFactory;
import com.gaebaljip.exceed.common.factory.MemberFixtureFactory;

class CalorieDailyMealFoodsAnalyzerTest {
    @Test
    void when_meals_size_0_expected_false() {
        DailyMealFoods dailyMealFoods =
                DailyMealFoodsFixtureFactory.create(LocalDate.now(), LocalDate.now(), 0);
        Member member = MemberFixtureFactory.create(1);
        DailyCalorieAnalyzerFactory dailyCalorieAnalyzerFactory =
                DailyCalorieAnalyzerFactory.getInstance();
        DailyCalorieAnalyzer dailyCalorieAnalyzer =
                dailyCalorieAnalyzerFactory.createAnalyzer(dailyMealFoods, member);
        boolean analyze = dailyCalorieAnalyzer.analyze();
        assertEquals(analyze, false);
    }
}
