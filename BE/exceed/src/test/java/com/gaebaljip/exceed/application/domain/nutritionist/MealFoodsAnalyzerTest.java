package com.gaebaljip.exceed.application.domain.nutritionist;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.dto.MonthlyMealRecordDTO;
import com.gaebaljip.exceed.common.factory.MemberFixtureFactory;
import com.gaebaljip.exceed.common.factory.MonthlyMealFixtureFactory;

class MealFoodsAnalyzerTest {
    @Test
    @DisplayName(
            "MonthlyAnalyzer의 members 필드가 1개일 경우"
                    + "membersMap의 key의 날짜가 now보다 40일전, 즉 무조건 한달 뒤를 보장")
    void when_isCalorieAchievementByDate_expected_success() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime startDateTime = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfDateTime = date.with(TemporalAdjusters.lastDayOfMonth());
        MonthlyMealRecordDTO monthlyMealRecordDTO =
                MonthlyMealFixtureFactory.create(startDateTime, endOfDateTime);
        Member member = MemberFixtureFactory.create(1);
        Map<LocalDate, Member> membersMap = new HashMap<>();
        membersMap.put(startDateTime.toLocalDate().minusDays(40), member);
        MealFoodsAnalyzer mealFoodsAnalyzer =
                new MealFoodsAnalyzer(monthlyMealRecordDTO.mealFoodsByDate(), membersMap);
        assertDoesNotThrow(() -> mealFoodsAnalyzer.isCalorieAchievementByDate());
    }
}
