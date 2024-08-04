package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

/**
 * DailyMeal과 Member를 이용하여 하루치 지방 달성률을 확인
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyFatAnalyzer extends DailyMealAnalyzer {
    public DailyFatAnalyzer(DailyMeal dailyMeal, Member member) {
        super(dailyMeal, member);
    }

    @Override
    public boolean analyze() {
        return member.measureTargetFat() - dailyMeal.calculateCurrentFat() <= 0;
    }
}
