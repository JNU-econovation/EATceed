package com.gaebaljip.exceed.application.domain.nutritionist;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;
import com.gaebaljip.exceed.common.exception.nutritionist.MinimumMemberRequiredException;

/**
 * 특정 기간의 식사들의 달성률을 계산
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class MealFoodsAnalyzer {
    private Map<LocalDate, DailyMealFoods> mealFoodsByDate;
    private Map<LocalDate, Member> members;

    public MealFoodsAnalyzer(
            Map<LocalDate, DailyMealFoods> mealFoodsByDate, Map<LocalDate, Member> members) {
        validateAtLeastOneMember(members);
        this.mealFoodsByDate = mealFoodsByDate;
        this.members = members;
    }

    @Timer
    public Map<LocalDate, Boolean> isCalorieAchievementByDate() {
        Map<LocalDate, Boolean> calorieAchievements = new HashMap<>();
        mealFoodsByDate.entrySet().stream()
                .forEach(
                        dailyMealEntry -> {
                            Member memberByDate =
                                    getMemberByDate(dailyMealEntry.getKey(), this.members);
                            DailyCalorieAnalyzer dailyCalorieAnalyzer =
                                    DailyCalorieAnalyzerFactory.getInstance()
                                            .createAnalyzer(
                                                    dailyMealEntry.getValue(), memberByDate);
                            calorieAchievements.put(
                                    dailyMealEntry.getKey(), dailyCalorieAnalyzer.analyze());
                        });
        return calorieAchievements;
    }

    /**
     * day와 비교해 가장 가까운 미래를 찾는다. 단, members의 size가 1인 경우는 day가 속한 달보다 미래이다.
     *
     * @param day
     * @param members
     * @return
     */
    private Member getMemberByDate(LocalDate day, Map<LocalDate, Member> members) {
        if (members.size() == 1) {
            return members.entrySet().stream().findFirst().map(Map.Entry::getValue).get();
        }
        return members.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(day) || entry.getKey().equals(day))
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
    }

    private void validateAtLeastOneMember(Map<LocalDate, Member> members) {
        if (members.isEmpty()) {
            throw MinimumMemberRequiredException.EXCEPTION;
        }
    }
}
