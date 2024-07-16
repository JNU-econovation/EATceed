package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class CalorieAnalyzer extends Analyzer {
    public CalorieAnalyzer(DailyMeal dailyMeal, Member member) {
        super(dailyMeal, member);
    }

    @Override
    public boolean analyze() {
        return member.measureTargetCalorie() - dailyMeal.calculateCurrentCalorie() <= 0;
    }
}
