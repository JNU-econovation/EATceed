package com.gaebaljip.exceed.application.domain.nutritionist;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.nutritionist.MinimumMemberRequiredException;

/**
 * 한달치 달성률을 계산
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class MonthlyAnalyzer {
    private MonthlyMeal monthlyMeal;
    private Map<LocalDate, Member> members;

    public MonthlyAnalyzer(MonthlyMeal monthlyMeal, Map<LocalDate, Member> members) {
        validateAtLeastOneMember(members);
        this.monthlyMeal = monthlyMeal;
        this.members = members;
    }

    @Timer
    public Map<LocalDate, Boolean> isCalorieAchievementByDate() {
        Map<LocalDate, Boolean> calorieAchievements = new HashMap<>();
        monthlyMeal.getMonthlyMeal().entrySet().stream()
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

    private Member getMemberByDate(LocalDate day, Map<LocalDate, Member> members) {
        return members.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(day) || entry.getKey().equals(day))
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(members.get(getLastDate(day)));
    }

    private LocalDate getLastDate(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    private void validateAtLeastOneMember(Map<LocalDate, Member> members) {
        if (members.isEmpty()) {
            throw MinimumMemberRequiredException.EXCEPTION;
        }
    }
}
