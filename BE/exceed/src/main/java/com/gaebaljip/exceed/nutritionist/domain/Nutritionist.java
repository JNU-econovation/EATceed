package com.gaebaljip.exceed.nutritionist.domain;

import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Nutritionist {

    private DailyMeal dailyMeal;
    private Member member;

    public boolean evaluateProteinAchieve() {
        return member.measureTargetProtein() - getCurrentProtein() <= 0;
    }

    public boolean evaluateFatAchieve() {
        return member.measureTargetFat() - getCurrentFat() <= 0;
    }

    public boolean evaluateCarbohydrateAchieve() {
        return member.measureTargetCarbohydrate() - getCurrentCarbohydrate() <= 0;
    }

    public double calculateCalorieAchieveRate() {
        return getCurrentCalorie() / member.measureTargetCalorie();
    }

    private Double getCurrentProtein() {
        return dailyMeal.calculateCurrentProtein();
    }

    private Double getCurrentFat() {
        return dailyMeal.calculateCurrentFat();
    }

    private Double getCurrentCarbohydrate() {
        return dailyMeal.calculateCurrentCarbohydrate();
    }

    private Double getCurrentCalorie() {
        return dailyMeal.calculateCurrentCalorie();
    }

    public static Nutritionist createNutritionist(Member memberModel, DailyMeal dailyMeal) {
        return Nutritionist.builder()
                .member(memberModel)
                .dailyMeal(dailyMeal)
                .build();
    }


}
