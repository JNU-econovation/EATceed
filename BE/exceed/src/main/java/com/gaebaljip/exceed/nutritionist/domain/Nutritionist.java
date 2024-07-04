package com.gaebaljip.exceed.nutritionist.domain;

import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.member.domain.Member;

import lombok.*;

/**
 * 회원의 신체 정보를 바탕으로 식사 정보를 분석하여 평가하는 영양사 도메인
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Nutritionist {

    private DailyMeal dailyMeal;
    private Member member;

    public boolean evaluateCalorieAchieve() {
        return member.measureTargetCalorie() - getCurrentCalorie() <= 0;
    }

    private Double getCurrentCalorie() {
        return dailyMeal.calculateCurrentCalorie();
    }

    public static Nutritionist createNutritionist(Member memberModel, DailyMeal dailyMeal) {
        return Nutritionist.builder().member(memberModel).dailyMeal(dailyMeal).build();
    }
}
